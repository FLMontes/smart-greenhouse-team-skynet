# TP Requerimientos – Sistema de Control de Acceso

Trabajo practico de Ingeniería de Requerimientos sobre un sistema de acceso para instalaciones mediante pulseras o llaveros RFID.

El objetivo fue identificar y clasificar requerimientos, ademas de generar historias de usuario con criterios de aceptación en Jira.

## Integrantes

- Ramiro Javier Baigorria
- Martina Ciruzzi
- Franco Leonel Montes
- Juan Ignacio Urrestarazu
- Hebe Azul Usandivaras
- Desiderio Jesús Valla Tello

## Caso planteado

El sistema debe permitir que los socios ingresen utilizando una pulsera o llavero.

Cuando el usuario acerca el identificador:

- se identifica al socio,
- se verifica el estado de la cuota,
- se habilita o bloquea el acceso,
- y se registra el ingreso.

Ademas, el administrador puede acceder a reportes mensuales con la informacion de los ingresos.

## Requerimientos funcionales

- Lectura y Validación: El sistema debe leer la pulsera o llavero e identificar al socio en la base
de datos.
- Verificación de Pagos: El sistema debe chequear en tiempo real si el socio tiene la cuota al día.
- Control de Acceso: Si está al día, debe destrabar el molinete; si debe, queda bloqueado con luz
roja.
- Registro de Ingresos: El sistema debe guardar automáticamente el DNI y la hora exacta de
cada entrada permitida.
- Panel Administrativo: El dueño debe tener una interfaz web segura para ver el sistema.
- Reportes: El sistema debe permitir descargar un Excel mensual con todos los movimientos de
ingresos.

## Requerimientos no funcionales

- Validación y apertura instantánea: "debe ser instantáneo que el socio pase la tarjeta y pase".
- Disponibilidad: "el sistema no puede caerse nunca".
- Modo offline: "si cae internet, el molinete tiene que funcionar igual ".
- Seguridad: "tiene que ser super segura, que no me la hackee nadie".
- Restricción Económica: "mientras funcione y no me cueste una fortuna en licencias raras".
- Compatibilidad: "debe correr en el servidor linux que ya tenemos instalado".


## Requerimientos de usuario

- Quiero entrar al lugar usando una pulsera o llavero.
- Quiero que el acceso sea rápido.
- Quiero descargar un reporte mensual de ingresos.
- Quiero que el sistema sea seguro

## Requerimientos de sistema

- El sistema debe consultar la base de datos de socios mediante el identificador leído: "el sistema
tiene que leer eso, buscar el socio en la base de datos...".
- El sistema debe verificar el estado de la cuota comparando el estado del campo correspondiente:
"y ver si tiene la cuota al día".
- El sistema debe guardar la hora y el DNI de la persona en una tabla de ingresos: "cada vez que
alguien entra el sistema debe guardar la hora exacta y el dni de la persona".
- El sistema debe generar un archivo Excel a partir de la tabla de registros: "a fin de mes quiero
entrar a una página web administrativa y descargarme un reporte en excel con todos los
ingresos".