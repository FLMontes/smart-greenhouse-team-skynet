# Algoritmos del Sistema

Este documento describe los algoritmos principales utilizados por el sistema de Invernadero Inteligente.
Los algoritmos trabajan sobre mediciones registradas en PostgreSQL y permiten detectar condiciones fuera de lo normal para generar alertas y activar actuadores.

---

## Criterio general de evaluación

Las alertas físicas y visuales no deben generarse directamente a partir del dato crudo recibido por la API.

Cada medición recibida debe ser registrada y luego evaluada por el algoritmo correspondiente. Los componentes encargados de activar alertas, como observadores de hardware o de dashboard, deben reaccionar al resultado de la evaluación del algoritmo y no únicamente al valor instantáneo recibido.

De esta forma, el sistema evita activar alarmas falsas por variaciones bruscas o picos aislados de los sensores.


## ALG-01 - Suavizado de temperatura

### Propósito

Calcular el promedio de las últimas N mediciones de temperatura registradas en PostgreSQL para obtener un indicador térmico más estable.

Este algoritmo permite suavizar variaciones bruscas de los sensores y evitar que se activen alarmas falsas por un pico aislado.

### Entradas

* Últimas N mediciones de temperatura registradas en PostgreSQL.
* Cantidad N de mediciones a considerar.
* Parámetros establecidos para evaluar la temperatura.

### Proceso

1. El sistema consulta en PostgreSQL las últimas mediciones de temperatura.
2. Si existen al menos N mediciones, calcula el promedio usando las últimas N.
3. Si existen menos de N mediciones, calcula el promedio usando todas las mediciones disponibles.
4. El promedio obtenido se utiliza como indicador actual de temperatura.

### Salidas

* Promedio de temperatura calculado.
* Indicador actual de temperatura.

### Escenarios considerados

#### Cálculo del promedio con suficientes datos

Dado que existen al menos N mediciones registradas en PostgreSQL,
cuando el sistema ejecuta el algoritmo de suavizado de indicadores,
entonces calcula el promedio de las últimas N mediciones y lo usa como indicador actual.

#### Cálculo del promedio con datos insuficientes

Dado que existen menos de N mediciones registradas en PostgreSQL,
cuando el sistema ejecuta el algoritmo de suavizado de indicadores,
entonces calcula el promedio con todas las mediciones disponibles y evita alarmas por picos aislados.

### Relación con PostgreSQL

El algoritmo utiliza mediciones de temperatura previamente registradas en PostgreSQL.

---

## ALG-02 - Evaluación de humedad y alerta de riego

### Propósito

Evaluar el promedio de medición de humedad registrado en PostgreSQL para detectar sequedad y activar las alertas correspondientes.

Cuando se detecta sequedad, el sistema debe encender el LED RGB en color BLANCO, activar la alarma y encender el regador.

### Entradas

* Mediciones de humedad registradas en PostgreSQL.
* Parámetros establecidos para determinar sequedad.

### Proceso

1. El sistema consulta en PostgreSQL las mediciones de humedad.
2. Calcula el promedio de medición de humedad.
3. Compara el promedio con los parámetros establecidos.
4. Si el promedio indica sequedad, activa la alerta correspondiente.
5. Si el promedio no indica sequedad, mantiene el sistema en estado normal para humedad.

### Salidas

* Resultado de evaluación de humedad.
* Estado del LED RGB.
* Estado de la alarma.
* Estado del regador.

### Escenarios considerados

#### Activar alarma cuando el promedio de medición indica sequedad

Dado el promedio de medición de humedad registrada,
cuando el sistema obtiene el promedio acorde a los parámetros establecidos,
entonces se enciende el LED RGB en BLANCO, se activa la alarma y se enciende el regador.

#### No activar alarmas cuando el promedio de medición no indica sequedad

Dado que hay al menos una medición de humedad registrada,
cuando el sistema obtiene el promedio acorde a los parámetros establecidos,
entonces se activa el LED RGB en VERDE pero no se activa la alarma ni el regador.

### Relación con PostgreSQL

El algoritmo utiliza mediciones de humedad almacenadas en PostgreSQL.

---

## ALG-03 - Evaluación de temperatura del ambiente y alerta de calefacción

### Propósito

Evaluar la temperatura del ambiente utilizando mediciones registradas en PostgreSQL para detectar sobreenfriamiento.

Cuando se detecta sobreenfriamiento, el sistema debe activar el LED RGB en color AZUL, la alarma y una resistencia que calefaccione.

### Entradas

* Mediciones de temperatura registradas en PostgreSQL.
* Indicador de temperatura calculado por el algoritmo de suavizado.
* Parámetros establecidos para detectar sobreenfriamiento.

### Proceso

