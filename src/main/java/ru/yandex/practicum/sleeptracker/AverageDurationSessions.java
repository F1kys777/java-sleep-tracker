package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class AverageDurationSessions implements Function<List<SleepingSession>, SleepAnalysisResult<Double>> {
    private static final String DESCRIPTION = "Средняя продолжительность сна в минутах";

    @Override
    public SleepAnalysisResult<Double> apply(List<SleepingSession> sessions) {
        double averageDuration = sessions.stream()
                .mapToLong(SleepingSession::getSessionDuration)
                .average()
                .orElse(0.00);
        return new SleepAnalysisResult<>(DESCRIPTION, averageDuration);
    }
}
