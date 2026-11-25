# clientes-cuentas-microservicio: guía para retomar el proyecto

> **Última actualización:** 25 de noviembre de 2026 · **Estado de git en esa fecha:** rama `feature/reactive-update-status`, commit `e2daf7a`. `mvn test` termina en `BUILD SUCCESS`.
> Si lees esto meses después, **empieza por este fichero**.

## 1. Qué es y qué papel tiene

Microservicio de **clientes, cuentas bancarias y movimientos** (hexagonal: `domain` / `application` / `infrastructure`, con config server, Eureka y gateway). Además de ser un proyecto en sí, hace de **servicio de referencia** de la plataforma común (`infrastructure-platform`, otro repositorio): es el primer consumidor del cliente de auditoría, de los filtros compartidos y de las librerías de métricas.

Hay **dos evolutivos a medias**, ambos interrumpidos al encontrar empleo y tener que aprender programación reactiva (para eso se creó el proyecto `fasttrack-delivery-microservicio`). Lo que hay es **poco más que un concepto**, y es intencionado:

| Evolutivo | Qué es | Dónde está el plan |
|---|---|---|
| **Seguridad y métricas de plataforma** | Keycloak + OAuth2 `client_credentials` para todos los servicios y métricas Prometheus estandarizadas | Aquí: [`roadmap-integracion-plataforma.md`](roadmap-integracion-plataforma.md). El plan de la plataforma está en el repo `infrastructure-platform`, carpeta `docs/` |
| **Estado de la cuenta (activar / bloquear / cerrar) y parte reactiva** | Endpoint `PATCH /cuentas/{id}/estado` e integración con un servicio reactivo | [`roadmap-estado-cuenta-reactivo.md`](roadmap-estado-cuenta-reactivo.md) |

## 2. Puertos y servicios (Docker)

| Servicio | Puerto | Notas |
|---|---|---|
| config-server | `8888` | Sirve los yml de la carpeta `config-server/` |
| eureka-server | `8761` | |
| clientes-cuentas-ms | `8080` | Context-path `/api/v1/clientes-cuentas` |
| gateway | `8081` | Rutas `/api/clientes`, `/api/cuentas`, `/api/movimientos` y `/api/audit` |

`audit-service` (puerto `8085`) y Keycloak (`8082` en el host) están en el compose del repo `infrastructure-platform`. Los dos composes se declaran **mutuamente la red del otro como `external`**: crea las redes antes o arranca en dos pasadas.

## 3. Primer día: comprobar que todo sigue vivo

```bash
mvn clean test        # debe terminar en BUILD SUCCESS (hay tests unitarios en los tres módulos)
git status -sb        # ¿algo sin commitear o sin subir?
```

- Para correrlo de verdad hace falta la plataforma (config server, Eureka, `audit-service`, PostgreSQL) y, si la seguridad está activa, Keycloak.
- **Versiones a revisar antes de seguir:** Spring Boot **3.3.1** y Spring Cloud **2023.0.1** son de 2024; comprueba su soporte.

## 4. Lo más importante que hay que saber

1. **El `SecurityConfig` de este servicio tiene un fallo conocido:** el conversor de roles usa la ruta anidada `resource_access.clientes-cuentas-service.roles` con el conversor estándar de Spring, que **devuelve siempre una lista vacía**. Con Keycloak configurado, **todas las rutas protegidas darían 403**. Está verificado (ver `infrastructure-platform/docs/hallazgos-y-decisiones.md`, A1) y **aún no se ha corregido aquí**.
2. **La auditoría fallará con la seguridad activada:** el cliente Feign no envía token. Se resuelve en la plataforma (fase 3 del roadmap de seguridad).
3. **El filtro `tipo` de `GET /movimientos/{id}` no hace nada:** el controller lo acepta pero no se lo pasa al caso de uso; solo se anota en el evento de auditoría.
4. **Dos dependencias inertes en el pom de `infrastructure`:** `spring-boot-starter-webflux` y `spring-boot-starter-data-r2dbc`. Se añadieron para la integración reactiva que no se llegó a hacer. Con `starter-web` también presente, Spring arranca en modo servlet (MVC).
5. **Credenciales de desarrollo en el repo** (`admin`, `admin123`) en los yml de `config-server/` y en el compose. No son de producción; plan de gestión en el repo de la plataforma.

## 5. Índice de esta carpeta

| Documento | Para qué |
|---|---|
| [`roadmap-integracion-plataforma.md`](roadmap-integracion-plataforma.md) | Qué debe hacer este servicio, el gateway y los tests para integrarse con la seguridad y las métricas de la plataforma |
| [`roadmap-estado-cuenta-reactivo.md`](roadmap-estado-cuenta-reactivo.md) | Cómo terminar el endpoint de estado de cuenta y la parte reactiva |
| [`propuestas/propuesta-gestion-estado-cuenta.md`](propuestas/propuesta-gestion-estado-cuenta.md) | Propuesta original (modelo, puertos, infraestructura) del estado de cuenta |
| [`propuestas/propuesta-auditoria-asincrona-resiliente.md`](propuestas/propuesta-auditoria-asincrona-resiliente.md) | Propuesta para desacoplar la auditoría y hacerla resiliente |

Los dos documentos de `propuestas/` se escribieron **antes** de decidir el rumbo actual: son contexto y alternativas, no el plan vigente.
