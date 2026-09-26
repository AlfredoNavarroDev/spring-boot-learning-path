# Práctica — Semana 10 (Cloud real: Cloudflare + Render para producción)

Llevar el sistema de inventario (`Mini-proyecto-S9`) a un entorno real sin gastar:
Postgres administrado (Neon), hosting con Docker (Render), DNS/CDN/WAF
(Cloudflare) y CI/CD con GitHub Actions.

> **Sobre el alcance**: esta carpeta deja los configs y la guía paso a paso listos
> y verificados localmente (build de imagen, `render.yaml`, workflow de CD). El
> **deploy en vivo** requiere crear cuentas reales en Render/Neon/Cloudflare con
> credenciales propias — eso se sigue a mano con la guía de abajo, no es algo
> automatizable sin acceso a esas cuentas.

Para verificar localmente antes de tocar ninguna cuenta:

```bash
cd Mini-proyecto-S9
docker build -t inventario-api .        # misma imagen que Render construiría
docker compose up -d --build            # app + Postgres local, HTTP 200 en /actuator/health
```

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

## Orden sugerido

### 1. Neon — PostgreSQL administrado
- [ ] Cuenta en [neon.tech](https://neon.tech) (free tier, no expira como el
      Postgres gratis de Render) + connection string
      (`postgresql://usuario:password@host/db?sslmode=require`)
- [ ] `Mini-proyecto-S9` lee host/usuario/password/db por separado como
      `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD` (ver `application.yml`)

### 2. Render — hosting con Docker (`render.yaml`)
- [ ] Blueprint conectado al repo, Render detecta `dockerfilePath` y construye la
      imagen igual que en CI (mismo `Dockerfile` multi-stage de la Semana 9)
- [ ] Env vars de base de datos completadas a mano con el connection string de
      Neon (`render.yaml` trae `fromDatabase` para el Postgres propio de Render,
      pero este proyecto usa Neon)
- [ ] `JWT_SECRET` se genera solo (`generateValue: true`) — nunca se commitea un
      secreto real

### 3. Cloudflare — DNS, CDN/WAF, dominio propio
- [ ] `CNAME` de `api.tudominio.com` → URL de Render, con el proxy naranja (☁️)
      activado
- [ ] Reglas básicas de WAF (rate limiting por IP, bloqueo de países si aplica)
- [ ] SSL/TLS en modo "Full (strict)"
- [ ] (Opcional) Cloudflare Access delante de `/swagger-ui` o `/actuator`
- [ ] (Opcional) R2 para adjuntos (equivalente a S3 sin costo de egress) — no
      usado en este mini-proyecto

### 4. CI/CD — `.github/workflows/deploy.yml`
- [ ] Job `test` — `mvn clean test` sobre `Mini-proyecto-S9` (incluye la
      integración con Testcontainers); si falla, no se llega a desplegar
- [ ] Job `deploy` — dispara el Deploy Hook de Render (`RENDER_DEPLOY_HOOK_URL`,
      guardado como secret del repo)

## Mapeo a AWS/Azure (para hablarlo en entrevista)

| Esta ruta (gratis) | AWS | Azure |
|---|---|---|
| Render (Web Service, Docker) | ECS Fargate / App Runner | App Service (contenedor) / Container Apps |
| Neon (Postgres administrado) | RDS for PostgreSQL / Aurora | Azure Database for PostgreSQL |
| Cloudflare (DNS + CDN + WAF) | Route 53 + CloudFront + WAF | Azure DNS + Front Door + WAF |
| Cloudflare R2 (objetos) | S3 | Blob Storage |
| GitHub Actions (CI/CD) | CodePipeline/CodeBuild | Azure DevOps Pipelines |

La idea que vale la pena poder explicar: el patrón (imagen Docker + Postgres
administrado + DNS/CDN/WAF delante + CI que testea antes de desplegar) es el mismo
en los tres proveedores — lo que cambia es el nombre del servicio gestionado, no
la arquitectura.

## Checklist de cierre

- [ ] La imagen Docker corre standalone local antes de conectar el Blueprint de Render
- [ ] Puedo explicar la arquitectura completa: Cloudflare → Render → Neon
- [ ] Deploy en vivo confirmado con cuentas reales (paso manual, fuera del repo)
- [ ] Sé mapear cada pieza gratis a su equivalente pago en AWS/Azure
