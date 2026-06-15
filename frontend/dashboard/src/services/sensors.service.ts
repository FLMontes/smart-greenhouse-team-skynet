import { config } from '@/config';
import type { SensorReading, Alert } from '@/types/sensor.types';

const API_BASE_URL = config.api.baseUrl;

export const sensorService = {
  
  // 1. Corregido para pegarle a /api/measurements y manejar paginación (limit/offset)
  async getSensorReadings(limit = 100, offset = 0): Promise<SensorReading[]> {
    try {
      const response = await fetch(`${API_BASE_URL}/api/measurements?limit=${limit}&offset=${offset}`, {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' },
        signal: AbortSignal.timeout(config.api.timeout),
      });

      if (!response.ok) throw new Error(`HTTP ${response.status}`);
      return await response.json();
    } catch (error) {
      console.error('Failed to fetch measurements:', error);
      return [];
    }
  },

  // 2. Corregido para retornar un SOLO objeto (SensorReading) y la URL correcta
  async getLatestReadings(): Promise<SensorReading | null> {
    try {
      const response = await fetch(`${API_BASE_URL}/api/measurements/latest`, {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' },
        signal: AbortSignal.timeout(config.api.timeout),
      });

      if (!response.ok) {
        if (response.status === 404) return null; // El backend devuelve 404 si no hay datos
        throw new Error(`HTTP ${response.status}`);
      }
      return await response.json();
    } catch (error) {
      console.error('Failed to fetch latest reading:', error);
      return null;
    }
  },

  // (Mantenemos getAlerts asumiendo que hay otro AlertController con /api/alerts)
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
  }
};