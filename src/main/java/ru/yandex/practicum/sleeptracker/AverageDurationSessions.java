package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class AverageDurationSessions implements Function<List<SleepingSession>, SleepAnalysisResult<Double>> {

    @Override
    public SleepAnalysisResult<Double> apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult<>("Средняя длительность", 0.00);
        }
        double averageDuration = sessions.stream()
                .mapToLong(SleepingSession::getSessionDuration)
                .average()
                .orElse(0.00);
        return new SleepAnalysisResult<>("Средняя продолжительность сна в минутах", averageDuration);
    }
}
