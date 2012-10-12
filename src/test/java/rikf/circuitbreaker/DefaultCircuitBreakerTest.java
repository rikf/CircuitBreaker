package rikf.circuitbreaker;

import org.joda.time.Period;
import org.junit.Test;
import rikf.clock.MockClock;

import static org.hamcrest.MatcherAssert.assertThat;
import static rikf.circuitbreaker.CircuitBreakerMatchers.closed;
import static rikf.circuitbreaker.CircuitBreakerMatchers.open;

public class DefaultCircuitBreakerTest {


    private CircuitBreaker<Void> underTest;
    private final MockClock clock = new MockClock();


    @Test
    public void circuitBreakerIsFlungOpenWhenErrorRateBecomesUnacceptable() {
        final ControllableCommand command = new ControllableCommand();

        underTest = new DefaultCircuitBreaker<Void>("Test", new CircuitBreakerSettings(10, Period.seconds(10), Period.minutes(1)), clock);


        assertThat(underTest, closed());

        for (int k = 0; k < 9; k++) {
            executeCommandAndSwallowException(command);
            clock.tick(Period.seconds(1));
        }
        //Still closed
        assertThat(underTest, closed());
        //One final exception within timeout throws it over threshold
        executeCommandAndSwallowException(command);
        assertThat(underTest, open());

    }


    @Test
    public void circuitBreakerIsMovedToHalfOpenStateAfterHalfOpenTimeoutIsReached() {

        underTest = new DefaultCircuitBreaker<Void>("Test", new CircuitBreakerSettings(2, Period.minutes(1), Period.minutes(1)), clock);
        final ControllableCommand command = new ControllableCommand();


        assertThat(underTest, closed());

        executeCommandAndSwallowException(command);
        executeCommandAndSwallowException(command);
        executeCommandAndSwallowException(command);


        assertThat(underTest, open());

        executeCommandAndSwallowException(command);


        clock.tick(Period.minutes(1).plusSeconds(1));
        executeCommandAndSwallowException(command);


    }

    @Test
    public void circuitBreakerIsReopenedWhenHalfOpenTimeoutIsReachedAndTheServiceComesBackUp() {
        underTest = new DefaultCircuitBreaker<Void>("Test", new CircuitBreakerSettings(2, Period.minutes(1), Period.minutes(1)), clock);
        final ControllableCommand command = new ControllableCommand();


        assertThat(underTest, closed());

        executeCommandAndSwallowException(command);
        executeCommandAndSwallowException(command);
        executeCommandAndSwallowException(command);


        assertThat(underTest, open());


        clock.tick(Period.minutes(1).plusSeconds(1));
        command.setShouldThrowException(false);
        executeCommandAndSwallowException(command);


        assertThat(underTest, closed());


    }

    private void executeCommandAndSwallowException(ControllableCommand command) {
        try {
            underTest.attemptCall(command);
        } catch (RuntimeException e) {
        }
    }

    private class ControllableCommand implements Callable<Void> {
        private boolean shouldThrowException = true;

        @Override
        public Void execute() {
            if (shouldThrowException) {
                throw new RuntimeException("Booom!!!");
            }
            return null;
        }

        public void setShouldThrowException(boolean shouldThrowException) {
            this.shouldThrowException = shouldThrowException;
        }
    }
}

