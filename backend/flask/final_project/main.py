"""
Application entrypoint.

Loads the monorepo ``.env``, builds the app via ``create_app()``, and runs the
development server on ``FLASK_PORT`` (default ``5000``). Keep this file free of
business logic.
"""

from __future__ import annotations

import os
from pathlib import Path

from dotenv import load_dotenv

_ROOT = Path(__file__).resolve().parents[3]
load_dotenv(_ROOT / ".env", override=False)

from app import create_app  # noqa: E402

app = create_app()


if __name__ == "__main__":
    port = int(os.getenv("FLASK_PORT", "5000"))
    debug = os.getenv("FLASK_DEBUG", "0") in ("1", "true", "True", "yes")
    app.run(host="0.0.0.0", port=port, debug=debug)
