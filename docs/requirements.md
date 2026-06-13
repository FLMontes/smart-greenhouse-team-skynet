# Requerimientos del Sistema

## Requerimientos funcionales

### RF-01 - Captura de variables ambientales y estado del pulsador

El sistema debe recopilar periódicamente, mediante la ESP32, mediciones de CO2, intensidad de luz, temperatura y humedad, junto con el estado del pulsador físico del invernadero.

### RF-02 - Registro histórico de mediciones

El sistema debe registrar las mediciones recibidas, asociarlas a una marca de tiempo y almacenarlas en PostgreSQL para su consulta posterior.

### RF-03 - Consulta de mediciones

El sistema debe permitir consultar tanto las mediciones históricas como la última medición registrada de cada variable ambiental.

### RF-04 - Configuración de parámetros ambientales

El sistema debe utilizar parámetros mínimos y máximos definidos para evaluar las variables ambientales monitoreadas.

### RF-05 - Evaluación de condiciones ambientales

El sistema debe evaluar las mediciones recibidas para determinar si cada variable se encuentra dentro de los parámetros configurados.

### RF-06 - Generación de alertas

El sistema debe generar alertas cuando una variable ambiental se encuentre fuera de los parámetros permitidos.

### RF-07 - Registro y consulta de alertas

El sistema debe generar alertas y permitir consultar las alertas activas.

### RF-08 - Activación de alertas físicas

El sistema debe activar alertas físicas mediante buzzer y LED RGB cuando se detecte una condición fuera de rango.

### RF-09 - Silenciado de alarma sonora

El sistema debe permitir registrar la solicitud de silenciado del buzzer mediante un pulsador físico informado por la ESP32.

### RF-10 - Control de actuadores

El sistema debe activar o desactivar los actuadores correspondientes según la condición detectada, como resistencia térmica, regador, ventilador o tira LED.

### RF-11 - Estado normal del sistema

Cuando todas las variables se encuentren dentro de los parámetros permitidos, el sistema debe indicar estado correcto, apagar alertas y mantener apagados los actuadores correctivos.

### RF-12 - Configuración de colores de alerta

El sistema debe representar las condiciones ambientales mediante la siguiente configuración de colores:

| Sensor      | Condición   | Color LED |
| ----------- | ----------- | --------- |
| TEMPERATURA | MUCHO CALOR | ROJO      |
| TEMPERATURA | MUCHO FRÍO  | AZUL      |
| CO2         | MUCHO CO2   | ROSA      |
| HUMEDAD     | MUY SECO    | BLANCO    |
| LUZ         | MUY OSCURO  | AMARILLO  |
| DEFAULT     | CORRECTO    | VERDE     |

### RF-13 - Dashboard de monitoreo

El sistema debe contar con un dashboard web que permita visualizar las lecturas actuales, la fecha y hora de actualización, el estado de alertas y el estado de los actuadores.

### RF-14 - Visualización histórica

El dashboard debe permitir visualizar datos históricos mediante tablas o gráficos de las variables ambientales monitoreadas.

### RF-15 - Actualización automática del dashboard

El dashboard debe actualizar las mediciones y alertas sin requerir recarga manual del navegador, mediante consultas periódicas al backend.

### RF-16 - Consistencia visual entre hardware y frontend

El frontend debe utilizar la misma configuración de colores definida para el LED RGB físico.

### RF-17 - Algoritmos sobre mediciones

El sistema debe ejecutar algoritmos sobre mediciones almacenadas en PostgreSQL para calcular promedios, suavizar variaciones bruscas y evaluar condiciones ambientales.

### RF-18 - Evaluación por tipo de variable

El sistema debe contar con evaluaciones específicas para temperatura, humedad, CO2 e iluminación.

### RF-19 - Comunicación mediante API REST

El sistema debe exponer endpoints REST para registrar mediciones, consultar mediciones, consultar alertas, consultar estado de actuadores y consultar resultados de algoritmos.

### RF-20 - Comunicación entre componentes

La ESP32 debe comunicarse con el backend mediante REST, y el frontend debe comunicarse con el backend mediante REST, sin comunicarse directamente con la ESP32.

### RF-21 - Notificación de eventos del sistema

El sistema debe notificar a los componentes interesados cuando se genere una alerta o una actualización relevante.

