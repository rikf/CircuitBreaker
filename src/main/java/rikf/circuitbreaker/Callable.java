package rikf.circuitbreaker;

/**
 * Standard callable.
 */
public interface Callable<T> {
    public T execute();
}
