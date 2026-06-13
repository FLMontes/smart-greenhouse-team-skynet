import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovingAverageStrategyTest {

    @Test
    void shouldCalculateAverageCorrectly() {

        MovingAverageStrategy strategy =
                new MovingAverageStrategy();

        double[] values = {20, 22, 24};

        double result =
                strategy.calculate(values);

        assertEquals(22.0, result);
    }
}