# Flask backend (IoT sensors API)

REST API aligned with the course contract: **POST /sensors** and **GET /sensors**, layered as controllers → services → repositories, with PostgreSQL persistence.

## Prerequisites

- Python **3.11+**
- PostgreSQL (e.g. `docker compose` from `docker/` — see [Docker Setup](../../../docker/README.md))

## Monorepo environment

Configuration lives in the **repository root** `.env` (copy from root `.env.example`). Do not maintain a separate `.env.example` in this folder.

Important keys:

| Variable | Purpose |
|----------|---------|
| `POSTGRES_*` / `DATABASE_URL` | Database connection (URI is built from `POSTGRES_*` if `DATABASE_URL` still contains `${...}`) |
| `FLASK_APP` | Must be `main:app` when using the Flask CLI |
| `FLASK_PORT` | Dev server port (default `5000`) |
| `FLASK_DEBUG` | `1` for debug |
| `FLASK_SECRET_KEY` | Session / signing |

Set **`NEXT_PUBLIC_API_URL=http://localhost:<FLASK_PORT>`** in the root `.env` when the Next.js app should talk to this backend.

## Setup

From **`backend/flask/final_project`**:

```bash
python -m venv .venv
source .venv/bin/activate   # Windows: .venv\Scripts\activate
pip install -r requirements.txt
```

Ensure the root `.env` exists and PostgreSQL is reachable, then apply migrations:

```bash
export FLASK_APP=main:app
flask db upgrade
```

## Run

```bash
python main.py
```

Or:

```bash
export FLASK_APP=main:app
flask run --host 0.0.0.0 --port "${FLASK_PORT:-5000}"
```

- API base: `http://localhost:5000` (or your `FLASK_PORT`)
- Swagger UI: **http://localhost:5000/apidocs**

## Quick test

```bash
curl -s -X POST http://localhost:5000/sensors \
  -H "Content-Type: application/json" \
  -d '{"sensorId":"esp32-01","temperature":23.5,"humidity":60.2}'

curl -s http://localhost:5000/sensors
```

## Layout

```
final_project/
├── alembic.ini
├── main.py
├── requirements.txt
├── app/
│   ├── __init__.py          # create_app(), extensions wiring
│   ├── extensions.py        # db, migrate
│   ├── controllers/         # Blueprints (HTTP only)
│   ├── services/            # Business logic
│   ├── repositories/        # PostgreSQL
│   └── models/
│       ├── entities/        # SQLAlchemy
│       └── schemas/         # Marshmallow
└── migrations/              # Alembic revisions
```

`ProcessingService` is a placeholder for coursework (thresholds, analytics, etc.).

## New migrations

```bash
export FLASK_APP=main:app
flask db migrate -m "describe change"
flask db upgrade
```

---

See also the root [Docker Setup](../../../docker/README.md) and [project README](../../../README.md).
