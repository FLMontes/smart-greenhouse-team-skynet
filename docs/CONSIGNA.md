# Consigna — Proyecto final IoT

**Universidad Nacional de Córdoba** · Facultad de Ciencias Exactas, Físicas y Naturales · Ingeniería de Software

Este documento define **obligaciones** y **criterios de aceptación** del trabajo final. Los **requisitos funcionales y no funcionales** los plantea **el equipo** (Jira, `docs/`, entregas de la materia, etc.) con la libertad de forma y granularidad que corresponda, siempre que el resultado sea **compatible** con todo lo exigido aquí: esta consigna actúa como **conjunto de restricciones (constraints)**.

---

## Resumen rápido

| Ámbito | Qué se exige |
|--------|----------------|
| Proceso | **Jira en la web** con **proyecto nuevo** del equipo; PRs obligatorias para trabajo de historias; **mínimo 2 aprobaciones** por PR; cada commit integrado con **clave de historia** (`KEY-NNN`); aporte de código **parejo**; **workflow Git documentado** (se recomienda [Gitflow — Atlassian](https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow)); **convención de commits documentada** (se recomienda [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0-beta.4/)); **hooks** `pre-commit` y `pre-push` instalables por el equipo; **linter** en repo y ejecutado en **pre-commit** (no hace falta repetir lint en Actions) |
| ESP32 | Recopilación de eventos del entorno (botones, sensores, potenciómetros, teclados, etc.); **≥3 canales o modalidades** distintas en la demo |
| Backend | **Un solo framework** elegido por el equipo; **≥3 algoritmos** distintos sobre datos persistidos o ligados a PostgreSQL; validación y códigos HTTP coherentes |
| Datos | Todo lo que ingresa desde la ESP32 debe **guardarse en histórico** en **PostgreSQL** |
| Integración | **REST** entre ESP32↔backend y frontend↔backend; el **frontend no puede** llamar a la ESP32 **directamente** |
| Contratos | API acordada y **documentada antes** (p. ej. OpenAPI en el repo) |
| Frontend | Consulta **histórica** (tablas u otro formato estructurado) y visualización **en vivo** de sensores y salidas de algoritmos (gráficos, texto o tablas a criterio del equipo) |
| Patrones | **Observer** obligatorio (**≥2** estructuras concretas); **Strategy** obligatorio; **≥1 tercer patrón GoF** a elección del grupo, justificado por escrito |
| Reactividad | No usar **solo** `useState` (u otro estado reactivo local) como mecanismo único para propagar datos de dominio: las actualizaciones relevantes **deben pasar** por las abstracciones Observer definidas |
| Idioma | **Código y comentarios en inglés**; informes y Jira pueden estar en español |
| GitHub Actions | Solo **GitHub Actions**: **`.github/workflows/`**; en **PRs** hacia integración: como mínimo **build + tests unitarios** del **backend** elegido; **frontend:** **build + tests unitarios**; **firmware:** **`pio test`** (tests unitarios PlatformIO). **No** es obligatorio ejecutar el **linter** otra vez en el pipeline (alcanza con **pre-commit**). Jobs requeridos / branch protection documentados |
| Testing | **Backend, frontend y firmware:** solo **tests unitarios**; se **valoran** en backend tests de integración/API. Los comandos de **test** deben coincidir entre **GitHub Actions**, **pre-push** y documentación local |
| Diseño UML (previo al código) | **Antes** de implementar funcionalidad sustantiva: **(1)** diagrama de **componentes** (arquitectura); **(2)** diagrama de **clases** al menos del **backend**; **(3)** **uno o más** diagramas de **secuencia** para flujos relevantes. Fuente + vista exportada en `docs/` |

---

# 1. Requisitos del producto

## 1.1 Alcance general

El equipo debe desarrollar un sistema IoT funcional compuesto por:

* un **firmware para ESP32**,
* un **backend**,
* una **base de datos PostgreSQL**,
* y un **frontend**.

