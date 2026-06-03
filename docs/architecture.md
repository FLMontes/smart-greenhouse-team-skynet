# Arquitectura del Sistema

El sistema Invernadero Inteligente está compuesto por una ESP32, un backend, una base de datos PostgreSQL y un frontend web.

La ESP32 recopila mediciones del ambiente y el estado del pulsador físico, y envía los datos al backend mediante una API REST. El backend valida las mediciones recibidas, las almacena en PostgreSQL, ejecuta algoritmos de evaluación sobre los datos registrados y genera alertas o comandos para actuadores cuando corresponde.

El frontend se comunica únicamente con el backend mediante REST. Desde el dashboard, el usuario puede visualizar mediciones actuales, datos históricos, alertas activas, resultados de algoritmos y estado de actuadores.

## Componentes principales

- **ESP32**: recopila datos de sensores y envía mediciones al backend.
- **Backend**: recibe mediciones, valida datos, ejecuta algoritmos, gestiona alertas y expone endpoints REST.
- **PostgreSQL**: almacena mediciones históricas y datos necesarios para los algoritmos.
- **Frontend**: muestra información actual e histórica del invernadero mediante un dashboard web.
- **Actuadores**: responden a comandos generados por el backend, como buzzer, LED RGB, ventilador, regador, resistencia térmica y tira LED.

## Diagramas UML

### Diagrama de componentes

Representa los componentes principales del sistema y cómo se relacionan entre sí.

- [Diagrama de componentes](./diagrams/component_diagram.pdf)

### Diagrama de clases

Representa la estructura principal del backend, incluyendo clases, interfaces, estrategias, observadores y repositorios.

- [Diagrama de clases](./diagrams/class_diagram.pdf)

### Diagrama de secuencia

Representa el flujo principal de interacción entre ESP32, backend, PostgreSQL, observadores y frontend.

- [Diagrama de secuencia](./diagrams/sequence_diagram.pdf)