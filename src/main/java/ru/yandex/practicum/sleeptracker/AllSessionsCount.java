package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class AllSessionsCount implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {
    private static final String DESCRIPTION = "Количество сессий сна";

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sessions) {
        return new SleepAnalysisResult<>(DESCRIPTION, (long) sessions.size());
    }
}
