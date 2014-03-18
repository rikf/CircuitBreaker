package rikf.clock;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

/**
 * Clock interface this is externalised to make testing possible
 */
public interface Clock {
    public LocalDateTime getCurrentTimestamp();
}
