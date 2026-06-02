# Descripción del Sistema

## Invernadero Inteligente

El sistema consiste en un invernadero inteligente monitoreado mediante una placa ESP32, sensores ambientales, actuadores físicos, un backend, una base de datos PostgreSQL y un frontend web.

Su objetivo principal es permitir que el dueño del invernadero pueda conocer el estado del ambiente de sus plantas, detectar valores fuera de lo normal y recibir alertas cuando sea necesario.

## Componentes principales

### ESP32

La ESP32 se encarga de recopilar las mediciones del invernadero mediante sensores de CO2, luz, temperatura y humedad, junto con el estado del pulsador físico.

También participa en la activación de alertas físicas, como el buzzer, los LEDs RGB y los actuadores asociados a cada condición detectada.

### Entradas

Las entradas permiten obtener información del ambiente del invernadero:

* CO2.
* Luz.
* Temperatura.
* Humedad.
* Pulsador físico.

Estas mediciones se registran con marca de tiempo y se envían al backend para su procesamiento.

### Actuadores

El sistema utiliza actuadores físicos para responder ante valores fuera de lo normal:

* Buzzer para alertas sonoras.
* LEDs RGB para indicar el estado mediante colores.
* Resistencia térmica para mucho frío.
* Motor de 5V o regador para humedad muy baja.
* Ventilador para mucho CO2.
* Tira LED para iluminación insuficiente.

### Backend

El backend recibe las mediciones enviadas por la ESP32, valida los datos, los registra en PostgreSQL y ejecuta algoritmos sobre las mediciones almacenadas.

También expone la información necesaria para que el frontend pueda mostrar lecturas actuales, historial y alertas.

### PostgreSQL

PostgreSQL almacena las mediciones históricas del sistema.

Estos datos permiten consultar el historial del invernadero y ejecutar algoritmos basados en mediciones previas.

### Frontend

El frontend consiste en un dashboard web desde el cual el dueño del invernadero puede visualizar el estado del sistema.

El dashboard muestra lecturas actuales, datos históricos y alertas visuales. Además, utiliza la misma configuración de colores definida para el LED RGB físico.

## Flujo general del sistema

1. La ESP32 recopila mediciones de CO2, luz, temperatura y humedad.
2. Las mediciones se registran con marca de tiempo.
3. La ESP32 envía los datos al backend.
4. El backend valida las mediciones recibidas.
5. El backend guarda las mediciones válidas en PostgreSQL.
6. El backend ejecuta algoritmos sobre los datos registrados.
7. El frontend consulta la información al backend.
8. El dueño del invernadero visualiza lecturas actuales, historial y alertas.
9. Si el resultado de la evaluación indica una condición fuera de rango, el sistema notifica a los observadores y actualiza las alertas físicas y visuales correspondientes.

## Configuración de alertas

El sistema utiliza una configuración de colores para representar el estado de cada sensor:

| Sensor      | Condición   | Color LED |
| ----------- | ----------- | --------- |
| TEMPERATURA | MUCHO CALOR | ROJO      |
| TEMPERATURA | MUCHO FRÍO  | AZUL      |
| CO2         | MUCHO CO2   | ROSA      |
| HUMEDAD     | MUY SECO    | BLANCO    |
| LUZ         | MUY OSCURO  | AMARILLO  |
| DEFAULT     | CORRECTO    | VERDE     |

El frontend debe reflejar estas mismas alertas visuales para mantener coherencia entre el hardware y el dashboard.

## Alcance inicial

El sistema se enfoca en monitorear el ambiente del invernadero, registrar mediciones, mostrar información al usuario y activar alertas ante valores fuera de lo normal.

No se incluye en esta etapa inteligencia artificial, aplicación móvil nativa ni control remoto manual completo de los actuadores desde el frontend.
