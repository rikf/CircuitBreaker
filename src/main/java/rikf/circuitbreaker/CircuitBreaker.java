package rikf.circuitbreaker;

/**
 * Circuit breaker as taken form the release it book. This implementation of the circuit breaker executes a Callable
 * object that is then responsible for composing and executing the external service this circuit breaker is protecting
 *
 * @param <T> the type is returned when executing this command
 */
public interface CircuitBreaker<T> {
    /**
     * Attempt a call to the underlying service as wrapped by the command
     * Implementers should call {@link Callable#execute()} to invoke an external service
     *
     * @param command the command object that combines an external service call with the data needed to call it
     * @return T the result of the command execution
     */
    public T attemptCall(Callable<T> command);

    /**
     * Move circuit breaker to open state see {@link #isOpen()}
     */
    public void open();

    /**
     * Move circuit breaker to closed state see {@link #isClosed()}
     */
    public void close();

    /**
     * Move circuit breaker to half open state see {@link #isHalfOpen()}
     */
    public void halfOpen();


    /**
     * The name of this circuit breaker.
     *
     * @return the name of this circuit breaker
     */
    public String getName();


    /**
     * Is this circuit open? If it is then it means that called to the underlying service are currently blocked and should fail fast.
     *
     * @return true if the circuit breaker state is open
     */
    public boolean isOpen();

    /**
     * The circuit breaker is closed and we are submitting calls to the external service
     *
     * @return true if the circuit breaker closed
     */
    public boolean isClosed();

    /**
     * In this state if the next call succeeds the circuit breaker is moved to a closed state and if it fails it is moved into a closed state
     *
     * @return true if the circuit breaker is in a half open state
     */
    public boolean isHalfOpen();


    public CircuitBreakerState getState();

}
