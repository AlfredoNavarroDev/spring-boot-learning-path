# Práctica — Semana 10 (Cloud real: Cloudflare + Render para producción)

Ejercicio de completar a mano los dos archivos de infraestructura como código
de esta semana (`render.yaml` y `.github/workflows/deploy.yml`), que despliegan
el sistema de inventario (`Mini-proyecto-S9`) a Render. Los `TODO` reemplazan
los valores reales; el resto de la guía (crear cuentas en Neon/Render/Cloudflare)
sigue siendo manual, fuera del repo, tal como en la versión resuelta.

Si te trabás, mirá `../Mini-proyecto-S10` (mismo archivo ya resuelto).

No hay `mvn compile` esta semana — la verificación es leer el YAML resultante y
compararlo línea a línea contra la versión resuelta.

## Orden sugerido

### 1. `render.yaml` — `healthCheckPath`
**Propósito:** Render usa este endpoint para saber si el contenedor está sano
antes de enrutarle tráfico — tiene que ser el mismo que expone Spring Boot
Actuator (y el mismo que usa el `HEALTHCHECK` del `Dockerfile` de la Semana 9).
**Archivos:** `render.yaml`
- [ ] Completar `healthCheckPath` con la ruta real de Actuator health

### 2. `render.yaml` — variables de entorno de la base de datos
**Propósito:** practicar cómo Render resuelve variables de entorno a partir de
una base de datos administrada (`fromDatabase`), en vez de hardcodear
credenciales — el equivalente a leer un `.env` pero gestionado por la plataforma.
**Archivos:** `render.yaml`
- [ ] Agregar `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD` siguiendo el mismo
      patrón que el `DB_HOST` ya presente (`fromDatabase: { name: inventario-db, property: <prop> }`)

### 3. `render.yaml` — `JWT_SECRET`
**Propósito:** entender por qué un secreto de firma JWT nunca se commitea, ni
siquiera como placeholder — se genera del lado de la plataforma.
**Archivos:** `render.yaml`
- [ ] Completar la clave que hace que Render genere y guarde un secreto
      random cifrado para `JWT_SECRET`

### 4. `deploy.yml` — comando de tests
**Propósito:** practicar el comando exacto de Maven Wrapper que corre en CI
antes de cualquier deploy — el gate de calidad.
**Archivos:** `.github/workflows/deploy.yml`
- [ ] Completar el `run:` del step de tests (Maven Wrapper, `clean` + `test`,
      modo batch/no interactivo)

### 5. `deploy.yml` — gate de deploy
**Propósito:** el deploy nunca debe dispararse si los tests fallan, y el deploy
en sí es solo un webhook (Render clona y construye del lado suyo).
**Archivos:** `.github/workflows/deploy.yml`
- [ ] Declarar que el job `deploy` depende del job `test`
- [ ] Completar el `curl` que dispara el Deploy Hook de Render usando el
      secret `RENDER_DEPLOY_HOOK_URL`

## Pasos manuales (fuera del repo, no se "completan" con código)

Estos siguen igual que en la versión resuelta — requieren cuentas reales:

- [ ] Cuenta en Neon (Postgres administrado) + connection string
- [ ] Blueprint en Render conectado al repo, apuntando a `render.yaml`
- [ ] Dominio/subdominio en Cloudflare con `CNAME` hacia Render, proxy activado
- [ ] `RENDER_DEPLOY_HOOK_URL` guardado como secret del repo en GitHub

## Checklist de cierre

- [ ] `render.yaml` completo coincide en estructura con `../Mini-proyecto-S10/render.yaml`
- [ ] `deploy.yml` completo coincide en estructura con `../Mini-proyecto-S10/.github/workflows/deploy.yml`
- [ ] Puedo explicar por qué el deploy es un webhook y no un `docker push` desde el runner
- [ ] Entiendo la diferencia entre secretos gestionados por la plataforma (`generateValue`) y secretos de repo (`secrets.*`)
