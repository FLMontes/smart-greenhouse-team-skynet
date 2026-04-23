# Spring Boot backend (IoT sensors API)

REST API aligned with the course contract: **POST /sensors** and **GET /sensors**.

## Prerequisites

- **JDK 21**
- **PostgreSQL** (monorepo `docker/` stack; default host port **5433**)
- **Root `.env`** at the repository root (see `.env.example`) — Spring reads **environment variables** with the same names (`POSTGRES_HOST`, `POSTGRES_PORT`, `POSTGRES_DB`, `POSTGRES_USER`, `POSTGRES_PASSWORD`, `SPRING_PORT`)

### Gradle says “Java 11” but `java -version` is 21

Gradle uses **`JAVA_HOME`** for the daemon (and for resolving plugins). Your shell may run JDK 21 from `PATH` while **`JAVA_HOME`** still refers to JDK 11.

```bash
echo "$JAVA_HOME"
java -version
```

Align `JAVA_HOME` with JDK 21, stop old daemons, then build:

```bash
# Linux (typical when `java` is already 21 on PATH):
export JAVA_HOME="$(dirname "$(dirname "$(readlink -f "$(command -v java)")")")"

./gradlew --stop
./gradlew test
```

On **macOS**: `export JAVA_HOME="$(/usr/libexec/java_home -v 21)"`

If you use **Corretto** on Fedora and know the install path, you can set it explicitly, e.g. `export JAVA_HOME=/usr/lib/jvm/java-21-amazon-corretto`.

### SDKMAN

SDKMAN switches **`JAVA_HOME`** when you select a candidate. Use a **21** distribution in this shell (or as default), then restart Gradle:

```bash
sdk list java          # pick an identifier, e.g. 21.0.5-tem
sdk install java 21.0.5-tem   # if not installed yet
sdk use java 21.0.5-tem       # current shell — or: sdk default java 21.0.5-tem

java -version
echo "$JAVA_HOME"     # should be under ~/.sdkman/candidates/java/...

./gradlew --stop
./gradlew test
```

If Gradle still picks an old JDK, your IDE or another terminal may be overriding **`JAVA_HOME`**; run **`./gradlew`** from the same shell where **`sdk use java …`** succeeded.

## Run

```bash
cd backend/spring/final-project
./gradlew bootRun
```

- API: http://localhost:8080 (or `SPRING_PORT`)
- Swagger UI: http://localhost:8080/swagger-ui.html

## Quick test

```bash
curl -s -X POST http://localhost:8080/sensors \
  -H "Content-Type: application/json" \
  -d '{"sensorId":"esp32-01","temperature":23.5,"humidity":60.2}'

curl -s http://localhost:8080/sensors
```

## Layout

- `com.iot` — application, controllers, services, repositories, DTOs, entities
- `src/main/resources/application.properties` — externalized config (no secrets committed)
- `src/main/resources/db/changelog/` — Liquibase (`ddl-auto=validate`)

Module `spring-boot-docker-compose` is present but **disabled** so the shared monorepo Docker Postgres is used instead of `compose.yaml` in this folder.

## Shared database with Flask

If `sensor_readings` was already created by **Flask/Alembic**, Liquibase used to fail with “relation already exists”. The changelog uses **`preConditions` + `onFail: MARK_RAN`** so that changeset is skipped and recorded when the table is already there.
