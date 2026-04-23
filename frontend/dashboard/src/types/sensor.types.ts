/**
 * This file holds all TypeScript interfaces matching the backend API response shapes.
 *
 * - {@link SensorReading} — shape returned by GET /sensors (id, sensorId, temperature, humidity, createdAt)
 * - {@link Alert} — alert payload for the dashboard (sensorId, message, severity, triggeredAt)
 */

export type AlertSeverity = 'low' | 'medium' | 'high';

export interface SensorReading {
  id: number;
  sensorId: string;
  temperature: number;
  humidity: number;
  createdAt: string;
}

export interface Alert {
  sensorId: string;
  message: string;
  severity: AlertSeverity;
  triggeredAt: string;
}

/** Props for chart/widget components — keeps components free of inline type definitions. */
export interface TemperatureChartProps {
  data: SensorReading[];
}

export interface HumidityChartProps {
  data: SensorReading[];
}

export interface SensorComparisonChartProps {
  data: SensorReading[];
}

export interface LatestReadingCardProps {
  reading: SensorReading;
}

export interface AlertBadgeProps {
  alert: Alert;
}
