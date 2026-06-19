'use client';

import { useState, useEffect } from 'react';
import { POLLING_INTERVALS } from '@/constants';
import { sensorSubject, type PollingState } from '@/services/SensorSubject';

export function useSensorPolling(
  intervalMs: number = POLLING_INTERVALS.SENSORS
): PollingState {
  
  // useState se usa para que React sepa cuándo repintar la pantalla, 
  const [pollingState, setPollingState] = useState<PollingState>({
    data: [],
    loading: true,
    error: null,
    lastUpdate: new Date().toISOString(),
  });

  useEffect(() => {
    // 1. Nos suscribimos al Sujeto de Vanilla TypeScript
    // El sujeto se encarga de hacer el setInterval y el fetch por detrás
    const unsubscribe = sensorSubject.subscribe((newState) => {
      setPollingState(newState);
    }, intervalMs);

    // 2. Limpieza: cuando el componente desaparece de la pantalla, nos desuscribimos
    return () => {
      unsubscribe();
    };
  }, [intervalMs]);

  return pollingState;
}