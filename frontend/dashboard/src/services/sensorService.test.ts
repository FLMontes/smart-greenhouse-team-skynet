import { beforeEach, describe, expect, it, vi } from 'vitest';
import { sensorService } from './sensors.service';

vi.mock('@/config', () => ({
  config: {
    api: {
      baseUrl: 'http://localhost:8080',
      timeout: 5000,
    },
  },
}));

const fetchMock = vi.fn<typeof fetch>();
vi.stubGlobal('fetch', fetchMock);

describe('Pruebas de la T47: Sensor Service', () => {
  beforeEach(() => {
    fetchMock.mockReset();
  });

  it('1. Debe obtener la última medición correctamente', async () => {
    const mockMeasurement = {
      id: 1,
      temperature: 25.5,
      humidity: 60,
      timestamp: '2026-06-15T18:00:00',
    };

    fetchMock.mockResolvedValue(
      new Response(JSON.stringify(mockMeasurement), {
        status: 200,
        headers: {
          'Content-Type': 'application/json',
        },
      })
    );

    const data = await sensorService.getLatestReadings();

    expect(fetchMock).toHaveBeenCalledTimes(1);
    expect(fetchMock).toHaveBeenCalledWith(
      'http://localhost:8080/api/measurements/latest',
      expect.any(Object)
    );

    expect(data).toEqual({
      ...mockMeasurement,
      createdAt: mockMeasurement.timestamp,
    });
  });

  it('2. Debe devolver null cuando el backend responde 404', async () => {
    fetchMock.mockResolvedValue(
      new Response(null, {
        status: 404,
      })
    );

    const data = await sensorService.getLatestReadings();

    expect(data).toBeNull();
  });

  it('3. Debe devolver un array vacío si la conexión falla', async () => {
    fetchMock.mockRejectedValue(
      new Error('Network Error: Connection Refused')
    );

    const data = await sensorService.getSensorReadings();

    expect(data).toEqual([]);
  });
});