/**
 * This file holds all TypeScript interfaces matching the backend API response shapes.
 *
 * - SensorReading — shape returned by GET /api/measurements and GET /api/measurements/latest
 * - Alert — alert payload for the dashboard
 */

export type AlertSeverity = 'low' | 'medium' | 'high';

export interface SensorReading {
  id: number;
  sensorId: number;
  temperature: number;
  humidity: number;
  light: number;
  co2: number;
  timestamp: string;
  buttonPressed: boolean;
  createdAt: string;
}

export interface Alert {
  sensorId: number;
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

export interface LightChartProps {
  data: SensorReading[];
}

export interface CO2ChartProps {
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

export interface CurrentTemperatureCardProps {
  temperature: number;
  sensorId: string;
  timestamp: string;
}

export interface CurrentHumidityCardProps {
  humidity: number;
  sensorId: string;
  timestamp: string;
}

export interface CurrentLightCardProps {
  light: number;
  sensorId: string;
  timestamp: string;
}

export interface CurrentCO2CardProps {
  co2: number;
  sensorId: string;
  timestamp: string;
}