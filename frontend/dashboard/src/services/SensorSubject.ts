import { sensorService } from '@/services/sensors.service';
import type { SensorReading } from '@/types/sensor.types';

// Definimos la estructura de datos que vamos a manejar
export interface PollingState {
  data: SensorReading[];
  loading: boolean;
  error: Error | null;
  lastUpdate: string;
}

// Tipo para las funciones que se suscriban a nuestros cambios
type Observer = (state: PollingState) => void;

class SensorSubject {
  private observers: Observer[] = [];
  private intervalId: NodeJS.Timeout | null = null;
  
  // El estado vive aquí, fuera de React
  private state: PollingState = {
    data: [],
    loading: true,
    error: null,
    lastUpdate: new Date().toISOString(),
  };

  // Método para que los componentes se suscriban
  subscribe(observer: Observer, intervalMs: number) {
    this.observers.push(observer);
    
    // Le enviamos el estado actual al observador ni bien se conecta
    observer(this.state);

    // Si es el primer observador en suscribirse, prendemos el motor (polling)
    if (this.observers.length === 1) {
      this.startPolling(intervalMs);
    }

    // Retornamos la función para desuscribirse
    return () => this.unsubscribe(observer);
  }

  // Método para darse de baja
  private unsubscribe(observer: Observer) {
    this.observers = this.observers.filter(obs => obs !== observer);
    
    // Si ya no hay nadie mirando (0 observadores), apagamos el motor para ahorrar memoria
    if (this.observers.length === 0) {
      this.stopPolling();
    }
  }

  // Avisa a todos los observadores que hay nueva información
  private notify() {
    this.observers.forEach(observer => observer(this.state));
  }

  // Inicia el ciclo automatizado
  private startPolling(intervalMs: number) {
    if (this.intervalId) return; // Evita que se creen múltiples intervalos
    
    this.fetchData(); // Hacemos la primera llamada inmediatamente
    
    // Configuramos la llamada repetitiva
    this.intervalId = setInterval(() => this.fetchData(), intervalMs);
  }

  // Detiene el ciclo automatizado
  private stopPolling() {
    if (this.intervalId) {
      clearInterval(this.intervalId);
      this.intervalId = null;
    }
  }

  // La lógica de red (aislada de React)
  private async fetchData() {
    this.state.loading = true;
    this.notify();

    try {
      const readings = await sensorService.getLatestReadings();
      
      // Adaptamos la respuesta: Si trae un solo objeto, lo metemos en un array para respetar la interfaz
      const dataArray = Array.isArray(readings) ? readings : (readings ? [readings] : []);

      this.state = {
        data: dataArray,
        loading: false,
        error: null,
        lastUpdate: new Date().toISOString()
      };
    } catch (err) {
      this.state = {
        ...this.state,
        loading: false,
        error: err instanceof Error ? err : new Error(String(err))
      };
    }
    
    this.notify();
  }
}

// Exportamos una única instancia (Singleton) para que toda la app comparta el mismo origen de datos
export const sensorSubject = new SensorSubject();