# TP Testing

## Integrantes
- Martina Ciruzzi
- Juan Ignacio Urrestarazu
- Desiderio Jesus Valla Tello
- Hebe Azul Usandivaras
- Ramiro Javier Baigorria
- Franco Leonel Montes

---

# Tareas a Realizar

Tests requeridos: Parte 2.1
1. Costo por debajo del umbral → shouldAlertCost retorna false
2. Costo exactamente en el umbral → definir y documentar el comportamiento esperado.
3. Costo por encima del umbral → shouldAlertCost retorna true .
4. ETA por debajo del umbral → shouldAlertETA retorna false .
5. ETA por encima del umbral → shouldAlertETA retorna true .

Tests requeridos: Parte 2.2 
1. Usando AlwaysAlertService : verificar que AlertObserver loggea cuando se notifica con cualquier snapshot .
2. Usando un fake que siempre retorna false : verificar que AlertObserver no loggea nada .

Tests requeridos: Parte 2.3
1. Verificar que cuando shouldAlertCost retorna true , AlertObserver llama al logger con logWarning .
2. Verificar que cuando shouldAlertETA retorna true , AlertObserver llama al logger con logError .
3. Verificar que cuando ambas condiciones son false , el logger no es llamado en ningún momento.

MODIFICAR EL "AlertObserver.java"

MODIFICAR el "ReadMe"

HACER "FakeAlertService.java"

---

# Evidencia y Resultados

HACER nuevo "Evidencia y Resultados"

---

## Video de ejecución

HACER nuevo video de Ejecucion

---

## Ejemplo de ejecución


HACER nuevo ejemplo de Ejecucion
