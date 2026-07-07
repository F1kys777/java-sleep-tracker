package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class MinDurationSessions implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {
    private static final String DESCRIPTION = "Минимальная продолжительность сна в минутах";

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sessions) {
        long minDuration = sessions.stream()
                .min(SleepingSession::compareTo)
                .map(SleepingSession::getSessionDuration)
                .orElse(0L);
        return new SleepAnalysisResult<>(DESCRIPTION, minDuration);
    }
}
