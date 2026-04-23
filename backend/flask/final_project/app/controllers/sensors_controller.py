"""
HTTP layer for ``/sensors`` (Flask Blueprint).

- Prefix ``/sensors`` is applied in the app factory.
- POST validates JSON with ``CreateSensorSchema`` and delegates to ``SensorsService``.
- GET returns the serialized list from the service.
- No business rules here — only request/response handling and status codes.
"""

from __future__ import annotations

from flask import Blueprint, Response, jsonify, request
from marshmallow import ValidationError

from app.repositories.sensors_repository import SensorsRepository
from app.services.sensors_service import SensorsService

sensors_bp = Blueprint("sensors", __name__)


def _service() -> SensorsService:
    return SensorsService(SensorsRepository())


@sensors_bp.route("", methods=["POST"])
def create_reading() -> tuple[Response, int]:
    """
    Ingest a sensor reading
    ---
    tags:
      - sensors
    consumes:
      - application/json
    parameters:
      - in: body
        name: body
        required: true
        schema:
          type: object
          required:
            - sensorId
            - temperature
            - humidity
          properties:
            sensorId:
              type: string
            temperature:
              type: number
            humidity:
              type: number
    responses:
      201:
        description: Created
      400:
        description: Validation error
    """
    if not request.is_json:
        return jsonify({"message": "Expected application/json"}), 400

    body = request.get_json(silent=True)
    if body is None:
        return jsonify({"message": "Invalid JSON body"}), 400

    try:
        result = _service().create(body)
    except ValidationError as err:
        return jsonify(err.messages), 400

    return jsonify(result), 201


@sensors_bp.route("", methods=["GET"])
def list_readings() -> tuple[Response, int]:
    """
    List sensor readings
    ---
    tags:
      - sensors
    responses:
      200:
        description: OK
    """
    rows = _service().find_all()
    return jsonify(rows), 200
