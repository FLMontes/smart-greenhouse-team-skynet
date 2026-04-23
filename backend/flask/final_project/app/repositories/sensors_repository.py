"""
PostgreSQL access for sensor readings (Flask-SQLAlchemy).

- Used only from ``SensorsService`` — controllers never import this module.
- ``save`` persists a new row; ``find_all`` returns newest-first history.
"""

from __future__ import annotations

from typing import Any

from app.extensions import db
from app.models.entities.sensor_reading import SensorReading


class SensorsRepository:
    def save(self, data: dict[str, Any]) -> SensorReading:
        row = SensorReading(
            sensor_id=data["sensor_id"],
            temperature=data["temperature"],
            humidity=data["humidity"],
        )
        db.session.add(row)
        db.session.commit()
        db.session.refresh(row)
        return row

    def find_all(self) -> list[SensorReading]:
        return (
            SensorReading.query.order_by(SensorReading.created_at.desc()).all()
        )
