"""
Marshmallow schema for POST /sensors JSON bodies (ESP32 / clients).

- Fields: ``sensorId`` (str), ``temperature`` (float), ``humidity`` (float), all required.
- ``data_key`` maps camelCase JSON to internal snake_case keys.
- Controllers call ``.load()``; invalid payloads raise ``ValidationError``.
"""

from marshmallow import Schema, fields


class CreateSensorSchema(Schema):
    sensor_id = fields.String(data_key="sensorId", required=True)
    temperature = fields.Float(required=True)
    humidity = fields.Float(required=True)