El sistema debe poder demostrarse de punta a punta en una demo funcional.

**Criterio de aceptación:** en la evaluación final debe poder observarse el flujo completo **ESP32 → backend → PostgreSQL → frontend**. 

---

## 1.2 Adquisición mínima desde ESP32

El firmware de ESP32 debe capturar información proveniente de **al menos tres modalidades de entrada distintas** del entorno o de interacción humana.

Se consideran modalidades distintas, por ejemplo:

* botón,
* sensor analógico,
* sensor digital,
* potenciómetro,
* teclado matricial,
* encoder,
* interruptor,
* sensor de distancia,
* sensor de temperatura.

No cuentan como modalidades distintas tres sensores del mismo tipo sin diferencia funcional clara.

**Criterio de aceptación:** durante la demo se observan **tres modalidades distintas** generando eventos o mediciones que ingresan efectivamente al sistema.

---

## 1.3 Persistencia histórica

Todo evento, telemetría o dato aceptado por el backend desde la ESP32 debe persistirse en **PostgreSQL** y permanecer disponible para consulta posterior, aun cuando el dispositivo esté apagado o desconectado.

**Criterio de aceptación:** tras desconectar la ESP32, el frontend o una herramienta de consulta puede recuperar datos previamente almacenados.

---

## 1.4 Frontend: histórico y visualización en vivo

El frontend debe ofrecer:

* al menos una vista de **consulta histórica** en formato estructurado (tabla, lista tabular, grilla u otro formato equivalente),
* y al menos una vista de **actualización en vivo** de:

  * datos provenientes de sensores o entradas,
  * y resultados generados por algoritmos del backend.

La actualización en vivo debe producirse **sin requerir refresh manual del navegador**.

**Criterio de aceptación:** durante la demo se puede consultar histórico y observar actualizaciones nuevas en la interfaz sin recargar manualmente la página. 

---

# 2. Requisitos de arquitectura y técnicos

## 2.1 Integración entre componentes

La comunicación entre componentes debe respetar estas reglas:

* **ESP32 ↔ backend:** mediante HTTP REST documentado.
* **Frontend ↔ backend:** mediante HTTP REST documentado.
* El **frontend no puede comunicarse directamente con la ESP32** para acceder a datos de dominio.

Para visualización en vivo, el equipo puede implementar polling u otro mecanismo equivalente desde/hacia el backend, siempre que el frontend no consuma datos directamente del dispositivo.

**Criterio de aceptación:** revisión de código, configuración y tráfico observable muestran que el navegador consume datos únicamente desde el backend.

---

## 2.2 Backend único

El equipo debe seleccionar **un único framework de backend** para la entrega evaluada. No debe ser necesario ejecutar múltiples backends alternativos para que el sistema funcione en la demo.

**Criterio de aceptación:** la documentación de ejecución y la demo utilizan un solo backend activo como parte del producto evaluado.

---

## 2.3 Contrato de API previo al consumo

Los endpoints utilizados por firmware y frontend deben estar definidos en un **contrato versionado** dentro del repositorio antes de ser consumidos por esos clientes.

Se admite OpenAPI u otro formato REST equivalente, siempre que documente al menos:

* ruta,
* método HTTP,
* payload esperado,
* respuesta esperada,
* códigos de error relevantes.

### Definición operativa de “antes de codear” en este contexto

Se considera que un contrato fue publicado **antes del consumo** si el repositorio muestra que la definición del endpoint fue incorporada antes del primer commit o PR que:

* invoque dicho endpoint desde frontend o firmware,
* dependa de sus campos,
* o implemente lógica cliente acoplada a ese contrato.

No se consideran “consumo” de contrato los siguientes casos:

* scaffolding inicial,
* mocks temporales aislados,
* pruebas exploratorias sin integración al flujo principal,
* estructura vacía de proyecto.

