# Gestión del Proyecto

## Proyecto Jira

El equipo utiliza Jira Web para gestionar el trabajo del proyecto final.

* Nombre del proyecto: Invernadero Inteligente
* URL del proyecto Jira: `https://mi-team-skynet.atlassian.net/jira/software/projects/INV/boards/34/backlog`
* Prefijo de claves Jira: `INV`

## Uso de Jira

En Jira se registran:

* historias de usuario;
* tareas técnicas;
* bugs, si corresponde;
* seguimiento del estado de cada actividad.

Cada historia o tarea debe tener una clave Jira identificable, por ejemplo `INV-15`.

## Relación entre Jira y GitHub

Todo trabajo relacionado con una historia o tarea debe poder trazarse desde GitHub hacia Jira.

Para eso:

* las ramas deben incluir la clave Jira cuando corresponda;
* los commits integrados deben incluir la clave Jira;
* los Pull Requests deben referenciar la clave Jira correspondiente.

Ejemplo:

* Tarea Jira: `INV-15 - Definir workflow Git`
* Rama: `feature/INV-15-project-planning-and-design`
* Commit: `docs(INV-15): add git workflow documentation`
* Pull Request: `INV-15 - Add initial project documentation`

## Pull Requests

Todo trabajo ligado a historias o tareas debe integrarse mediante Pull Request hacia la rama `develop`.

Cada Pull Request debe:

* referenciar al menos una clave Jira;
* recibir al menos dos aprobaciones antes del merge;
* pasar las validaciones requeridas del proyecto.

Si el equipo tuviera solo dos integrantes, se requerirá una aprobación de la otra persona.

## Aporte Equilibrado

El aporte del equipo se considerará equilibrado si cada integrante participa de forma verificable en al menos dos de las siguientes actividades:

* autoría de Pull Requests integradas;
* revisión y aprobación de Pull Requests;
* commits sustantivos;
* tareas asignadas y cerradas en Jira;
* participación en componentes o historias relevantes del sistema.

El aporte no se evaluará únicamente por cantidad de commits, sino por la evidencia conjunta de Jira, GitHub, Pull Requests y revisiones.
