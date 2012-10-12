package rikf.circuitbreaker;

import org.joda.time.LocalTime;
import org.joda.time.ReadablePeriod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rikf.clock.Clock;

public class CircuitBreakerStateOpen implements CircuitBreakerState {

    private static final Logger logger = LoggerFactory.getLogger(CircuitBreakerStateOpen.class);


    private final Clock clock;
    private LocalTime trippedTimestamp;
    private LocalTime earliestTimeToAttemptCircuitClose;

    public CircuitBreakerStateOpen(Clock clock, ReadablePeriod timeToWaitBeforeRetryingCircuit) {
        this.clock = clock;
        this.trippedTimestamp = clock.getCurrentTime();
        this.earliestTimeToAttemptCircuitClose = trippedTimestamp.plus(timeToWaitBeforeRetryingCircuit);
    }

    @Override
    public void preExecute(CircuitBreaker circuitBreaker) {
        if (earliestTimeToAttemptCircuitClose.isBefore(clock.getCurrentTime())) {
            circuitBreaker.halfOpen();
        } else {
            logger.info("Circuit breaker [name: " + circuitBreaker.getName() + "] is open will attempt to close circuit after [time: " + earliestTimeToAttemptCircuitClose + "]");
            throw new CircuitBreakerOpenException();
        }
    }

    @Override
    public void postExecute(CircuitBreaker circuitBreaker) {
    }

    @Override
    public void onExecutionError(CircuitBreaker circuitBreaker) {
    }

    @Override
    public String toString() {
        return "Open";
    }
}