**Criterio de aceptación:** en el historial Git, la definición del contrato aparece antes del primer uso integrado de cada endpoint por parte de frontend o firmware. 

---

## 2.4 Algoritmos del backend

El backend debe implementar **al menos tres algoritmos distintos** aplicados sobre datos ingeridos, persistidos o consultados desde PostgreSQL.

### Definición operativa de “algoritmo distinto”

Para esta consigna, dos algoritmos se consideran distintos si cumplen simultáneamente estas condiciones:

1. realizan una **transformación, análisis o decisión diferente** sobre los datos,
2. producen una **salida semánticamente diferente** o persiguen un propósito distinto,
3. están implementados como unidades separables del código,
4. pueden identificarse y explicarse por separado en documentación o API.

Ejemplos válidos:

* media móvil,
* mediana móvil,
* Kalman,
* umbral con histéresis,
* detección de anomalías,
* agregación por ventana temporal,
* clasificación de estados.

Ejemplos que **no** cuentan como algoritmos distintos por sí solos:

* cambiar una constante de umbral,
* la misma lógica con distinto nombre,
* la misma operación aplicada a tres sensores distintos,
* tres variantes triviales sin diferencia funcional clara.

Cada algoritmo debe estar vinculado al flujo real del sistema, leyendo datos persistidos, procesándolos o generando resultados que el sistema exponga o utilice.

**Criterio de aceptación:** en el repositorio y/o la API se identifican tres algoritmos con nombre, propósito, entradas y salidas diferenciables, y con vínculo explícito al uso de PostgreSQL. 

---

## 2.5 Validación y calidad mínima del backend

El backend debe validar payloads de ingesta y responder con códigos HTTP coherentes.

En caso de payload inválido, el backend debe:

* responder con código **4xx**,
* y **no persistir** registros inválidos o corruptos.

**Criterio de aceptación:** una prueba manual con payload malformado genera respuesta 4xx y no deja basura persistida.

---

# 3. Requisitos de diseño y modelado

## 3.1 UML obligatorio previo al desarrollo sustantivo

Antes de implementar funcionalidad sustantiva del sistema, el equipo debe versionar en `docs/`:

1. un **diagrama de componentes**,
2. un **diagrama de clases** del backend,
3. uno o más **diagramas de secuencia** de flujos relevantes.

También debe incluirse la **fuente editable** del diagrama y, si la herramienta lo permite, una versión exportada legible.

### Definición operativa de “antes de codear”

A efectos de esta consigna, se considera **desarrollo sustantivo** cualquiera de los siguientes:

* implementación de lógica de dominio,
* creación de endpoints reales,
* persistencia real en base de datos,
* integración ESP32 ↔ backend,
* integración frontend ↔ backend,
* implementación de algoritmos del sistema,
* implementación de flujos de negocio o de UI ligados al producto final.

No se considera desarrollo sustantivo:

* creación del repositorio,
* configuración inicial del proyecto,
* scaffolding,
* instalación de dependencias,
* configuración de linters, hooks o CI,
* archivos placeholder,
* pruebas de concepto descartables no integradas al flujo principal.

**Criterio de aceptación:** los diagramas deben estar mergeados en el repositorio antes del primer PR o conjunto de commits que introduzca desarrollo sustantivo según esta definición. 

---

## 3.2 Coherencia entre diseño y sistema final

El diseño UML no tiene que coincidir línea por línea con el código final, pero sí debe reflejar razonablemente la arquitectura y los flujos centrales del sistema entregado.

**Criterio de aceptación:** el diagrama de componentes coincide con los componentes reales; el diagrama de clases representa el backend efectivamente implementado; y al menos un diagrama de secuencia cubre un flujo end-to-end real del sistema.

---

## 3.3 Patrones de diseño

El sistema debe evidenciar:

* al menos **dos implementaciones concretas** del patrón **Observer**,
* al menos una implementación del patrón **Strategy**,
* y al menos **un tercer patrón GoF**, justificado por escrito en `docs/`.

