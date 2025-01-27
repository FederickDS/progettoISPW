package org.example.dao;

import org.example.entity.DailyTimeInterval;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DailyTimeIntervalDaoMemory implements GenericDao<DailyTimeInterval> {
    private final List<DailyTimeInterval> dailyTimeIntervals = new ArrayList<>();

    public DailyTimeIntervalDaoMemory() {
        dailyTimeIntervals.add(new DailyTimeInterval(LocalDate.of(2025, 6, 1), LocalDate.of(2025, 9, 30), "opening"));
        dailyTimeIntervals.add(new DailyTimeInterval(LocalDate.of(2025, 12, 1), LocalDate.of(2025, 12, 15), "opening"));
    }

    @Override
    public void create(DailyTimeInterval entity) {
        if (read(entity.getStartDate(), entity.getEndDate(), entity.getType()) != null) {
            return;
        }
        dailyTimeIntervals.add(entity);
    }

    @Override
    public DailyTimeInterval read(Object... keys) {
        if (keys.length != 3) return null;
        LocalDate startDate = (LocalDate) keys[0];
        LocalDate endDate = (LocalDate) keys[1];
        String type = (String) keys[2];

        for (DailyTimeInterval interval : dailyTimeIntervals) {
            if (interval.getStartDate().equals(startDate) &&
                    interval.getEndDate().equals(endDate) &&
                    interval.getType().equals(type)) {
                return interval;
            }
        }
        return null;
    }

    @Override
    public void update(DailyTimeInterval entity) {
        delete(entity.getStartDate(), entity.getEndDate(), entity.getType());
        create(entity);
    }

    @Override
    public void delete(Object... keys) {
        if (keys.length != 3) return;
        LocalDate startDate = (LocalDate) keys[0];
        LocalDate endDate = (LocalDate) keys[1];
        String type = (String) keys[2];

        dailyTimeIntervals.removeIf(interval ->
                interval.getStartDate().equals(startDate) &&
                        interval.getEndDate().equals(endDate) &&
                        interval.getType().equals(type)
        );
    }

    @Override
    public List<DailyTimeInterval> readAll() {
        return new ArrayList<>(dailyTimeIntervals);
    }
}
