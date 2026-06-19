// Configuration values for the dashboard
export const config = {
  api: {
    baseUrl: process.env.NEXT_PUBLIC_API_BASE_URL || 'http://localhost:8080',
    timeout: 10000,
  },
  app: {
    name: 'Dashboard',
    version: '0.1.0',
  },
  features: {
    sensors: true,
    alerts: true,
    analytics: true,
  },
};