### Observer

Las dos implementaciones concretas de Observer deben corresponder a dos flujos o estructuras distinguibles del sistema.
No alcanza con duplicar nombres o clases vacías.

### Strategy

Se considera válida una Strategy si existen al menos **dos estrategias intercambiables reales** usadas por el sistema para resolver un mismo problema.

### Tercer patrón GoF

Debe identificarse explícitamente:

* nombre del patrón,
* clases o módulos que cumplen cada rol,
* justificación de por qué se usó.

**Criterio de aceptación:** el código y la documentación permiten identificar con claridad las implementaciones y roles de los tres patrones. 

---

# 4. Requisitos de proceso

## 4.1 Jira

El equipo debe utilizar **Jira web** con un proyecto del equipo para esta materia, salvo indicación distinta del cuerpo docente.

Debe existir:

* proyecto identificable,
* backlog con historias de usuario,
* tareas asociadas,
* seguimiento del trabajo.

En el repositorio debe figurar:

* nombre del proyecto,
* URL,
* prefijo de claves.

**Criterio de aceptación:** el proyecto existe, tiene historias y tareas visibles, y el repositorio lo referencia sin ambigüedad.

---

## 4.2 Pull requests y revisiones

Todo trabajo ligado a historias de usuario debe integrarse mediante **pull request** a una rama de integración protegida.

Cada PR debe:

* referenciar la clave Jira correspondiente,
* tener al menos **dos aprobaciones** de personas distintas del equipo antes del merge.

Si el equipo tiene solo **dos integrantes**, el mínimo exigido es **una aprobación** de la otra persona.

**Criterio de aceptación:** la configuración del repositorio y el historial de PRs muestran el cumplimiento de esta regla.

---

## 4.3 Aporte equilibrado

La participación del equipo debe ser auditable y distribuida.

Se considerará que hay aporte equilibrado si, salvo justificación documentada, **cada integrante** cumple al menos dos de estas condiciones a lo largo del cuatrimestre:

* participa como autor de PRs integradas,
* realiza revisiones aprobatorias,
* aporta commits sustantivos,
* tiene tareas asignadas y cerradas en Jira,
* aparece vinculado a componentes o historias relevantes del sistema.

No se evaluará el equilibrio únicamente por cantidad de commits.

**Criterio de aceptación:** la evidencia conjunta de Git, PRs y Jira permite verificar participación efectiva de todos los integrantes.

---

## 4.4 Workflow Git documentado

El equipo debe definir, documentar y cumplir un workflow de ramas en `docs/`.

La documentación debe indicar al menos:

* ramas de trabajo,
* rama de integración,
* forma de integración por PR,
* criterio de merge,
* relación entre ramas y Jira.

**Criterio de aceptación:** existe el documento y el uso real del repositorio coincide con lo documentado.

---

## 4.5 Convención de commits

El equipo debe usar una única convención de mensajes de commit y documentarla en `docs/`.

La documentación debe incluir:

* regla general,
* al menos tres ejemplos válidos,
* inclusión de clave Jira en cada ejemplo.

**Criterio de aceptación:** la convención está documentada y los commits integrados recientes la respetan de forma consistente.

---

# 5. Requisitos de calidad y automatización

## 5.1 Idioma

Deben estar en **inglés**:

* código fuente,
* comentarios,
* nombres de APIs internas,
* identificadores relevantes.

Pueden estar en español:

* documentación del equipo,
* Jira,
* informes,
* textos externos no técnicos si el producto lo requiere.

**Criterio de aceptación:** en la rama integrada no aparecen comentarios ni nombres técnicos de API en español, salvo strings visibles al usuario final cuando corresponda.

---

## 5.2 Testing

Deben existir **tests unitarios** para:

* backend,
* frontend,
* firmware.

Los tests deben:

