package rikf.circuitbreaker;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class CircuitBreakerMatchers {
    public static Matcher<CircuitBreaker> open() {
        return new BaseMatcher<CircuitBreaker>() {
            private CircuitBreaker circuitBreaker;

            @Override
            public boolean matches(Object item) {
                if (!(item instanceof CircuitBreaker)) {
                    return false;
                }
                circuitBreaker = (CircuitBreaker) item;
                return circuitBreaker.isOpen();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Circuit breaker should be open");
            }

            @Override
            public void describeMismatch(Object item, Description description) {
                CircuitBreaker circuitBreaker = (CircuitBreaker) item;
                description.appendText("was " + circuitBreaker.getState());
            }

        };
    }

    public static Matcher<CircuitBreaker> closed() {
        return new BaseMatcher<CircuitBreaker>() {
            private CircuitBreaker circuitBreaker;

            @Override
            public boolean matches(Object item) {
                if (!(item instanceof CircuitBreaker)) {
                    return false;
                }
                circuitBreaker = (CircuitBreaker) item;
                return circuitBreaker.isClosed();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Circuit breaker should be closed");
            }


            @Override
            public void describeMismatch(Object item, Description description) {
                CircuitBreaker circuitBreaker = (CircuitBreaker) item;
                description.appendText("was " + circuitBreaker.getState());
            }
        };
    }
}
