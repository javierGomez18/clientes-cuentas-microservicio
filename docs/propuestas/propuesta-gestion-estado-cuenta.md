# Propuesta de funcionalidad: gestión del estado de la cuenta bancaria (activar / bloquear / cerrar)

> Documento de análisis (Tech Lead). No incluye implementación, solo diagnóstico, alcance propuesto y consideraciones de diseño para que se implemente manualmente.

## 1. Contexto detectado en el código

En `CreateMovimientoService` (módulo `application`) hay código comentado que ya anticipa esta necesidad:

```java
/*Optional.of(cuentaOrigen)
.filter(CuentaBancaria::isActiva)
.orElseThrow(() -> new CuentaBloqueadaException(id));*/
```

Además, `CuentaConflictException` ya define un tipo `NO_CERRABLE` (`GlobalExceptionHandler.handleCuentaConflictException`, mapeado a `/errors/cuenta-no-cerrable`), lo que indica que el dominio ya contempla la idea de "cuenta que no se puede cerrar", pero **no existe ningún endpoint ni lógica que dé de baja, bloquee o cierre una cuenta**. Hoy `CuentaBancaria` solo tiene `id`, `dniCliente`, `tipoCuenta`, `total`: no hay ningún campo de estado.

En resumen: el diseño ya "sabe" que esta funcionalidad existirá (hay una excepción reservada para ella), pero nunca se completó.

## 2. Problema / oportunidad de negocio

Hoy **cualquier cuenta admite movimientos siempre que tenga saldo suficiente**, sin importar si el cliente la ha solicitado bloquear (p. ej. por fraude, pérdida de tarjeta, impago) o si ya debería estar cerrada. Esto es un riesgo funcional real en un dominio bancario:

- No se puede bloquear una cuenta comprometida sin tocar código.
- No hay forma de cerrar formalmente una cuenta (con su regla de negocio: solo cerrable si el saldo es 0, coherente con `NO_CERRABLE`).
- No hay distinción entre "cuenta operativa" y "cuenta en proceso de apertura/verificación".

## 3. Alcance propuesto

### 3.1 Modelo de dominio

Añadir un campo `estado` a `CuentaBancaria` (enum, igual patrón que `TipoMovimiento`):

```
EstadoCuenta { ACTIVA, BLOQUEADA, CERRADA }
```

Reglas de negocio candidatas:

- Una cuenta nace en `ACTIVA` (o `PENDIENTE_ACTIVACION` si se quiere un flujo de alta con verificación, opcional).
- Solo una cuenta `ACTIVA` admite movimientos (`INGRESO`, `RETIRADA`, `TRANSFERENCIA` como origen o destino).
- `BLOQUEADA → ACTIVA` es un desbloqueo explícito (requiere acción, no automática).
- `CERRADA` es un estado terminal: no admite movimientos ni reactivación. Solo se permite `ACTIVA/BLOQUEADA → CERRADA` si `total == 0` (esto es exactamente lo que ya cubre `CuentaConflictException.Tipo.NO_CERRABLE`).

### 3.2 Puertos y casos de uso (capa `domain` / `application`)

- Nuevo puerto de entrada `UpdateEstadoCuentaUseCase` (o extender `UpdateCuentaUseCase` si se prefiere un único caso de uso de actualización), con comando `UpdateEstadoCuentaCommand(idCuenta, nuevoEstado, motivo)`.
- Nueva excepción de dominio `CuentaBloqueadaException` (el nombre que ya insinúa el comentario) para rechazar movimientos sobre cuentas no activas.
- Reutilizar `CuentaConflictException.Tipo.NO_CERRABLE` para el cierre con saldo distinto de cero.
- Modificar `CreateMovimientoService` para descomentar y activar la validación de estado antes de aplicar el movimiento (tanto en la cuenta origen como, si es transferencia, en la cuenta destino).

### 3.3 Infraestructura

- Migración de BD: columna `estado` en la tabla de cuentas (con valor por defecto `ACTIVA` para no romper datos existentes).
- Nuevo endpoint, p. ej. `PATCH /cuentas/{idCuenta}/estado` con body `{ "estado": "BLOQUEADA", "motivo": "..." }` (añadir a `swagger.yml`, que genera el contrato `CuentasApi`).
- Nuevo `ProblemDetail` / tipo de error para `/errors/cuenta-bloqueada` en `GlobalExceptionHandler`.
- Actualizar `ClienteEntityMapper`/`CuentaEntityMapper` y mappers de respuesta (`CuentaResponseMapper`) para incluir el nuevo campo.
- Registrar el cambio de estado como evento de auditoría (`AuditAction`, ya existe integración con `AuditFeignClient`; ver también la propuesta sobre auditoría asíncrona para no duplicar esfuerzo).

### 3.4 Impacto en tests existentes

Los tests de `CreateMovimientoServiceTest`, `CuentaBancariaTest`, `CuentaBancariaRepositoryAdapterTest` y `CuentasBancariasControllerUnitTest` deberán actualizarse para cubrir cuentas bloqueadas/cerradas como caso de rechazo.

## 4. Preguntas a resolver antes de implementar

- ¿Quién puede bloquear/desbloquear una cuenta? (Rol/permiso vía `SecurityConfig` + OAuth2 resource server ya presentes en el proyecto.)
- ¿El bloqueo impide solo movimientos o también consultas (`FindCuentaUseCase`, `FindMovimientoUseCase`)? Recomendación: bloquear escritura, permitir lectura.
- ¿Se necesita historial de cambios de estado (quién, cuándo, motivo) o basta con el estado actual + el rastro que ya deja el evento de auditoría?

## 5. Fuera de alcance de esta propuesta

- Flujos de verificación KYC para el alta de cuenta.
- Notificaciones al cliente cuando su cuenta se bloquea (podría ser una iteración posterior).
