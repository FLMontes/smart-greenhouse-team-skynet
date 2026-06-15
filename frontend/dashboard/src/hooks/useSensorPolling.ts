'use client';

import { useState, useEffect } from 'react';
import { sensorService } from '@/services/sensors.service';
import type { SensorReading } from '@/types/sensor.types';
import { POLLING_INTERVALS } from '@/constants';

interface UseSensorPollingReturn {
  data: SensorReading[];
  loading: boolean;
  error: Error | null;
  lastUpdate: string;
}

export function useSensorPolling(
  intervalMs: number = POLLING_INTERVALS.SENSORS
): UseSensorPollingReturn {
  const [data, setData] = useState<SensorReading[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<Error | null>(null);
  const [lastUpdate, setLastUpdate] = useState(new Date().toISOString());

  useEffect(() => {
    const fetchData = async () => {
      try {
        setLoading(true);
        setError(null);
        const readings = await sensorService.getLatestReadings();
        setData(readings);
        setLastUpdate(new Date().toISOString());
      } catch (err) {
        const errorObj = err instanceof Error ? err : new Error(String(err));
        setError(errorObj);
        console.error('Error polling sensor data:', errorObj);
      } finally {
        setLoading(false);
      }
    };

    // Fetch immediately on mount
    fetchData();

    // Set up interval for polling
    const interval = setInterval(fetchData, intervalMs);

    // Cleanup interval on unmount
    return () => clearInterval(interval);
  }, [intervalMs]);

  return { data, loading, error, lastUpdate };
}
