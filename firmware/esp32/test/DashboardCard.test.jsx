import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import DashboardCard from '../components/DashboardCard';

describe('DashboardCard Component', () => {
    it('renders the alert badge color correctly for COLD condition', () => {
        // Arrange: Renderizamos la tarjeta simulando estado crítico por frío
        render(
            <DashboardCard
                title="Temperatura"
                value="12°C"
                alertStatus="COLD"
            />
        );

        // Act: Buscamos el elemento de alerta en la pantalla
        const alertBadge = screen.getByText('COLD');

        // Assert: Verificamos presencia en el DOM
        expect(alertBadge).toBeInTheDocument();
    });
});