package org.example.dao;

import org.example.entity.TimeInterval;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TimeIntervalDaoMemory implements GenericDao<TimeInterval> {
    private final List<TimeInterval> timeIntervals = new ArrayList<>();

    public TimeIntervalDaoMemory() {
        timeIntervals.add(new TimeInterval(LocalDate.of(2025, 6, 1), LocalDate.of(2025, 9, 30), "giorni di apertura"));
        timeIntervals.add(new TimeInterval(LocalDate.of(2025, 12, 1), LocalDate.of(2025, 12, 15), "giorni di apertura"));
    }

    @Override
    public void create(TimeInterval entity) {
        if (read(entity.getStartDate(), entity.getEndDate(), entity.getType()) != null) {
            return;
        }
        timeIntervals.add(entity);
    }

    @Override
    public TimeInterval read(Object... keys) {
        if (keys.length != 3) return null;
        LocalDate startDate = (LocalDate) keys[0];
        LocalDate endDate = (LocalDate) keys[1];
        String type = (String) keys[2];

        for (TimeInterval interval : timeIntervals) {
            if (interval.getStartDate().equals(startDate) &&
                    interval.getEndDate().equals(endDate) &&
                    interval.getType().equals(type)) {
                return interval;
            }
        }
        return null;
    }

    @Override
    public void update(TimeInterval entity) {
        delete(entity.getStartDate(), entity.getEndDate(), entity.getType());
        create(entity);
    }

    @Override
    public void delete(Object... keys) {
        if (keys.length != 3) return;
        LocalDate startDate = (LocalDate) keys[0];
        LocalDate endDate = (LocalDate) keys[1];
        String type = (String) keys[2];

        timeIntervals.removeIf(interval ->
                interval.getStartDate().equals(startDate) &&
                        interval.getEndDate().equals(endDate) &&
                        interval.getType().equals(type)
        );
    }

    @Override
    public List<TimeInterval> readAll() {
        return new ArrayList<>(timeIntervals);
    }
}
