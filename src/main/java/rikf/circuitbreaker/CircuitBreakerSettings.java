package rikf.circuitbreaker;

import org.joda.time.Period;

public class CircuitBreakerSettings {

    public static final int DEFAULT_ACCEPTABLE_FAILURES_WITHIN_WINDOW = 5;
    public static final Period DEFAULT_TIME_TO_WAIT_BEFORE_RETRYING_CIRCUIT = Period.seconds(10);
    public static final Period DEFAULT_FAILURE_WINDOW = DEFAULT_TIME_TO_WAIT_BEFORE_RETRYING_CIRCUIT;

    private int numberOfAcceptableFailuresWithinWindow = DEFAULT_ACCEPTABLE_FAILURES_WITHIN_WINDOW;
    private Period failureWindow = DEFAULT_FAILURE_WINDOW;
    private Period timeToWaitBeforeRetryingCircuit = DEFAULT_TIME_TO_WAIT_BEFORE_RETRYING_CIRCUIT;

    public CircuitBreakerSettings() {
    }

    public CircuitBreakerSettings(int numberOfAcceptableFailuresWithinWindow, Period failureWindow, Period timeToWaitBeforeRetryingCircuit) {
        this.numberOfAcceptableFailuresWithinWindow = numberOfAcceptableFailuresWithinWindow;
        this.failureWindow = failureWindow;
        this.timeToWaitBeforeRetryingCircuit = timeToWaitBeforeRetryingCircuit;
    }

    public CircuitBreakerSettings(int numberOfAcceptableFailuresWithinWindow, int failureWindowInSeconds, int timeToWaitBeforeRetryingCircuitInSeconds) {
        this.numberOfAcceptableFailuresWithinWindow = numberOfAcceptableFailuresWithinWindow;
        this.failureWindow = Period.seconds(failureWindowInSeconds);
        this.timeToWaitBeforeRetryingCircuit = Period.seconds(timeToWaitBeforeRetryingCircuitInSeconds);
    }

    public int getNumberOfAcceptableFailuresWithinWindow() {
        return numberOfAcceptableFailuresWithinWindow;
    }

    public Period getFailureWindow() {
        return failureWindow;
    }

    public Period getTimeToWaitBeforeRetryingCircuit() {
        return timeToWaitBeforeRetryingCircuit;
    }
}
