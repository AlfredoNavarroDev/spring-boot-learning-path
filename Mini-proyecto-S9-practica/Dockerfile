# --- Etapa 1: build ---
# Compila con Maven + JDK, pero esta capa entera se descarta y no llega a la imagen final.
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /build

# .mvn/mvnw primero: cachea la descarga de dependencias en su propia capa mientras el
# codigo fuente (que cambia mucho mas seguido) todavia no se copio.
COPY mvnw pom.xml ./
COPY .mvn .mvn
RUN chmod +x mvnw && ./mvnw -q -B dependency:go-offline

COPY src src
RUN ./mvnw -q -B clean package -DskipTests

# --- Etapa 2: runtime ---
# Solo JRE (no JDK) y el jar ya compilado: imagen final mucho mas chica y sin toolchain de build.
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Correr como usuario sin privilegios, no como root.
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=builder /build/target/*.jar app.jar

EXPOSE 8089
HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
    CMD wget -qO- http://localhost:8089/actuator/health | grep -q '"status":"UP"' || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]