1. El sistema consulta en PostgreSQL las mediciones de temperatura.
2. El sistema obtiene el indicador de temperatura suavizado.
3. Compara el indicador de temperatura con los parámetros establecidos.
4. Si detecta sobreenfriamiento, genera el resultado de alerta correspondiente.
5. Los observadores del sistema reaccionan al resultado de la evaluación.
6. Si no detecta sobreenfriamiento, mantiene el sistema en estado normal para temperatura baja.

### Salidas

* Resultado de evaluación de temperatura.
* Estado del LED RGB.
* Estado de la alarma.
* Estado de la resistencia.

### Escenarios considerados

#### Activar alarma cuando la evaluación indica sobreenfriamiento

Dado un indicador de temperatura calculado a partir de mediciones registradas,
cuando el sistema detecta sobreenfriamiento,
entonces se activa el LED RGB en AZUL, la alarma y se enciende una resistencia que calefaccione.

#### No activar alarma cuando la evaluación no indica sobreenfriamiento

Dado un indicador de temperatura calculado a partir de mediciones registradas,
cuando el sistema obtiene un valor acorde a los parámetros establecidos,
entonces se activa el LED RGB en VERDE pero no se activa la alarma ni la resistencia.

### Relación con PostgreSQL

El algoritmo utiliza mediciones de temperatura almacenadas en PostgreSQL y trabaja sobre el indicador calculado, no directamente sobre el dato crudo recibido por la API.

---

## ALG-04 - Evaluación de CO2 del ambiente

### Propósito

Evaluar las últimas mediciones de CO2 registradas en PostgreSQL para detectar alta concentración de CO2 en el aire.

Cuando se detecta alta concentración de CO2, el sistema debe activar una alerta visual, activar el buzzer y encender el ventilador.

### Entradas

* Últimas mediciones de CO2 registradas en PostgreSQL.
* Parámetros establecidos para determinar alta concentración de CO2.

### Proceso

1. El sistema consulta en PostgreSQL las últimas mediciones de CO2.
2. Calcula o evalúa el promedio de mediciones de CO2.
3. Compara el promedio con el parámetro establecido.
4. Si el promedio indica alta concentración de CO2, activa la alerta correspondiente.
5. Si el promedio se encuentra estable, mantiene apagados los actuadores de CO2.

### Salidas

* Resultado de evaluación de CO2.
* Estado del LED RGB.
* Estado del buzzer.
* Estado del ventilador.

### Escenarios considerados

#### Activar alarma cuando el promedio indique alta concentración de CO2

Dado el promedio de mediciones de alta concentración de CO2,
cuando el promedio se pase del parámetro máximo establecido,
entonces el LED RGB físico se enciende en color ROSA, se activa el buzzer y se enciende el ventilador.

#### No activar alarma cuando el promedio no indique alta concentración de CO2

Dado el promedio de mediciones de alta concentración de CO2,
cuando el promedio se encuentre estable respecto del máximo establecido,
entonces se enciende el LED RGB en VERDE y los actuadores de CO2 permanecen apagados.

### Relación con PostgreSQL

El algoritmo utiliza mediciones de CO2 almacenadas en PostgreSQL.

---

## ALG-05 - Evaluación de iluminación del ambiente y alerta de claridad

### Propósito

Evaluar el promedio de medición de iluminación registrado en PostgreSQL para detectar escasa luz.

Cuando se detecta escasa luz, el sistema debe encender el LED RGB en color AMARILLO, activar una alarma y encender una tira de LEDs seteables.

### Entradas

* Mediciones de iluminación registradas en PostgreSQL.
* Parámetros establecidos para determinar escasa luz.

### Proceso

1. El sistema consulta en PostgreSQL las mediciones de iluminación.
2. Calcula el promedio de medición de iluminación.
3. Compara el promedio con los parámetros establecidos.
4. Si el promedio indica escasa luz, activa la alerta correspondiente.
5. Si el promedio no indica escasa luz, mantiene apagados los actuadores de iluminación.

### Salidas

* Resultado de evaluación de iluminación.
* Estado del LED RGB.
* Estado de la alarma.
* Estado de la tira LED.

### Escenarios considerados

#### Activar alarma cuando el promedio de medición indica escasa luz

Dado que el promedio de medición indica escasa luz,
cuando el sistema evalúa el promedio de medición de iluminación,
entonces el LED RGB físico se enciende en color AMARILLO, se activa la alarma y la tira LED se enciende para compensar la falta de iluminación.

#### No activar alarma cuando el promedio de medición no indica escasa luz

Dado que el promedio de medición no indica escasa luz,
cuando el sistema evalúa el promedio de medición de iluminación,
entonces se enciende el LED RGB en VERDE y los actuadores de iluminación permanecen apagados.

### Relación con PostgreSQL

El algoritmo utiliza mediciones de iluminación almacenadas en PostgreSQL.
