package rikf.circuitbreaker;

import clock.Clock;

/**
 * This class is responsible for manufacturing the various states used by the state machine
 */
public class CircuitBreakerStateFactory {

    private final CircuitBreakerSettings circuitBreakerSettings;
    private final Clock clock;

    public CircuitBreakerStateFactory(CircuitBreakerSettings circuitBreakerSettings, Clock clock) {
        this.circuitBreakerSettings = circuitBreakerSettings;
        this.clock = clock;
    }

    public CircuitBreakerStateFactory(Clock clock) {
        this.clock = clock;
        circuitBreakerSettings = new CircuitBreakerSettings();
    }

    public CircuitBreakerStateOpen openState() {
        return new CircuitBreakerStateOpen(clock, circuitBreakerSettings.getTimeToWaitBeforeRetryingCircuit());
    }

    public CircuitBreakerStateHalfOpen halfOpenState() {
        return new CircuitBreakerStateHalfOpen();
    }

    public CircuitBreakerStateClosed closedState() {
        return new CircuitBreakerStateClosed(clock, circuitBreakerSettings.getNumberOfAcceptableFailuresWithinWindow(), circuitBreakerSettings.getFailureWindow());
    }

}
