# Semana 10 — Cloud real: Cloudflare + Render para producción

Llevar el sistema de inventario (`Mini-proyecto-S9`) a un entorno real sin gastar: Postgres administrado (Neon), hosting con Docker (Render), DNS/CDN/WAF (Cloudflare) y CI/CD con GitHub Actions.

> **Sobre el alcance de esta semana**: esta carpeta deja los **configs y la guía paso a paso** listos y verificados localmente (build de imagen, `render.yaml`, workflow de CD). El **deploy en vivo** requiere crear cuentas reales en Render/Neon/Cloudflare con tus propias credenciales — eso lo hacés vos siguiendo la guía; no es algo que se pueda automatizar sin acceso a esas cuentas.

## Arquitectura del despliegue

```
Usuario ──HTTPS──▶ Cloudflare (DNS, proxy, WAF, cache)
                       │
                       ▼
                  Render (Web Service, Docker)
                  Mini-proyecto-S9/Dockerfile
                       │
                       ▼
                  Neon (PostgreSQL administrado)
```

## Paso a paso

### 1. Neon — PostgreSQL administrado

1. Crear cuenta en [neon.tech](https://neon.tech) (free tier, no expira como el Postgres gratis de Render).
2. Crear un proyecto → copiar el **connection string** (`postgresql://usuario:password@host/db?sslmode=require`).
3. Guardar host/usuario/password/db por separado — `Mini-proyecto-S9` los lee como `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD` (ver `application.yml`).

### 2. Render — hosting con Docker

1. [render.com](https://render.com) → **New → Blueprint** → conectar el repo → seleccionar este `render.yaml`.
2. Render detecta `dockerfilePath` y construye la imagen igual que en CI (mismo `Dockerfile` multi-stage de la Semana 9).
3. Completar a mano las env vars de base de datos con el connection string de Neon (el `render.yaml` trae la opción `fromDatabase` para el Postgres propio de Render, pero para este proyecto usamos Neon — ver comentario en el archivo).
4. `JWT_SECRET` se genera solo (`generateValue: true`) — nunca se commitea un secreto real.
5. Copiar la URL pública que asigna Render (`https://inventario-api-xxxx.onrender.com`).

### 3. Cloudflare — DNS, CDN/WAF, dominio propio

1. Agregar tu dominio a Cloudflare (o usar un subdominio si no tenés uno).
2. **DNS**: registro `CNAME` de `api.tudominio.com` → la URL de Render, con el proxy naranja (☁️) activado — así el tráfico pasa por Cloudflare antes de llegar a Render.
3. **WAF / Security**: reglas básicas (rate limiting por IP, bloqueo de países si aplica) en Security → WAF.
4. **SSL/TLS**: modo "Full (strict)" — Cloudflare y Render ya manejan certificados válidos en ambos extremos.
5. (Opcional) **Cloudflare Access**: proteger `/swagger-ui` o `/actuator` detrás de un login de Cloudflare Zero Trust en vez de dejarlos públicos en producción.
6. (Opcional) **R2**: si el sistema necesitara guardar archivos (comprobantes, fotos de producto), R2 es el equivalente a S3 sin costo de egress — no se usa en este mini-proyecto, pero es la pieza que faltaría para adjuntos.

### 4. CI/CD con GitHub Actions

`.github/workflows/deploy.yml` en esta carpeta:
1. Job `test`: corre `mvn clean test` sobre `Mini-proyecto-S9` (incluye la integración con Testcontainers) — si falla, no se llega a desplegar.
2. Job `deploy`: si los tests pasan, dispara el **Deploy Hook** de Render (`RENDER_DEPLOY_HOOK_URL`, guardado como secret del repo) — Render clona y construye la imagen del lado suyo, el runner de GitHub no necesita Docker para este paso.

Para activarlo: Render → servicio → Settings → **Deploy Hook** → copiar la URL → GitHub → repo → Settings → Secrets → `RENDER_DEPLOY_HOOK_URL`.

## Mapeo a AWS/Azure (para hablarlo en entrevista)

| Esta ruta (gratis) | AWS | Azure |
|---|---|---|
| Render (Web Service, Docker) | ECS Fargate / App Runner | App Service (contenedor) / Container Apps |
| Neon (Postgres administrado) | RDS for PostgreSQL / Aurora | Azure Database for PostgreSQL |
| Cloudflare (DNS + CDN + WAF) | Route 53 + CloudFront + WAF | Azure DNS + Front Door + WAF |
| Cloudflare R2 (objetos) | S3 | Blob Storage |
| Cloudflare Access | (Cognito + API Gateway authorizer, o VPN) | Azure AD (Entra ID) App Proxy |
| GitHub Actions (CI/CD) | CodePipeline/CodeBuild (o GH Actions igual) | Azure DevOps Pipelines (o GH Actions igual) |
| Docker multi-stage + healthcheck | Igual (ECS también usa el healthcheck del Dockerfile) | Igual |

La idea que vale la pena poder explicar: el patrón (imagen Docker + Postgres administrado + DNS/CDN/WAF delante + CI que testea antes de desplegar) es el mismo en los tres proveedores — lo que cambia es el nombre del servicio gestionado, no la arquitectura.

## Verificación local (lo que sí se corrió en esta sesión)

```bash
cd Mini-proyecto-S9
docker build -t inventario-api .        # misma imagen que Render construiría
docker compose up -d --build            # app + Postgres local, HTTP 200 en /actuator/health
```

Esto confirma que el `Dockerfile` funciona standalone (sin depender de nada de Render) antes de conectar el Blueprint — si esto no corre local, tampoco va a correr en Render.
