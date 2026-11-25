# Roadmap: estado de la cuenta (activar / bloquear / cerrar) y parte reactiva

**Rama:** `feature/reactive-update-status`. **Modelo y reglas de negocio:** ya pensados en [`propuestas/propuesta-gestion-estado-cuenta.md`](propuestas/propuesta-gestion-estado-cuenta.md); léela antes de implementar. Este documento dice **dónde se quedó** y **qué falta**, no repite la propuesta.

## Qué hay hecho (commit `e2daf7a`, 25 de noviembre de 2026)

| Pieza | Estado |
|---|---|
| `swagger.yml`: `PATCH /cuentas/{idCuenta}/estado` con `CuentaUpdateEstadoRQ` (`estado`, `motivo` opcional) y `EstadoEnum` (`ACTIVA`, `BLOQUEADA`, `CERRADA`) | **Hecho** (solo el contrato). Respuestas documentadas: 200, 400, 404, 409, 422, 500 |
| Controller | Método `actualizarEstadoCuenta` que **delega en el valor por defecto de la interfaz generada: responde 501** |
| `CuentaRS.estado` | Existe en el contrato pero **opcional** y **nunca se rellena** (no existe en dominio ni en BD) |
| Enum compartido `TipoMovimientoEnum` | Hecho (refactor); el mapper cubre los 3 tipos |
| pom de `infrastructure` | Añadidas `spring-boot-starter-webflux` y `spring-boot-starter-data-r2dbc`, **sin uso**. Con `starter-web` también presente, la aplicación arranca en modo servlet |
| Dominio, caso de uso, persistencia | **Nada.** `CuentaBancaria` no tiene `estado` |

## Lo que falta, en orden

Es el orden de la propuesta, que va de dentro hacia fuera (dominio → aplicación → infraestructura). Cada paso debe dejar el proyecto compilando y con los tests en verde.

### 1. Dominio
- [ ] `EstadoCuenta` (enum) y campo `estado` en `CuentaBancaria`.
- [ ] Reglas: nace `ACTIVA`; solo una cuenta activa admite movimientos; `BLOQUEADA → ACTIVA` es explícito; `CERRADA` es terminal y solo se alcanza con saldo 0 (ya existe `CuentaConflictException.Tipo.NO_CERRABLE`).
- [ ] Excepción de dominio para rechazar movimientos sobre cuentas no activas.

### 2. Aplicación
- [ ] Caso de uso de cambio de estado (puerto de entrada, servicio, comando con `idCuenta`, `nuevoEstado`, `motivo`).
- [ ] Activar la validación de estado en `CreateMovimientoService` (hay un comentario que la insinúa), tanto en la cuenta origen como en el destino de una transferencia.

### 3. Infraestructura
- [ ] **Migración de BD:** columna `estado` con valor por defecto `ACTIVA` para no romper datos existentes. Los scripts SQL de este servicio **viven en el repo `infrastructure-platform`**, carpeta `shared-database/clientes-cuentas/`.
- [ ] Entidad y mappers (`CuentaEntityMapper`, `CuentaResponseMapper`): incluir `estado`.
- [ ] Implementar el método del controller y mapear las excepciones a `ProblemDetail` en el `GlobalExceptionHandler` (tipo `/errors/cuenta-bloqueada`).
- [ ] Marcar `CuentaRS.estado` como **obligatorio** en el swagger cuando ya se rellene (hoy es opcional a propósito, para no mentir en el contrato).
- [ ] Auditar el cambio de estado (`AuditAction`).

### 4. Tests
Actualizar los que la propuesta lista (`CreateMovimientoServiceTest`, `CuentaBancariaTest`, `CuentaBancariaRepositoryAdapterTest`, `CuentasBancariasControllerUnitTest`) y añadir los de cuentas bloqueadas y cerradas.

## La parte "reactiva": **no está definida, y es lo primero que hay que decidir**

La idea original era "integrarlo con un servicio reactivo", pero **no quedó escrito qué servicio ni con qué contrato**. Antes de tocar nada hay que responder:

1. **¿Qué servicio reactivo es** y qué debe hacer con el cambio de estado (¿notificarlo?, ¿consultarlo antes de permitir un cierre?).
2. **¿De qué lado va la reactividad?**
   - **A.** Este servicio sigue siendo **servlet/MVC** y llama al servicio reactivo con `WebClient` (usándolo de forma bloqueante o devolviendo el resultado). Es lo más simple y encaja con el pom actual.
   - **B.** Se migra este endpoint (o el servicio) a **WebFlux + R2DBC**. Es mucho más trabajo: las entidades JPA y los adapters actuales son bloqueantes, y **mezclar JPA con R2DBC en un mismo servicio es una mala idea**.
3. Hasta decidirlo, `webflux` y `data-r2dbc` del pom **no aportan nada**: se pueden quitar o dejar.

> Referencia útil: el proyecto `fasttrack-delivery-microservicio` (otro repositorio) es un servicio reactivo completo (WebFlux + R2DBC + tests) construido para aprender; sirve de modelo si se elige la opción B.

## Otros puntos pendientes de este mismo servicio

- **El filtro `tipo` de `GET /movimientos/{id}` se acepta pero no se aplica:** el controller no se lo pasa al caso de uso (`findByCuentaId`). Hay que cambiar la firma del caso de uso, del adapter y de la consulta, y añadir tests.
- **La auditoría de ese `GET` registra la acción como `CREATE`**: parece una copia y pega; debería ser una lectura.
- **Las respuestas 422 del swagger se describen como "Conflict"** en varios endpoints (copia y pega). Solo el del endpoint de estado está bien descrito.
