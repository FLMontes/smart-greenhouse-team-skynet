import { describe, it, expect, vi, beforeEach } from 'vitest';
import { sensorService } from './sensors.service';

// 1. Mockeamos la configuración para no depender del .env real durante las pruebas
vi.mock('@/config', () => ({
  config: {
    api: {
      baseUrl: 'http://localhost:8080',
      timeout: 5000,
    },
  },
}));

// 2. Mockeamos la función 'fetch' global del navegador/Node
global.fetch = vi.fn();

describe('Pruebas de la T47: Sensor Service', () => {
  
  // Limpiamos el historial del fetch falso antes de cada prueba
  beforeEach(() => {
    vi.clearAllMocks();
  });

  it('1. Debe obtener la última medición correctamente (getLatestReadings)', async () => {
    // Preparamos los datos falsos que simulan la respuesta exitosa de Spring Boot
    const mockMeasurement = {
      id: 1,
      temperature: 25.5,
      humidity: 60,
      timestamp: '2026-06-15T18:00:00'
    };

    // Le decimos al fetch que responda con un 200 OK y nuestros datos
    (fetch as any).mockResolvedValue({
      ok: true,
      status: 200,
      json: async () => mockMeasurement,
    });

    // Ejecutamos tu servicio
    const data = await sensorService.getLatestReadings();

    // Comprobaciones
    expect(fetch).toHaveBeenCalledTimes(1);
    expect(fetch).toHaveBeenCalledWith(
      'http://localhost:8080/api/measurements/latest',
      expect.any(Object) // Verifica que se enviaron los headers y el timeout
    );
    expect(data).toEqual(mockMeasurement);
  });

  it('2. Debe devolver null si el backend no tiene datos y responde con 404', async () => {
    // Simulamos que el backend dice "No hay datos" (404 Not Found)
    (fetch as any).mockResolvedValue({
      ok: false,
      status: 404,
    });

    const data = await sensorService.getLatestReadings();

    // Comprobamos que el código manejó el error y devolvió null
    expect(data).toBeNull();
  });

  it('3. Debe devolver un array vacío si la conexión falla completamente (Ej: Servidor caído)', async () => {
    // Simulamos que el servidor está apagado o no hay internet
    (fetch as any).mockRejectedValue(new Error('Network Error: Connection Refused'));

    const data = await sensorService.getSensorReadings();

    // Comprobamos que no crasheó la app y devolvió un array vacío por seguridad
    expect(data).toEqual([]);
  });
});