package rikf.circuitbreaker;

/**
 * This interface handles the behavior associated with different circuit breaker states
 */
public interface CircuitBreakerState {
    /**
     * Before a call is submitted to a external service this method is called
     *
     * @param circuitBreaker
     */
    public void preExecute(CircuitBreaker circuitBreaker);

    /**
     * After a call is submitted to a external service this method is called
     *
     * @param circuitBreaker
     */
    public void postExecute(CircuitBreaker circuitBreaker);

    /**
     * When call to external service fails this method is invoked
     *
     * @param circuitBreaker
     */
    public void onExecutionError(CircuitBreaker circuitBreaker);

}
