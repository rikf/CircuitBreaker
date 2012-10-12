package rikf.circuitbreaker;

import clock.Clock;
import org.joda.time.LocalTime;
import org.joda.time.ReadablePeriod;

import java.util.Deque;
import java.util.concurrent.LinkedBlockingDeque;


public class CircuitBreakerStateClosed implements CircuitBreakerState {

    private volatile Deque<LocalTime> failures = new LinkedBlockingDeque<LocalTime>();
    private final Clock clock;
    private int numberOfAcceptableFailuresWithinWindow;
    private ReadablePeriod failureWindow;

    public CircuitBreakerStateClosed(Clock clock, int numberOfAcceptableFailuresWithinWindow, ReadablePeriod failureWindow) {
        this.clock = clock;
        this.numberOfAcceptableFailuresWithinWindow = numberOfAcceptableFailuresWithinWindow;
        this.failureWindow = failureWindow;
    }


    @Override
    public void preExecute(CircuitBreaker circuitBreaker) {
    }

    @Override
    public void postExecute(CircuitBreaker circuitBreaker) {
    }

    @Override
    public void onExecutionError(CircuitBreaker circuitBreaker) {
        failures.push(clock.getCurrentTime());


        removeFailuresOutsideOfErrorWindow();
        if (failures.size() >= numberOfAcceptableFailuresWithinWindow) {
            circuitBreaker.open();
        }
    }

    private void removeFailuresOutsideOfErrorWindow() {
        final LocalTime cutOffThreshold = clock.getCurrentTime().minus(failureWindow);
        while (failures.size() > 0 && failures.peekLast().isBefore(cutOffThreshold)) {
            failures.pollLast();
        }
    }


    public String toString() {
        return "Closed";
    }
}
