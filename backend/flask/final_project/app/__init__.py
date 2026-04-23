"""
Flask application factory.

``create_app`` wires SQLAlchemy, Flask-Migrate, CORS, and Flasgger, registers
the sensors blueprint, and loads configuration from the **monorepo root**
``.env`` (shared with Docker / other backends). No route implementations belong
here — only extension setup and registration.
"""

from __future__ import annotations

import os
from pathlib import Path
from urllib.parse import quote_plus

from dotenv import load_dotenv
from flasgger import Swagger
from flask import Flask
from flask_cors import CORS

from app.controllers.sensors_controller import sensors_bp
from app.extensions import db, migrate


def _monorepo_root() -> Path:
    # app/__init__.py -> app -> final_project -> flask -> backend -> repo root
    return Path(__file__).resolve().parents[4]


def _sqlalchemy_database_uri() -> str:
    raw = os.getenv("DATABASE_URL", "").strip()
    if raw and not raw.startswith("postgresql://${"):
        return raw

    user = quote_plus(os.getenv("POSTGRES_USER", "postgres"))
    password = quote_plus(os.getenv("POSTGRES_PASSWORD", ""))
    host = os.getenv("POSTGRES_HOST", "localhost")
    port = os.getenv("POSTGRES_PORT", "5432")
    name = os.getenv("POSTGRES_DB", "postgres")
    return f"postgresql+psycopg2://{user}:{password}@{host}:{port}/{name}"


def create_app() -> Flask:
    load_dotenv(_monorepo_root() / ".env", override=False)

    app = Flask(__name__)
    app.config["SQLALCHEMY_DATABASE_URI"] = _sqlalchemy_database_uri()
    app.config["SQLALCHEMY_TRACK_MODIFICATIONS"] = False
    app.config["SECRET_KEY"] = os.getenv("FLASK_SECRET_KEY", "dev-change-me")

    db.init_app(app)
    migrate.init_app(app, db)

    # Register models with SQLAlchemy metadata for migrations.
    with app.app_context():
        from app.models.entities.sensor_reading import SensorReading  # noqa: F401

    CORS(app)

    swagger_template = {
        "swagger": "2.0",
        "info": {
            "title": "IoT Sensors API (Flask)",
            "description": "REST API aligned with the course IoT dashboard contract.",
            "version": "1.0.0",
        },
        "basePath": "/",
    }
    Swagger(app, template=swagger_template)

    app.register_blueprint(sensors_bp, url_prefix="/sensors")

    return app
