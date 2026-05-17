# TP Patrones de Diseño

## Integrantes
- Martina Ciruzzi
- Juan Ignacio Urrestarazu
- Desiderio Jesus Valla Tello
- Hebe Azul Usandivaras
- Ramiro Javier Baigorria

---

# Evidencia y Resultados

La aplicacion desarrollada implementa e integra los patrones de diseno **Strategy**, **Observer** y **Singleton** mediante un sistema de monitoreo de transportes.

El sistema permite cambiar el medio de transporte desde consola mientras el monitoreo continua ejecutandose en tiempo real. Ademas, los observers reciben actualizaciones automaticas del estado del transporte seleccionado y el logger singleton registra los eventos de la aplicacion.

Durante la ejecucion se verifica:

- compilacion correcta del proyecto,
- funcionamiento de las distintas estrategias de transporte,
- actualizacion automatica de observers,
- uso compartido de una unica instancia de `Logger`,
- cambio dinamico de estrategia sin detener el monitoreo.

---

## Video de ejecución

https://drive.google.com/file/d/1hrKAeL6b0Sn1tqE1J3NhZGczJM87DQgL/view?usp=sharing

---

## Ejemplo de ejecución

```txt
17/05/26 [INFO] Inicio de la aplicacion

Transporte cambiado a Taxi

17/05/26 [INFO] Transporte: Taxi
17/05/26 [INFO] Costo: $3491
17/05/26 [INFO] Distancia: 6 km
17/05/26 [INFO] ETA: 11 minutos

1 - Taxi
2 - Bus
3 - Bicicleta

2

Transporte cambiado a Colectivo

1 - Taxi
2 - Bus
3 - Bicicleta

0

Fin de la aplicacion
```