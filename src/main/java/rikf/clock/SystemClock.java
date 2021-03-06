package rikf.clock;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

/**
 * Default implementation that is used in production.
 */
public class SystemClock implements Clock {
    @Override
    public LocalDateTime getCurrentTimestamp() {
        return new LocalDateTime();
    }
}
