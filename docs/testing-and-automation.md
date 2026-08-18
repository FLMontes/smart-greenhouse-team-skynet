# Pruebas y Automatización

Este documento describe los comandos locales de pruebas, lint, hooks de Git e Integración Continua del proyecto.

## Estructura

| Componente | Ruta                           |
| ---------- | ------------------------------ |
| Backend    | `backend/final-project` |
| Frontend   | `frontend/dashboard`           |
| Firmware   | `firmware/esp32`               |
| CI         | `.github/workflows/ci.yml`     |

---

## Comandos locales

### Backend

Desde la raíz del repositorio:

```bash
cd backend/final-project
./gradlew build
./gradlew test
./gradlew checkstyleMain checkstyleTest
```

En Windows CMD:

```bat
cd backend\spring\final-project
gradlew.bat build
gradlew.bat test
gradlew.bat checkstyleMain checkstyleTest
```

### Frontend

Desde la raíz del repositorio:

```bash
cd frontend/dashboard
npm ci
npm run build
npm test -- --run
npm run lint
```

### Firmware

Desde la raíz del repositorio:

```bash
cd firmware/esp32
pio test -e native_test
pio check -e esp32dev
```

---

## Hooks de Git

El proyecto usa hooks locales para validar el código antes de confirmar o subir cambios.

### Instalar hooks

Desde la raíz del repositorio:

```bash
./scripts/install-hooks.sh
```

Este script configura Git para usar la carpeta `.githooks`:

```bash
git config core.hooksPath .githooks
```

En Windows se recomienda ejecutar el script desde Git Bash.

### Pre-commit

El hook `pre-commit` ejecuta validaciones de lint antes de permitir un commit.

Validaciones ejecutadas:

```bash
./gradlew checkstyleMain checkstyleTest
npm run lint
pio check -e esp32dev
```

### Pre-push

El hook `pre-push` ejecuta tests unitarios antes de permitir un push.

Validaciones ejecutadas:

```bash
./gradlew test
npm test -- --run
pio test -e native_test
```

Los comandos de test usados en `pre-push` deben mantenerse alineados con los comandos usados en GitHub Actions.

---

## Integración Continua

El workflow de CI se encuentra en:

```txt
.github/workflows/ci.yml
```

Se ejecuta automáticamente en Pull Requests hacia:

```txt
develop
```

El CI tiene tres jobs:

| Job      | Validación                     |
| -------- | ------------------------------ |
| Backend  | Build y tests unitarios        |
| Frontend | Build y tests unitarios        |
| Firmware | Tests unitarios con PlatformIO |

Un Pull Request hacia `develop` solo debería integrarse si todos los jobs pasan correctamente.

---

## Resumen

| Componente | Build             | Test                      | Lint / Check                              |
| ---------- | ----------------- | ------------------------- | ----------------------------------------- |
| Backend    | `./gradlew build` | `./gradlew test`          | `./gradlew checkstyleMain checkstyleTest` |
| Frontend   | `npm run build`   | `npm test -- --run`       | `npm run lint`                            |
| Firmware   | No requerido      | `pio test -e native_test` | `pio check -e esp32dev`                   |
