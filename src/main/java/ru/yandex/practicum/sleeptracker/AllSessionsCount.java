package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class AllSessionsCount implements Function<List<SleepingSession>, SleepAnalysisResult<Integer>> {

    @Override
    public SleepAnalysisResult<Integer> apply(List<SleepingSession> sessions) {
        int count = sessions.size();
        return new SleepAnalysisResult<>("Количество сессий сна", count);
    }
}
