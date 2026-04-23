"""
Marshmallow schema for API responses (GET/POST /sensors).

- Serializes ``SensorReading`` rows to JSON with camelCase keys via ``data_key``.
- Fields: ``id``, ``sensorId``, ``temperature``, ``humidity``, ``createdAt``.
"""

from marshmallow import Schema, fields


class SensorResponseSchema(Schema):
    id = fields.Integer(required=True)
    sensor_id = fields.String(data_key="sensorId", required=True)
    temperature = fields.Float(required=True)
    humidity = fields.Float(required=True)
    created_at = fields.DateTime(data_key="createdAt", required=True)