* estar versionados en el repo,
* ser ejecutables localmente,
* coincidir con los comandos usados en CI,
* cubrir lógica relevante del sistema.

Se valoran tests de integración en backend, pero no son obligatorios.

**Criterio de aceptación:** existen tests visibles en los tres componentes y sus comandos están documentados y alineados con CI.

---

## 5.3 GitHub Actions

La automatización de CI debe implementarse exclusivamente con **GitHub Actions** en `.github/workflows/`.

En PRs hacia la rama de integración deben ejecutarse automáticamente como mínimo:

* **backend:** build + tests unitarios,
* **frontend:** build + tests unitarios,
* **firmware:** `pio test`.

**Criterio de aceptación:** un PR hacia integración muestra checks automáticos exitosos o fallidos para esos tres ámbitos.

---

## 5.4 Hooks Git

El repositorio debe permitir instalar y usar:

* `pre-commit`, que ejecute al menos el linter,
* `pre-push`, que ejecute al menos los tests exigidos o una verificación equivalente documentada.

Los hooks deben estar versionados o ser instalables desde el propio repositorio.

**Criterio de aceptación:** la documentación permite activar hooks en un clone limpio y el equipo puede demostrar su funcionamiento.

---

## 5.5 Linter

Debe existir al menos un linter configurado y versionado para cada componente activo del sistema entregado:

* backend,
* frontend,
* firmware.

El linter debe ejecutarse en `pre-commit`.

**Criterio de aceptación:** hay archivos de configuración del linter en el repo y el hook `pre-commit` lo invoca.

---

# 6. Evidencia mínima exigible para evaluación

Para considerar cumplida la entrega, debe existir evidencia observable de todos los puntos siguientes:

1. demo funcional **ESP32 → backend → PostgreSQL → frontend**,
2. tres modalidades de entrada distintas desde ESP32,
3. persistencia histórica en PostgreSQL,
4. frontend con histórico y actualización en vivo sin refresh manual,
5. un único backend activo,
6. contrato de API versionado antes del consumo,
7. tres algoritmos distintos correctamente identificables,
8. diagramas UML obligatorios versionados antes del desarrollo sustantivo,
9. dos Observers, una Strategy y un tercer patrón GoF identificables,
10. proyecto Jira con historias y tareas,
11. PRs con aprobaciones mínimas exigidas,
12. workflow Git documentado,
13. convención de commits documentada,
14. código y comentarios técnicos en inglés,
15. tests unitarios en backend, frontend y firmware,
16. GitHub Actions funcionando sobre PRs,
17. hooks instalables,
18. linter configurado y ejecutado en pre-commit.

---

# 7. Observaciones finales

Los RF y RNF del producto siguen siendo definidos por el equipo, pero no pueden contradecir ninguna de las restricciones de esta consigna.

Ante dudas de interpretación, prevalecerá siempre el criterio de:

* **evidencia observable en repositorio**,
* **trazabilidad en Git/Jira**,
* y **demostrabilidad en la evaluación**.


### Nice to have — Docker Compose en Actions (entorno tipo producción)

Cuando el proyecto esté **suficientemente avanzado o cerrado**, el equipo **puede** agregar un job opcional en GitHub Actions que:

- ejecute **`docker compose build`** para construir las **imágenes** desde los `Dockerfile` / manifiestos del repo,
- levante el stack con **`docker compose up`** (o variante adecuada para CI: perfil mínimo, `depends_on` + healthchecks, variables de entorno de “prod simulada”),

como **smoke** del despliegue integrado (backend, base, frontend en contenedores, según lo que el equipo haya dockerizado), acercándose a un **entorno tipo producción**.

Debe quedar **documentado** en `docs/` o **ABOUT** cómo reproducir el mismo flujo en la máquina local. **No** es requisito de aprobación del curso salvo que el docente lo indique aparte.

---

Para instalación y stack del repositorio base, ver [ABOUT.md](../ABOUT.md).
