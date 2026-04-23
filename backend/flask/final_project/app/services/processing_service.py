"""
Processing / analytics layer for sensor data.

Students add algorithms here (anomaly detection, heat index, moving averages,
threshold alerts). Inject ``SensorsRepository`` for read access.

Do not put HTTP or Marshmallow concerns in this module.
"""

from __future__ import annotations

from app.repositories.sensors_repository import SensorsRepository


class ProcessingService:
    """Placeholder — implement domain algorithms in coursework."""

    def __init__(self, sensors_repository: SensorsRepository) -> None:
        self._sensors = sensors_repository
