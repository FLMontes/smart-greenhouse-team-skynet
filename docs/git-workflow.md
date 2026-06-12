# Workflow del Repositorio

## Rama de Integración

Nuestra rama base de integración será `develop`.

En esta rama se integran los cambios aprobados mediante Pull Request antes de pasar a una versión estable en `main` o `master`.

La rama `develop` se encuentra protegida mediante reglas de GitHub para evitar modificaciones directas. Nadie del equipo tiene permitido hacer push directo a esta rama.

## Ramas de Trabajo

Todo el desarrollo, incluyendo nuevas funcionalidades, arreglos y documentación, se realiza en ramas secundarias independientes que nacen a partir de `develop`.

## Nomenclatura de Ramas

Se utilizará un prefijo que indique el tipo de tarea, seguido de la clave Jira y una breve descripción.

Formato general:

```txt
<tipo>/INV-NNN-descripcion-breve
```

Ejemplos:

```txt
feature/INV-15-project-planning-and-design
feature/INV-20-measurement-ingestion
bugfix/INV-22-invalid-payload-error
```

## Relación entre Ramas y Jira

Para mantener la trazabilidad, toda rama de trabajo debe incluir en su nombre la clave del ticket de Jira correspondiente.

Ejemplo:

```txt
feature/INV-15-project-planning-and-design
```

Todos los commits realizados dentro de esa rama deben seguir la convención de commits definida en `docs/commit-convention.md`.

Ejemplo:

```txt
docs(INV-15): add git workflow documentation
```

## Forma de Integración por Pull Request

La única forma de integrar el trabajo de una rama secundaria hacia `develop` es abriendo una Pull Request en GitHub.

Cada Pull Request debe:

* apuntar a la rama `develop`;
* referenciar al menos una clave Jira;
* describir brevemente los cambios realizados;
* ser revisada antes del merge.

## Criterios de Merge

Para que una Pull Request pueda ser fusionada con `develop`, debe cumplir las siguientes condiciones:

* tener al menos dos aprobaciones de integrantes distintos del equipo antes del merge;
* pasar los checks automáticos requeridos por GitHub Actions;
* respetar la convención de commits;
* estar relacionada con una historia o tarea de Jira.

Si el equipo tuviera solo dos integrantes, se requerirá una aprobación de la otra persona.

## Rama Estable

La rama `main` o `master` se reserva para versiones estables o entregas.

Los cambios llegan a `main` o `master` únicamente después de haber sido integrados y validados en `develop`.
