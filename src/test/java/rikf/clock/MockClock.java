package rikf.clock;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.Period;

public class MockClock implements Clock {

    public MockClock(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public MockClock() {
    }

    private LocalDateTime startTime = new LocalDateTime();

    @Override
    public LocalDateTime getCurrentTimestamp() {
        return startTime;
    }


    public void tick(Period tickSize) {
        startTime = startTime.plus(tickSize);

    }
}
