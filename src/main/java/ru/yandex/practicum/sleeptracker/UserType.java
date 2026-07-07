package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserType implements Function<List<SleepingSession>, SleepAnalysisResult<String>> {
    private static final LocalTime NIGHT_START = LocalTime.of(0, 0);
    private static final LocalTime NIGHT_END = LocalTime.of(6, 0);
    private static final LocalTime OWL_SLEEP_START = LocalTime.of(23, 0);
    private static final LocalTime OWL_WAKE_END = LocalTime.of(9, 0);
    private static final LocalTime LARK_SLEEP_END = LocalTime.of(22, 0);
    private static final LocalTime LARK_WAKE_END = LocalTime.of(7, 0);
    private static final String DESCRIPTION = "Хронотип";

    @Override
    public SleepAnalysisResult<String> apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult<>(DESCRIPTION, "Недостаточно данных для определения");
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

        Map<Type, Long> counts = Stream.iterate(firstDate,
                        date -> !date.isAfter(lastDate),
                        date -> date.plusDays(1))
                .map(date -> {
                    LocalDateTime nightStart = LocalDateTime.of(date, NIGHT_START);
                    LocalDateTime nightEnd = LocalDateTime.of(date, NIGHT_END);

                    List<SleepingSession> nightSessions = sessions.stream()
                            .filter(session -> {
                                LocalDateTime sessionStartTimeStart = session.getStartTime();
                                LocalDateTime sessionEndTimeEnd = session.getEndTime();
                                return sessionStartTimeStart.isBefore(nightEnd) && sessionEndTimeEnd.isAfter(nightStart);
                            })
                            .collect(Collectors.toList());

                    if (nightSessions.isEmpty()) {
                        return null;
                    }

                    LocalDateTime sleepStart = nightSessions.stream()
                            .map(SleepingSession::getStartTime)
                            .min(LocalDateTime::compareTo)
                            .orElseThrow();

                    LocalDateTime wakeEnd = nightSessions.stream()
                            .map(SleepingSession::getEndTime)
                            .max(LocalDateTime::compareTo)
                            .orElseThrow();

                    return classifyType(sleepStart.toLocalTime(), wakeEnd.toLocalTime());
                })
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        if (counts.isEmpty()) {
            return new SleepAnalysisResult<>(DESCRIPTION, "Недостаточно данных");
        }

        Type result = counts.entrySet().stream()
                .max((e1, e2) -> {
                    int res = Long.compare(e1.getValue(), e2.getValue());
                    if (res != 0) return res;
                    if (e1.getKey() == Type.PIGEON) return 1;
                    if (e2.getKey() == Type.PIGEON) return -1;
                    return 0;
                })
                .map(Map.Entry::getKey)
                .orElse(Type.PIGEON);

        return new SleepAnalysisResult<>(DESCRIPTION, result.toString());
    }

    private Type classifyType(LocalTime sleepStart, LocalTime wakeEnd) {
        boolean isOwl = sleepStart.isAfter(OWL_SLEEP_START) && wakeEnd.isAfter(OWL_WAKE_END);
        boolean isLark = sleepStart.isBefore(LARK_SLEEP_END) && wakeEnd.isBefore(LARK_WAKE_END);

        if (isOwl) return Type.OWL;
        if (isLark) return Type.LARK;
        return Type.PIGEON;
    }
}