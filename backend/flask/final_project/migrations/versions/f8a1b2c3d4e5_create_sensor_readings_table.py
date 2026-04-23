"""create sensor_readings table

Revision ID: f8a1b2c3d4e5
Revises:
Create Date: 2026-04-05

"""

from alembic import op
import sqlalchemy as sa


revision = "f8a1b2c3d4e5"
down_revision = None
branch_labels = None
depends_on = None


def upgrade():
    op.create_table(
        "sensor_readings",
        sa.Column("id", sa.Integer(), autoincrement=True, nullable=False),
        sa.Column("sensor_id", sa.String(length=128), nullable=False),
        sa.Column("temperature", sa.Float(), nullable=False),
        sa.Column("humidity", sa.Float(), nullable=False),
        sa.Column(
            "created_at",
            sa.DateTime(timezone=True),
            server_default=sa.text("now()"),
            nullable=False,
        ),
        sa.PrimaryKeyConstraint("id"),
    )
    op.create_index(
        "ix_sensor_readings_sensor_id",
        "sensor_readings",
        ["sensor_id"],
        unique=False,
    )


def downgrade():
    op.drop_index("ix_sensor_readings_sensor_id", table_name="sensor_readings")
    op.drop_table("sensor_readings")
