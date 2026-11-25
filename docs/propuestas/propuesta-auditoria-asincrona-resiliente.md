# Propuesta de funcionalidad: desacoplar y hacer resiliente la notificación de auditoría

> Documento de análisis (Tech Lead). No incluye implementación, solo diagnóstico, alcance propuesto y consideraciones de diseño para que se implemente manualmente.

## 1. Contexto detectado en el código

Los tres controladores (`CuentasBancariasController`, `MovimientosController`, y previsiblemente `ClienteController`) llaman a `AuditFeignClient.createAuditEvent(...)` **de forma síncrona y bloqueante**, *después* de que el caso de uso ya ha persistido el cambio:

```java
createCuentaUseCase.createCuenta(command);           // ya persistido, transacción cerrada
auditFeignClient.createAuditEvent(auditMapper.toAuditEventRQ(...));  // llamada síncrona a otro microservicio
return ResponseEntity.status(HttpStatus.CREATED).build();
```

Puntos relevantes del `pom.xml` de `infrastructure`: hay `spring-cloud-starter-openfeign` + `feign-okhttp`, pero **no hay ninguna dependencia de resiliencia** (`resilience4j`, `spring-cloud-starter-circuitbreaker-resilience4j`) ni de mensajería (`spring-kafka`), aunque en la carpeta de estudio del repo (`temario-kafka-spring-boot.md`) ya hay contenido preparado sobre Kafka.

## 2. Problema / riesgo detectado

1. **Acoplamiento de disponibilidad**: si el microservicio de auditoría está caído, lento, o el `AuditFeignClient` lanza una excepción, esa excepción no está capturada en el controlador → **la petición del cliente falla con 500**, aunque la cuenta/movimiento **ya se creó correctamente**. Esto genera una respuesta de error engañosa: el negocio se completó pero el usuario ve un fallo.
2. **Latencia acoplada**: el tiempo de respuesta de `POST /cuentas` o `POST /cuentas/{id}/movimientos` depende del tiempo de respuesta del servicio de auditoría, que es un requisito secundario (no crítico para el negocio principal de mover dinero).
3. **Sin reintentos ni circuit breaker**: un fallo transitorio de red hacia el servicio de auditoría no se reintenta; se pierde el evento de auditoría sin ningún registro de que se intentó.
4. **Sin idempotencia visible**: no hay claves de idempotencia en la llamada, por lo que un reintento manual futuro podría duplicar eventos de auditoría.

Este patrón es justo el típico caso de uso de comunicación **fire-and-forget entre microservicios**, que hoy está implementado como llamada síncrona crítica.

## 3. Alcance propuesto (dos niveles, de menor a mayor esfuerzo)

### Opción A — Resiliencia mínima (quick win)

- Añadir `resilience4j-spring-boot3` (o `spring-cloud-starter-circuitbreaker-resilience4j`, ya que el proyecto usa Spring Cloud).
- Envolver la llamada a `auditFeignClient.createAuditEvent(...)` con:
  - **Circuit breaker** (evita seguir llamando a un servicio caído).
  - **Retry** con backoff para fallos transitorios.
  - **Fallback** que solo loguea el fallo (`log.warn`) sin propagar la excepción al cliente HTTP.
- Ejecutar la llamada de forma **asíncrona** (`@Async` con un `ThreadPoolTaskExecutor` dedicado, o `CompletableFuture`) para que el hilo de la petición no espere la respuesta de auditoría.
- Ventaja: cambio acotado a la capa `infrastructure`, no toca `domain`/`application`, se puede hacer servicio por servicio.
- Límite: si el evento de auditoría se pierde tras agotar reintentos, no hay forma de recuperarlo (no hay persistencia intermedia).

### Opción B — Arquitectura orientada a eventos (más robusta)

- Publicar un evento de dominio (`CuentaCreadaEvent`, `MovimientoRealizadoEvent`) en un topic de Kafka **dentro de la misma transacción** usando el patrón **transactional outbox** (tabla `outbox_events` + proceso que publica a Kafka), para garantizar que el evento se registra si y solo si la operación de negocio se confirmó.
- El microservicio de auditoría pasa a ser un **consumidor** del topic, desacoplado por completo de la disponibilidad del `clientes-cuentas-microservicio`.
- Beneficios: elimina el acoplamiento de disponibilidad y latencia por completo, permite replay de eventos, abre la puerta a que otros consumidores (analítica, notificaciones) se suscriban al mismo stream sin tocar este microservicio.
- Coste: mayor complejidad operativa (gestión de Kafka, esquema de eventos/Avro o JSON versionado, outbox con job de publicación o Debezium/CDC), y es un cambio arquitectónico, no puntual.

## 4. Recomendación de secuencia

1. Implementar **Opción A** primero: resuelve el riesgo más grave (romper la respuesta al cliente por un fallo ajeno al negocio) con bajo esfuerzo y sin tocar contratos.
2. Evaluar **Opción B** como evolución si el número de consumidores de eventos de auditoría/negocio crece, o si ya se plantea introducir Kafka en la plataforma (hay indicios de que es un interés del equipo, a juzgar por el material de estudio ya preparado sobre Kafka + Spring Boot).

## 5. Impacto por capa (orientativo)

- **`domain`**: sin cambios en Opción A. En Opción B, nuevos eventos de dominio y puerto de salida `DomainEventPublisher`.
- **`application`**: sin cambios en Opción A (la llamada de auditoría vive en el controlador, no en los casos de uso). En Opción B, los casos de uso publican el evento en vez de que lo haga el controlador.
- **`infrastructure`**: en Opción A, configuración de Resilience4j + executor async + manejo de fallback. En Opción B, adaptador Kafka producer/consumer, tabla y job de outbox.

## 6. Preguntas a resolver antes de implementar

- ¿Es aceptable perder un evento de auditoría puntual tras agotar reintentos (Opción A), o la trazabilidad de auditoría es un requisito no negociable (en cuyo caso Opción B es obligatoria a medio plazo)?
- ¿Existe ya infraestructura de Kafka disponible en el entorno (broker, esquema registry) o habría que provisionarla?
- ¿El servicio de auditoría necesita el evento en tiempo real o es aceptable un desfase de segundos/minutos?
