import { describe, expect, it } from 'vitest';

import { formatSensorReading } from './formatSensorReading';

describe('formatSensorReading', () => {
  it('formatea correctamente una lectura recibida del backend', () => {
    const reading = {
      id: 1,
      sensorId: 1,
      temperature: 23.456,
      humidity: 61.2,
      light: 15000,
      co2: 450,
      buttonPressed: false,
      timestamp: '2026-06-04T14:30:00Z',
      createdAt: '2026-06-04T14:30:00Z',
    };

    expect(formatSensorReading(reading)).toEqual({
      sensorId: '1',
      temperature: '23.5 C',
      humidity: '61.2%',
      createdAt: '04/06/2026',
    });
  });
});