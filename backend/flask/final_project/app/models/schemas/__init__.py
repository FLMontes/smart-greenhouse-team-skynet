"""Marshmallow schemas for validation and serialization."""

from app.models.schemas.create_sensor_schema import CreateSensorSchema
from app.models.schemas.sensor_response_schema import SensorResponseSchema

__all__ = ["CreateSensorSchema", "SensorResponseSchema"]
