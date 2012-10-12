package rikf.circuitbreaker;

import org.joda.time.Period;
import org.junit.Test;
import rikf.circuitbreaker.clock.MockClock;

import static org.mockito.Mockito.*;

public class CircuitBreakerStateClosedTest {
    @Test
    public void testOnExecutionError_WhenErrorThresholdIsExceededThenCircuitBreakerIsOpened() throws Exception {
        CircuitBreakerStateClosed underTest = new CircuitBreakerStateClosed(new MockClock(), 2, Period.seconds(1));

        final CircuitBreaker mockCircuitBreaker = mock(CircuitBreaker.class);

        underTest.onExecutionError(mockCircuitBreaker);

        verifyZeroInteractions(mockCircuitBreaker);

        underTest.onExecutionError(mockCircuitBreaker);

        verify(mockCircuitBreaker).open();

    }
}
