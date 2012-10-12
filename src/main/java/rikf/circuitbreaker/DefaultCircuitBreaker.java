package rikf.circuitbreaker;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rikf.clock.Clock;

/**
 * A standard implementation of the circuit breaker
 *
 * @param <A>
 */
public class DefaultCircuitBreaker<A> implements CircuitBreaker<A> {

    private static final Logger logger = LoggerFactory.getLogger(DefaultCircuitBreaker.class);

    private final String name;

    private final CircuitBreakerStateFactory circuitBreakerStateFactory;

    private volatile CircuitBreakerState state;

    public DefaultCircuitBreaker(final String name, final CircuitBreakerSettings circuitBreakerSettings, Clock clock) {
        this.name = name;
        this.circuitBreakerStateFactory = new CircuitBreakerStateFactory(circuitBreakerSettings, clock);
        state = circuitBreakerStateFactory.closedState();
    }

    @Override
    public synchronized A attemptCall(Callable<A> command) {
        A result;
        try {
            getState().preExecute(this);
            result = command.execute();
            getState().postExecute(this);
        } catch (Throwable t) {
            getState().onExecutionError(this);
            throw new RuntimeException(t);
        }
        return result;
    }

    @Override
    public void open() {
        logger.info("Circuit breaker[name: " + name + "] has been moved to [state: OPEN]");
        this.state = circuitBreakerStateFactory.openState();
    }

    @Override
    public void close() {
        logger.info("Circuit breaker[name: " + name + "] has been moved to [state: CLOSED]");
        this.state = circuitBreakerStateFactory.closedState();
    }

    @Override
    public void halfOpen() {
        logger.info("Circuit breaker[name: " + name + "] has been moved to [state: HALF OPEN]");
        this.state = circuitBreakerStateFactory.halfOpenState();
    }


    public CircuitBreakerState getState() {
        return state;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isOpen() {
        return (this.state instanceof CircuitBreakerStateOpen);
    }

    @Override
    public boolean isClosed() {
        if (isOpen()) {
            //If we are checking to see if this circuit breaker is closed then check to see if the timeout has expired to move it to a state of half-open
            //This makes the api a little bit more client friendly
            try {
                this.state.preExecute(this);
            } catch (CircuitBreakerOpenException exception) {
                //Ok the circuit breaker is still open just swallow this exception because we did not actually attempt to call the underlying service
            }
        }

        return this.state instanceof CircuitBreakerStateClosed;
    }

    @Override
    public boolean isHalfOpen() {
        return (this.state instanceof CircuitBreakerStateHalfOpen);
    }

    @Override
    public String toString() {
        return "DefaultCircuitBreaker{" +
                "name='" + name + '\'' +
                ", state=" + state +
                '}';
    }
}
