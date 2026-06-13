# Proyecto final — Ingeniería de Software

**Universidad Nacional de Córdoba** · Facultad de Ciencias Exactas, Físicas y Naturales · Ingeniería de Software

Repositorio base del trabajo final: sistema IoT de punta a punta (ESP32, backend, PostgreSQL, frontend).

- **Documentación del proyecto (instalación, uso, stack):** [ABOUT.md](ABOUT.md)
- **Consigna y criterios de evaluación:** [docs/CONSIGNA.md](docs/CONSIGNA.md)

## GitHub Actions CI/CD

Este proyecto incluye automatización con GitHub Actions para CI/CD sobre el TP de testing y patrones ubicado en `TPs Practicos/tp_testing_patrones_de_diseno`.

### CI

El workflow `.github/workflows/main.yml` se ejecuta en cada Pull Request hacia `master`.

Usa dos actions reutilizables:

- `.github/actions/build`: compila el proyecto con `mvn compile`.
- `.github/actions/test`: ejecuta los tests con `mvn test`.

El job `test` depende de `build` mediante `needs: build`.

### CD

El workflow `.github/workflows/release.yml` se ejecuta cuando se sube un tag semántico, por ejemplo `v1.0.0`.

Este workflow ejecuta `mvn package` y publica un GitHub Release con el archivo `.jar` adjunto.

### CI vs CD

CI valida cambios antes de integrarlos a `master`.

CD publica una versión del proyecto cuando se crea un tag.