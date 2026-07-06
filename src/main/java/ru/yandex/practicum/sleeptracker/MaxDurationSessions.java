package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class MaxDurationSessions implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult<>("Максимальная длительность", 0L);
        }
        long maxDuration = sessions.stream()
                .max(SleepingSession::compareTo)
                .map(SleepingSession::getSessionDuration)
                .orElse(0L);
        return new SleepAnalysisResult<>("Максимальная продолжительность сна в минутах", maxDuration);
    }
}
