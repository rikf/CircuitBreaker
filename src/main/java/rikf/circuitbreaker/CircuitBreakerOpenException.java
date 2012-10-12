package rikf.circuitbreaker;

/**
 * When a circuit breaker is open then calls the external service are failed fast by throwing this exception
 */
public class CircuitBreakerOpenException extends RuntimeException {
    public CircuitBreakerOpenException() {
        super("Circuit breaker is open calls are currently failing fast");
    }
}
