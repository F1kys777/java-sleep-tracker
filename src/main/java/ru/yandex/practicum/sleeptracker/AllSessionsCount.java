package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class AllSessionsCount implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sessions) {
        return new SleepAnalysisResult<>("Количество сессий сна", (long) sessions.size());
    }
}
