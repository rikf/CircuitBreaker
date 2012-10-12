package rikf.circuitbreaker.clock;

import org.joda.time.LocalTime;
import org.joda.time.Period;

public class MockClock implements Clock {

    private LocalTime startTime = new LocalTime();

    @Override
    public LocalTime getCurrentTime() {
        return startTime;
    }


    public void tick(Period tickSize) {
        startTime = startTime.plus(tickSize);

    }
}
