# T19 — Justificación de patrones de diseño

## Introducción

En el proyecto Smart Greenhouse se utilizan los patrones de diseño Observer, Strategy y Factory para separar responsabilidades, reducir el acoplamiento entre componentes y facilitar la incorporación de nuevas funcionalidades.

Cada patrón resuelve un problema específico dentro del sistema:

* Strategy encapsula los distintos algoritmos de análisis ambiental.
* Observer permite que varios componentes reaccionen ante los resultados del análisis.
* Factory centraliza la creación de los sensores utilizados por el firmware.

---

## Strategy

El patrón Strategy se utiliza para encapsular los diferentes algoritmos encargados de analizar las mediciones ambientales.

### Clases involucradas

* `EnvironmentalAnalyzer`: contexto.
* `IAlgorithmStrategy`: interfaz común de las estrategias.
* `TemperatureStrategy`: análisis de temperatura.
* `HumidityStrategy`: análisis de humedad.
* `CO2Strategy`: análisis de dióxido de carbono.
* `LightStrategy`: análisis de luminosidad.
* `MovingAverageStrategy`: cálculo del promedio móvil.

### Funcionamiento

`EnvironmentalAnalyzer` recibe una estrategia que implementa la interfaz `IAlgorithmStrategy` y delega en ella el procesamiento de la medición.

Cada estrategia aplica una lógica diferente, pero todas respetan el mismo contrato. Esto permite que el analizador utilice distintos algoritmos sin depender directamente de sus implementaciones concretas.

### Justificación

La utilización de Strategy permite:

* Separar cada algoritmo en una clase independiente.
* Cambiar el algoritmo utilizado sin modificar el analizador.
* Agregar nuevas estrategias sin alterar las existentes.
* Evitar grandes estructuras condicionales.
* Probar cada algoritmo de manera aislada.
* Mantener el código organizado y extensible.

Este patrón resulta adecuado porque el sistema procesa diferentes tipos de mediciones y cada una requiere una lógica de análisis específica.

---

## Observer

El patrón Observer se utiliza para notificar a diferentes componentes cuando se completa el análisis de una nueva medición ambiental.

### Clases involucradas

* `EnvironmentalAnalyzer`: sujeto u observable.
* `IObserver`: interfaz común de los observadores.
* `WebDashboardObserver`: prepara los datos procesados para la visualización en el dashboard.
* `HardwareAlarmObserver`: genera comandos físicos para el hardware a partir de los resultados del análisis.

### Funcionamiento

Los observadores se registran en `EnvironmentalAnalyzer`.

Cuando el analizador recibe una nueva medición, ejecuta las estrategias de análisis correspondientes y obtiene los resultados del procesamiento ambiental.

Una vez finalizado el análisis, `EnvironmentalAnalyzer` notifica a todos los observadores registrados.

Cada observador consulta el estado actual del analizador y ejecuta una acción diferente según su responsabilidad.

Por ejemplo:

* `WebDashboardObserver` prepara la información procesada para que pueda ser consumida por el frontend.
* `HardwareAlarmObserver` traduce los resultados del análisis en comandos físicos para los actuadores del firmware.

Si el análisis detecta una temperatura elevada, `HardwareAlarmObserver` puede generar un estado de actuadores que encienda el ventilador, active el buzzer y configure el color RGB en rojo (`#FF0000`).

Los observadores no representan conexiones directas con el frontend o con la ESP32. Son componentes internos que reaccionan al resultado del análisis y dejan disponible la información para que los controladores o servicios correspondientes la expongan.

### Justificación

La utilización de Observer permite:

* Desacoplar el análisis ambiental de las acciones posteriores.
* Incorporar nuevos observadores sin modificar el analizador.
* Ejecutar varias reacciones ante una misma medición.
* Separar la preparación de datos para el dashboard de la generación de comandos físicos.
* Mantener `EnvironmentalAnalyzer` enfocado en analizar mediciones y notificar resultados.
* Facilitar las pruebas mediante observadores simulados.
* Mantener una arquitectura orientada a eventos.

Este patrón resulta adecuado porque una misma medición procesada puede provocar distintas acciones dentro del sistema.

---

## Factory

El patrón Factory se utiliza en el firmware para centralizar la creación de los diferentes sensores físicos utilizados por la ESP32.

### Clases involucradas

* `SensorFactory`: fábrica de sensores.
* `Sensor`: interfaz común de sensores físicos.
* `TemperatureHumiditySensor`: sensor digital HTU21D para temperatura y humedad.
* `LightSensor`: sensor digital BH1750 para luminosidad.
* `Co2Sensor`: sensor analógico MQ-135 para dióxido de carbono.
* `ButtonSensor`: botón físico de silencio.

### Funcionamiento

`SensorFactory` expone métodos de creación específicos para cada sensor físico utilizado por el firmware:

* `createTemperatureHumiditySensor()`
* `createLightSensor()`
* `createCo2Sensor()`
* `createButtonSensor()`

`SensorService` utiliza la fábrica para obtener las instancias de sensores y coordinar su inicialización y lectura.

El resto del firmware trabaja con los sensores mediante la interfaz común `Sensor`, sin depender directamente de las clases concretas ni de la lógica de construcción de cada dispositivo.

Esta implementación centraliza la creación de sensores en una única clase y separa la lógica de instanciación de la lógica de lectura.

### Justificación

La utilización de Factory permite:

* Centralizar la creación de sensores.
* Reducir la dependencia hacia clases concretas.
* Separar la creación de objetos de su utilización.
* Mantener `SensorService` enfocado en coordinar lecturas.
* Agregar nuevos sensores modificando la fábrica y no la lógica principal del firmware.
* Integrar el botón de silencio como un sensor físico más del sistema.

`SensorFactory` debe crear sensores del firmware y no estrategias del backend.

Las estrategias de análisis pertenecen al patrón Strategy y son utilizadas por `EnvironmentalAnalyzer`.

---

## Relación entre los patrones

Los tres patrones trabajan de manera complementaria dentro del sistema.

1. El firmware utiliza `SensorFactory` para crear el sensor correspondiente.

2. Los sensores obtienen las mediciones ambientales y el firmware las envía al backend.

3. `EnvironmentalAnalyzer` utiliza una implementación de `IAlgorithmStrategy` para analizar la medición.

4. Una vez obtenido el resultado, `EnvironmentalAnalyzer` notifica a los componentes que implementan `IObserver`.

5. Cada observador ejecuta la acción correspondiente, como preparar datos para el dashboard o generar comandos de actuadores para el firmware.

---

## Conclusión

El patrón Strategy permite separar e intercambiar los algoritmos de análisis ambiental.

El patrón Observer permite que distintos componentes reaccionen ante los resultados sin generar dependencias directas con el analizador.

El patrón Factory centraliza la creación de sensores y evita que el firmware dependa de implementaciones concretas.

La combinación de estos patrones mejora la mantenibilidad, extensibilidad, organización y capacidad de prueba del sistema Smart Greenhouse.