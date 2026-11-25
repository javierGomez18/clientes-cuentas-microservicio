# Roadmap: integrar este servicio con la seguridad y las métricas de la plataforma

El plan **de la plataforma** (Keycloak, `client_credentials`, estándar de métricas, Prometheus y Grafana) está en el repositorio **`infrastructure-platform`**, carpeta `docs/`:

- `roadmap-seguridad-keycloak.md`
- `roadmap-metricas-prometheus.md`
- `hallazgos-y-decisiones.md` (qué se verificó y cómo)

Este documento recoge **solo lo que toca en este repositorio** (el servicio, el gateway y sus tests). **Dependencia:** casi todo lo de la seguridad espera a que Keycloak esté configurado de forma reproducible (fase 1 del roadmap de seguridad).

## Estado de partida (25 de noviembre de 2026)

| Pieza | Estado |
|---|---|
| `SecurityConfig` del servicio (`infrastructure/.../config`) | Resource server JWT, sin sesión. `/actuator/**` abierto. Roles por ruta (ver abajo) |
| Conversor de roles | **Roto** (ruta anidada con el conversor estándar → siempre vacío). Verificado en `audit-service`, donde ya se corrigió |
| Gateway | Resource server + filtro `TokenRelay` por defecto; ruta a `audit-service` por Eureka |
| Cliente de auditoría | Lo aporta la plataforma (`audit-client`); **no envía token**; envía `X-Client-Id` |
| Métricas | La dependencia `metrics-service` está declarada, pero **inerte**: este servicio no la activa ni publica eventos |

## Fase 1: este servicio como resource server correcto

- [ ] **Corregir el conversor de roles** de `SecurityConfig` (mismo arreglo que en `audit-service`; ver su `SecurityConfig` y su `SecurityConfigTest` como modelo). Idealmente se consume de la librería compartida (fase 5 del roadmap de seguridad) en vez de copiarlo.
- [ ] **Revisar las reglas de roles.** Hoy `POST`/`PUT` sobre `/cuentas/**` y `GET` de `/movimientos/**` exigen `clientes.read` **y** `cuentas.read`. Una escritura que pide solo roles de lectura casi seguro es un error: decidir `*.write`.
- [ ] **Alinear el `issuer-uri`** con el resto (en `config-server/clientes-cuentas-microservicio-dev.yml` es `keycloak:8082`; `8082` es el puerto del host, dentro de Docker Keycloak escucha en `8080`).
- [ ] Tests de la cadena de seguridad: 401 sin token, 403 con rol equivocado, 200 con rol correcto (`MockMvc` + `jwt()` de Spring Security Test).

**Hecho cuando:** con Keycloak levantado, un token con el rol correcto entra y uno sin él es rechazado, y hay tests que lo demuestran.

## Fase 2: el gateway

- [ ] **Ruta de `audit-service`:** el `RewritePath` parece duplicar `audit` (`/api/v1/audit/audit/...`). Probarlo con `curl` por el gateway y corregirlo (detalle en `infrastructure-platform/docs/hallazgos-y-decisiones.md`, B2).
- [ ] **`TokenRelay`:** comprobar si hace algo útil en un gateway que solo valida JWT; probablemente se puede quitar porque el gateway ya reenvía `Authorization`.
- [ ] Alinear el `issuer-uri` del gateway (hoy `localhost:8082`).
- [ ] Decidir qué rutas del gateway son públicas y cuáles requieren token.

## Fase 3: auditoría con `client_credentials` (depende de la plataforma)

La fase 3 del roadmap de seguridad de la plataforma da el token al cliente Feign **dentro de `audit-client`**, así que aquí **casi no hay código**: añadir las propiedades del `ClientRegistration` (id, secreto desde fuera del repo, `token-uri`) y comprobar.

- [ ] Configurar el *client* de este servicio en la configuración (`config-server/`), **sin el secreto en el yml**.
- [ ] Comprobar de extremo a extremo: una operación aquí → el evento aparece en `audit-service`.
- [ ] Decidir el destino de `X-Client-Id` y de `security.allowed-clients` (ver decisiones de la plataforma).
- [ ] Con la seguridad activa, un fallo de autenticación de la auditoría **debe verse** (log de error y métrica), no ser silencioso.

## Fase 4: métricas (depende de la plataforma)

- [ ] Cuando la librería sea autoconfigurable (fase 1 del roadmap de métricas), este servicio la recoge solo con la dependencia que ya tiene.
- [ ] Publicar eventos de métrica al auditar (éxito, fallo, conflicto, no procesable, latencia) desde el `audit-client`; aquí solo se comprueba que aparecen.
- [ ] Comprobar que `/api/v1/clientes-cuentas/actuator/prometheus` responde y que `application` y `environment` salen bien (hoy el yml compartido ya pone `application`).
- [ ] Revisar que `shared-filters` (cabecera, sanitización) no bloquea el *scrape* de Prometheus.

## Fase 5: tests de extremo a extremo

- [ ] Un test de integración que levante la cadena mínima (este servicio + `audit-service` + Keycloak con Testcontainers) y compruebe: operación → token → evento auditado → métrica incrementada.
- [ ] Que los tests se salten limpiamente si Docker no está disponible.

## Cosas que no hay que olvidar

- **No subir secretos.** Ni el de los *clients* de Keycloak ni contraseñas reales. Hoy hay valores de desarrollo (`admin`) en `config-server/`.
- **Los dos composes se necesitan mutuamente** (redes `external`); ver el README.
