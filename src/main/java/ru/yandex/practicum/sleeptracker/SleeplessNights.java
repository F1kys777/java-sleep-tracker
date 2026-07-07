package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class SleeplessNights implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {
    private static final LocalTime NIGHT_START = LocalTime.of(0, 0);
    private static final LocalTime NIGHT_END = LocalTime.of(6, 0);
    private static final String DESCRIPTION = "Ночей без сна";

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult<>(DESCRIPTION, 0L);
        }

        LocalDate firstDate = sessions.stream()
                .map(SleepingSession::getStartTime)
                .min(LocalDateTime::compareTo)
                .map(LocalDateTime::toLocalDate)
                .orElseThrow();

        LocalDate lastDate = sessions.stream()
                .map(SleepingSession::getEndTime)
                .max(LocalDateTime::compareTo)
                .map(LocalDateTime::toLocalDate)
                .orElseThrow();

        LocalDateTime firstStart = sessions.stream()
                .min(Comparator.comparing(SleepingSession::getStartTime))
                .map(SleepingSession::getStartTime)
                .orElseThrow();
        if (firstStart.getHour() < 12) {
            firstDate = firstDate.minusDays(1);
        } else {
            firstDate = firstDate.plusDays(1);
        }

        long sleeplessNights = Stream.iterate(firstDate, date -> !date.isAfter(lastDate), date -> date.plusDays(1))
                .filter(date -> {
                    LocalDateTime nightStart = LocalDateTime.of(date, NIGHT_START);
                    LocalDateTime nightEnd = LocalDateTime.of(date, NIGHT_END);
                    return sessions.stream().noneMatch(session -> {
                        LocalDateTime sessionStart = session.getStartTime();
                        LocalDateTime sessionEnd = session.getEndTime();
                        return sessionStart.isBefore(nightEnd) && sessionEnd.isAfter(nightStart);
                    });
                })
                .count();

        return new SleepAnalysisResult<>(DESCRIPTION, sleeplessNights);
    }
}