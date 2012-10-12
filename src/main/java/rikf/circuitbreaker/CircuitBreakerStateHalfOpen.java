package rikf.circuitbreaker;

public class CircuitBreakerStateHalfOpen implements CircuitBreakerState {


    @Override
    public void preExecute(CircuitBreaker circuitBreaker) {

    }

    @Override
    public void postExecute(CircuitBreaker circuitBreaker) {
        circuitBreaker.close();
    }

    @Override
    public void onExecutionError(CircuitBreaker circuitBreaker) {
        circuitBreaker.open();
    }


    public String toString() {
        return "Half-Open";
    }


}