### RF-22 - Uso de patrones de diseño

El sistema debe implementar Observer para la notificación de eventos, Strategy para la evaluación de condiciones ambientales y un tercer patrón de diseño para organizar la creación o el acceso a objetos del sistema.

---

## Requerimientos no funcionales

### RNF-01 - Frecuencia mínima de lectura

La ESP32 debe realizar lecturas de CO2, luz, temperatura y humedad al menos una vez cada 10 segundos durante la operación normal del sistema.

### RNF-02 - Tiempo máximo de envío de mediciones

Cada medición tomada por la ESP32 debe enviarse al backend en un tiempo menor o igual a 5 segundos desde el momento de su lectura, siempre que exista conexión disponible.

### RNF-03 - Tiempo máximo de persistencia

El backend debe guardar cada medición válida en PostgreSQL en un tiempo menor o igual a 2 segundos desde su recepción.

### RNF-04 - Tiempo máximo de actualización del dashboard

El dashboard debe reflejar una nueva medición o alerta en un tiempo menor o igual a 5 segundos desde que el backend recibe la información, en condiciones normales de conexión local.

### RNF-05 - Tiempo máximo de activación física

Cuando el resultado de la evaluación indique una condición fuera del parámetro permitido, las alertas físicas y los actuadores asociados deben activarse en un tiempo menor o igual a 3 segundos desde la detección de la anomalía.

### RNF-06 - Tiempo máximo de consulta histórica

El backend debe responder consultas históricas en un tiempo menor o igual a 3 segundos para consultas de hasta 1000 mediciones.

### RNF-07 - Tiempo máximo de cálculo de algoritmos

Cada algoritmo que utilice mediciones almacenadas en PostgreSQL debe ejecutarse en un tiempo menor o igual a 3 segundos para un conjunto de hasta 1000 mediciones.

### RNF-08 - Disponibilidad del backend

Durante una demo o período de operación local, el backend debe mantenerse disponible al menos el 99% del tiempo, excluyendo reinicios manuales o mantenimiento planificado.

### RNF-09 - Conservación de datos

El sistema debe conservar en PostgreSQL el 100% de las mediciones válidas recibidas por el backend durante la ejecución de la demo.

### RNF-10 - Validación de datos de entrada

El backend debe rechazar el 100% de las mediciones que no cumplan con los campos obligatorios o rangos definidos por el contrato de la API, respondiendo con un código HTTP 400 (Bad Request) y evitando su persistencia en la base de datos.

### RNF-11 - Compatibilidad del frontend

El dashboard web debe poder ejecutarse correctamente en al menos dos navegadores modernos: Google Chrome y Microsoft Edge.

### RNF-12 - Resolución mínima de pantalla

El dashboard debe poder visualizarse correctamente en pantallas con resolución mínima de 1366x768 píxeles.

### RNF-13 - Uso máximo de memoria

Durante la operación normal de la demo, el backend no debe superar los 512 MB de memoria RAM.

### RNF-14 - Recuperación ante reinicio

Después de reiniciar el backend, el sistema debe volver a estar disponible en un tiempo menor o igual a 30 segundos.

### RNF-15 - Datos históricos consultables

El sistema debe permitir consultar al menos las últimas 1000 mediciones almacenadas en PostgreSQL desde el frontend.

### RNF-16 - Consistencia visual de alertas

El color mostrado en el frontend para una alerta debe coincidir con el color definido para el LED RGB físico en el 100% de los tipos de alerta definidos.

### RNF-17 - Clientes simultáneos

El sistema debe soportar al menos 3 clientes conectados simultáneamente al dashboard durante la demo sin superar los tiempos máximos de actualización y consulta definidos.

### RNF-18 - Ejecución con Docker

El sistema debe poder iniciar sus componentes principales mediante Docker en un tiempo menor o igual a 2 minutos en una computadora de desarrollo del equipo.

### RNF-19 - Trazabilidad documental

El 100% de los requerimientos implementados debe poder asociarse con al menos una historia de Jira, caso de uso o tarea del proyecto.

### RNF-20 - Logging

El sistema debe registrar el 100% de los errores del backend y de los eventos de alerta mediante un mecanismo de logging, incluyendo fecha, hora y tipo de evento.
