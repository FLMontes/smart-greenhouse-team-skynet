"""
Business logic for sensor ingestion and listing.

- Depends on ``SensorsRepository`` (constructor injection).
- ``create`` validates input, persists, returns a serialized dict.
- ``find_all`` returns serialized rows (never raw SQLAlchemy models).
"""

from __future__ import annotations

from typing import Any

from app.models.schemas.create_sensor_schema import CreateSensorSchema
from app.models.schemas.sensor_response_schema import SensorResponseSchema
from app.repositories.sensors_repository import SensorsRepository


class SensorsService:
    def __init__(self, sensors_repository: SensorsRepository) -> None:
        self._sensors = sensors_repository
        self._create_schema = CreateSensorSchema()
        self._response_schema = SensorResponseSchema()

    def create(self, data: dict[str, Any]) -> dict[str, Any]:
        payload = self._create_schema.load(data)
        saved = self._sensors.save(payload)
        return self._response_schema.dump(saved)

    def find_all(self) -> list[dict[str, Any]]:
        rows = self._sensors.find_all()
        return self._response_schema.dump(rows, many=True)
