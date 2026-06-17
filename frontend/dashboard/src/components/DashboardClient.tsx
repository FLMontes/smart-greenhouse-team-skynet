/**
 * Dashboard route — a React Server Component. Do **not** add `'use client'`.
 *
 * Composición del dashboard de invernadero inteligente con paneles de sensores actuales,
 * gráficos históricos, alertas y tabla de historial.
 */

import { GreenhouseHeader } from '@/components/GreenhouseHeader';
import { SensorComparisonChart } from '@/components/charts/SensorComparisonChart';
import { HumidityChart } from '@/components/charts/HumidityChart';
import { TemperatureChart } from '@/components/charts/TemperatureChart';
import { LightChart } from '@/components/charts/LightChart';
import { AlertBadge } from '@/components/widgets/AlertBadge';
import { CurrentTemperatureCard } from '@/components/widgets/CurrentTemperatureCard';
import { CurrentHumidityCard } from '@/components/widgets/CurrentHumidityCard';
import { CurrentLightCard } from '@/components/widgets/CurrentLightCard';
import { HistoryTable } from '@/components/HistoryTable';
import type { Alert, SensorReading } from '@/types/sensor.types';
import { PLACEHOLDER_READINGS } from '@/constants';

const now = new Date().toISOString();

const placeholderReading: SensorReading = {
  id: 0,
  sensorId: 1,
  temperature: PLACEHOLDER_READINGS.temperature,
  humidity: PLACEHOLDER_READINGS.humidity,
  light: PLACEHOLDER_READINGS.light,
  co2: 420,
  buttonPressed: false,
  timestamp: now,
  createdAt: now,
};

function buildPlaceholderAlert(reading: SensorReading): Alert {
  const triggeredAt = new Date().toISOString();

  if (reading.temperature > 30) {
    return {
      sensorId: reading.sensorId,
      message: 'Alerta: temperatura elevada. Se recomienda activar ventilación.',
      severity: 'high',
      triggeredAt,
    };
  }

  if (reading.temperature < 15) {
    return {
      sensorId: reading.sensorId,
      message: 'Alerta: temperatura baja. Se recomienda activar calefacción.',
      severity: 'medium',
      triggeredAt,
    };
  }

  if (reading.humidity < 40) {
    return {
      sensorId: reading.sensorId,
      message: 'Alerta: humedad baja. Se recomienda activar riego.',
      severity: 'medium',
      triggeredAt,
    };
  }

  if (reading.humidity > 80) {
    return {
      sensorId: reading.sensorId,
      message: 'Alerta: humedad elevada. Revisar ventilación del invernadero.',
      severity: 'medium',
      triggeredAt,
    };
  }

  if (reading.light < 300) {
    return {
      sensorId: reading.sensorId,
      message: 'Alerta: nivel de luz bajo. Revisar iluminación del invernadero.',
      severity: 'low',
      triggeredAt,
    };
  }

  if (reading.co2 > 1000) {
    return {
      sensorId: reading.sensorId,
      message: 'Alerta: nivel de CO₂ elevado. Se recomienda ventilar el invernadero.',
      severity: 'high',
      triggeredAt,
    };
  }

  if (reading.buttonPressed) {
    return {
      sensorId: reading.sensorId,
      message: 'Alerta manual activada desde el botón físico.',
      severity: 'medium',
      triggeredAt,
    };
  }

  return {
    sensorId: reading.sensorId,
    message: 'Condiciones normales. No se requieren acciones correctivas.',
    severity: 'low',
    triggeredAt,
  };
}

const placeholderAlert: Alert = buildPlaceholderAlert(placeholderReading);