# Frontend Dashboard - Scaffolding

## Estructura del Proyecto

```
src/
├── app/                    # Next.js App Router
│   ├── layout.tsx         # Layout principal
│   ├── page.tsx           # Página home
│   └── dashboard/         # Rutas del dashboard
│       ├── page.tsx       # Dashboard principal
│       └── loading.tsx    # Skeleton/loading state
├── components/            # Componentes React reutilizables
│   ├── ui/               # Componentes base (button, card, etc)
│   ├── charts/           # Componentes de gráficos
│   └── widgets/          # Widgets específicos del dashboard
├── hooks/                # Custom React hooks
│   └── useSensorPolling.ts
├── services/             # Servicios API
│   └── sensors.service.ts
├── types/                # TypeScript types/interfaces
│   └── sensor.types.ts
├── lib/                  # Funciones utilitarias
│   ├── formatSensorReading.ts
│   ├── utils.ts
│   └── formatSensorReading.test.ts
├── contexts/             # React Context (estado global)
├── store/                # State management (Redux, Zustand, etc)
├── config/               # Configuración de la app
├── constants/            # Constantes globales
├── middleware/           # Middleware (autenticación, etc)
└── utils/                # Utilidades generales
```

## Scripts Disponibles

```bash
npm run dev      # Inicia servidor de desarrollo (localhost:3000)
npm run build    # Construye para producción
npm run start    # Inicia servidor de producción
npm run lint     # Ejecuta linter
npm run test     # Ejecuta tests con Vitest
```

## Stack Tecnológico

- **Next.js 16.2.2** - Framework React con SSR
- **React 19.2.4** - UI library
- **TypeScript 5** - Type safety
- **Tailwind CSS 4** - Utility-first CSS
- **Shadcn UI** - Componentes reutilizables
- **Recharts 3.8.1** - Gráficos
- **Vitest** - Testing framework

## Guía de Desarrollo

### Agregar un Nuevo Componente

1. Crear archivo en `src/components/`
2. Usar TypeScript para type safety
3. Exportar desde index si es necesario

```tsx
// src/components/widgets/MyWidget.tsx
import { FC } from 'react';

interface MyWidgetProps {
  title: string;
}

export const MyWidget: FC<MyWidgetProps> = ({ title }) => {
  return <div>{title}</div>;
};
```

### Agregar una Nueva Ruta

1. Crear carpeta en `src/app/`
2. Agregar `page.tsx` en la carpeta

```tsx
// src/app/settings/page.tsx
export default function SettingsPage() {
  return <div>Settings</div>;
}
```

### Usar Servicios API

```tsx
import { sensorService } from '@/services/sensors.service';

const data = await sensorService.getSensors();
```

### Agregar Tipos TypeScript

```tsx
// src/types/myType.ts
export interface MyType {
  id: string;
  name: string;
}
```

## Variables de Entorno

Crear archivo `.env.local` en la raíz del proyecto:

```
NEXT_PUBLIC_API_BASE_URL=http://localhost:8000
```

## Próximos Pasos

- [ ] Configurar autenticación
- [ ] Integrar con API backend
- [ ] Implementar estado global (Zustand/Redux)
- [ ] Agregar tests unitarios
- [ ] Configurar CI/CD
- [ ] Implementar features del dashboard
