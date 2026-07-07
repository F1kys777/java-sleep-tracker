package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class MaxDurationSessions implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {
    private static final String DESCRIPTION = "Максимальная продолжительность сна в минутах";

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sessions) {
        long maxDuration = sessions.stream()
                .max(SleepingSession::compareTo)
                .map(SleepingSession::getSessionDuration)
                .orElse(0L);
        return new SleepAnalysisResult<>(DESCRIPTION, maxDuration);
    }
}
