"""
SQLAlchemy model for persisted sensor measurements.

- Mapped to the ``sensor_readings`` table.
- Columns: ``id`` (PK, autoincrement), ``sensor_id``, ``temperature``, ``humidity``,
  ``created_at`` (server default ``now()``).
- Routes must not return this model directly — serialize with ``SensorResponseSchema``.
"""

from datetime import datetime

from sqlalchemy import DateTime, Float, Integer, String, func
from sqlalchemy.orm import Mapped, mapped_column

from app.extensions import db


class SensorReading(db.Model):
    __tablename__ = "sensor_readings"

    id: Mapped[int] = mapped_column(Integer, primary_key=True, autoincrement=True)
    sensor_id: Mapped[str] = mapped_column(String(128), nullable=False, index=True)
    temperature: Mapped[float] = mapped_column(Float, nullable=False)
    humidity: Mapped[float] = mapped_column(Float, nullable=False)
    created_at: Mapped[datetime] = mapped_column(
        DateTime(timezone=True),
        nullable=False,
        server_default=func.now(),
    )
