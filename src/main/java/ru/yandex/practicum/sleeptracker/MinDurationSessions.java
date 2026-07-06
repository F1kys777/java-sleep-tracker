package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class MinDurationSessions implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult<>("Минимальная длительность", 0L);
        }
        long minDuration = sessions.stream()
                .min(SleepingSession::compareTo)
                .map(SleepingSession::getSessionDuration)
                .orElse(0L);
        return new SleepAnalysisResult<>("Минимальная продолжительность сна в минутах", minDuration);
    }
}
