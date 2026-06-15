import { config } from '@/config';
import type { SensorReading, Alert } from '@/types/sensor.types';

const API_BASE_URL = config.api.baseUrl;

export const sensorService = {
  async getSensorReadings(
    sensorType?: 'temperature' | 'humidity' | 'light'
  ): Promise<SensorReading[]> {
    try {
      const endpoint = sensorType
        ? `${API_BASE_URL}/api/sensors?type=${sensorType}`
        : `${API_BASE_URL}/api/sensors`;

      const response = await fetch(endpoint, {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' },
        signal: AbortSignal.timeout(config.api.timeout),
      });

      if (!response.ok) throw new Error(`HTTP ${response.status}`);
      return await response.json();
    } catch (error) {
      console.error('Failed to fetch sensor readings:', error);
      return [];
    }
  },

  async getLatestReadings(): Promise<SensorReading[]> {
    try {
      const response = await fetch(`${API_BASE_URL}/api/sensors/latest`, {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' },
        signal: AbortSignal.timeout(config.api.timeout),
      });

      if (!response.ok) throw new Error(`HTTP ${response.status}`);
      return await response.json();
    } catch (error) {
      console.error('Failed to fetch latest readings:', error);
      return [];
    }
  },

  async getHistoricalReadings(
    sensorId: string,
    timeRangeMinutes = 1440
  ): Promise<SensorReading[]> {
    try {
      const response = await fetch(
        `${API_BASE_URL}/api/sensors/${sensorId}/history?range=${timeRangeMinutes}`,
        {
          method: 'GET',
          headers: { 'Content-Type': 'application/json' },
          signal: AbortSignal.timeout(config.api.timeout),
        }
      );

      if (!response.ok) throw new Error(`HTTP ${response.status}`);
      return await response.json();
    } catch (error) {
      console.error(`Failed to fetch historical readings for ${sensorId}:`, error);
      return [];
    }
  },

  async getAlerts(): Promise<Alert[]> {
    try {
      const response = await fetch(`${API_BASE_URL}/api/alerts`, {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' },
        signal: AbortSignal.timeout(config.api.timeout),
      });

      if (!response.ok) throw new Error(`HTTP ${response.status}`);
      return await response.json();
    } catch (error) {
      console.error('Failed to fetch alerts:', error);
      return [];
    }
  },

  async getReadingsBySensor(sensorId: string): Promise<SensorReading[]> {
    try {
      const response = await fetch(`${API_BASE_URL}/api/sensors/${sensorId}`, {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' },
        signal: AbortSignal.timeout(config.api.timeout),
      });

      if (!response.ok) throw new Error(`HTTP ${response.status}`);
      return await response.json();
    } catch (error) {
      console.error(`Failed to fetch readings for sensor ${sensorId}:`, error);
      return [];
    }
  },
};
