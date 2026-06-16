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
  },

  // 3. Función mockeada para obtener historial con paginación integrada al servicio
  async getHistoricalReadings(page: number, limit: number, dateFilter?: string): Promise<{ data: SensorReading[], total: number }> {
    // Aquí iría el fetch real al backend: fetch(`/api/history?page=${page}&limit=${limit}&date=${dateFilter}`)
    
    // Mock data para propósitos de demostración
    const mockData: SensorReading[] = Array.from({ length: 50 }).map((_, i) => ({
      id: i,
      sensorId: 'sensor-1',
      temperature: 20 + Math.random() * 10,
      humidity: 50 + Math.random() * 30,
      light: 300 + Math.random() * 200,
      createdAt: new Date(Date.now() - i * 60000).toISOString(), // Restando minutos
    }));

    const filteredData = dateFilter 
      ? mockData.filter(d => d.createdAt.startsWith(dateFilter))
      : mockData;

    const start = (page - 1) * limit;
    const paginatedData = filteredData.slice(start, start + limit);

    return {
      data: paginatedData,
      total: filteredData.length,
    };
  }
};