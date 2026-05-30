# Convención de Commits del Proyecto

Este documento define la convención de mensajes de commit que debe usar el equipo durante el desarrollo del proyecto.

## Formato general

Cada commit debe seguir el siguiente formato:

```txt
<tipo>(<CLAVE-JIRA>): <descripción breve del cambio>
```

Ejemplo:

```txt
docs(INV-15): add git workflow documentation
```

## Clave Jira

Todos los commits integrados al repositorio deben incluir la clave Jira correspondiente.

La clave Jira permite vincular cada cambio del repositorio con una historia o tarea del proyecto.

Ejemplo de clave Jira:

```txt
INV-15
```

## Tipos de commit

El campo `<tipo>` debe indicar qué clase de cambio se realizó.

Los tipos permitidos son:

* `feat`: agrega una nueva funcionalidad.
* `fix`: corrige un bug o error.
* `docs`: modifica documentación.
* `test`: agrega o modifica tests.
* `refactor`: modifica código sin agregar funcionalidad ni corregir bugs.
* `build`: modifica dependencias, configuración de build o instalación.
* `ci`: modifica configuración de integración continua, como GitHub Actions.
* `style`: modifica formato, espacios, tabulaciones o estilo sin cambiar lógica.
* `perf`: mejora rendimiento.

## Descripción del cambio

La descripción debe:

* estar escrita en inglés técnico;
* ser breve y clara;
* indicar qué se hizo;
* no terminar con punto;
* intentar no superar los 50 caracteres.

## Ejemplos válidos

```txt
docs(INV-15): add git workflow documentation
feat(INV-20): add measurement ingestion endpoint
fix(INV-22): handle invalid sensor payload
test(INV-30): add backend unit tests
ci(INV-31): add frontend workflow
```

## Ejemplos inválidos

```txt
update files
arreglo cosas
feat: add endpoint
docs: [INV-15] add documentation.
```

Los ejemplos anteriores son inválidos porque no respetan el formato definido o no incluyen correctamente la clave Jira.

## Regla para Pull Requests

Los commits incluidos en un Pull Request deben respetar esta convención.

Además, el Pull Request debe referenciar la clave Jira relacionada con el trabajo realizado.
