import type { SensorReading } from "../types/sensor.types";

export interface FormattedSensorReading {
  sensorId: string;
  temperature: string;
  humidity: string;
  createdAt: string;
}

const dateFormatter = new Intl.DateTimeFormat("es-AR", {
  day: "2-digit",
  month: "2-digit",
  year: "numeric",
  timeZone: "UTC",
});

export function formatSensorReading(
  reading: SensorReading,
): FormattedSensorReading {
  return {
    sensorId: reading.sensorId,
    temperature: `${reading.temperature.toFixed(1)} C`,
    humidity: `${reading.humidity.toFixed(1)}%`,
    createdAt: dateFormatter.format(new Date(reading.createdAt)),
  };
}
