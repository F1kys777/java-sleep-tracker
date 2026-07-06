package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SleepTrackerAppTest {

    @Test
    void testAllSessionsCountEmpty() {
        AllSessionsCount analyzer = new AllSessionsCount();
        SleepAnalysisResult<Long> result = analyzer.apply(List.of());
        assertEquals(0L, result.getValue());
    }

    @Test
    void testAllSessionsCount() {
        AllSessionsCount analyzer = new AllSessionsCount();
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        Quality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 6, 30),
                        Quality.NORMAL
                )
        );
        SleepAnalysisResult<Long> result = analyzer.apply(sessions);
        assertEquals(2L, result.getValue());
    }

    @Test
    void testMinDurationEmpty() {
        MinDurationSessions analyzer = new MinDurationSessions();
        SleepAnalysisResult<Long> result = analyzer.apply(List.of());
        assertEquals(0L, result.getValue());
    }

    @Test
    void testMinDuration() {
        MinDurationSessions analyzer = new MinDurationSessions();
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        Quality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 5, 30),
                        Quality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 0, 30),
                        LocalDateTime.of(2025, 10, 3, 3, 0),
                        Quality.BAD
                )
        );
        SleepAnalysisResult<Long> result = analyzer.apply(sessions);
        assertEquals(150L, result.getValue());
    }

    @Test
    void testMaxDurationEmpty() {
        MaxDurationSessions analyzer = new MaxDurationSessions();
        SleepAnalysisResult<Long> result = analyzer.apply(List.of());
        assertEquals(0L, result.getValue());
    }

    @Test
    void testMaxDuration() {
        MaxDurationSessions analyzer = new MaxDurationSessions();
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        Quality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 8, 30),
                        Quality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 0, 30),
                        LocalDateTime.of(2025, 10, 3, 3, 0),
                        Quality.BAD
                )
        );
        SleepAnalysisResult<Long> result = analyzer.apply(sessions);
        assertEquals(570L, result.getValue());
    }

    @Test
    void testAverageDurationEmpty() {
        AverageDurationSessions analyzer = new AverageDurationSessions();
        SleepAnalysisResult<Double> result = analyzer.apply(List.of());
        assertEquals(0.0, result.getValue());
    }

    @Test
    void testAverageDuration() {
        AverageDurationSessions analyzer = new AverageDurationSessions();
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        Quality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 5, 30),
                        Quality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 0, 30),
                        LocalDateTime.of(2025, 10, 3, 3, 0),
                        Quality.BAD
                )
        );
        long d1 = sessions.get(0).getSessionDuration();
        long d2 = sessions.get(1).getSessionDuration();
        long d3 = sessions.get(2).getSessionDuration();
        double avg = (d1 + d2 + d3) / 3.0;
        SleepAnalysisResult<Double> result = analyzer.apply(sessions);
        assertEquals(avg, result.getValue());
    }

    @Test
    void testBadSessionCounterEmpty() {
        BadSessionCounter analyzer = new BadSessionCounter();
        SleepAnalysisResult<Long> result = analyzer.apply(List.of());
        assertEquals(0L, result.getValue());
    }

    @Test
    void testBadSessionCounter() {
        BadSessionCounter analyzer = new BadSessionCounter();
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        Quality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 5, 30),
                        Quality.BAD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 0, 30),
                        LocalDateTime.of(2025, 10, 3, 3, 0),
                        Quality.BAD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 22, 0),
                        LocalDateTime.of(2025, 10, 4, 7, 0),
                        Quality.NORMAL
                )
        );
        SleepAnalysisResult<Long> result = analyzer.apply(sessions);
        assertEquals(2L, result.getValue());
    }

    @Test
    void testSleeplessNightsAllCovered() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0),
                        Quality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 22, 0),
                        LocalDateTime.of(2025, 10, 3, 6, 0),
                        Quality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 22, 37),
                        LocalDateTime.of(2025, 10, 4, 7, 52),
                        Quality.GOOD
                )
        );
        SleeplessNights analyzer = new SleeplessNights();
        SleepAnalysisResult<Long> result = analyzer.apply(sessions);
        assertEquals(0L, result.getValue());
    }

    @Test
    void testSleeplessNightsEmpty() {
        SleeplessNights analyzer = new SleeplessNights();
        SleepAnalysisResult<Long> result = analyzer.apply(List.of());
        assertEquals(0L, result.getValue());
    }

    @Test
    void testSleeplessNights() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0),
                        Quality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 7, 0),
                        Quality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 4, 23, 0),
                        LocalDateTime.of(2025, 10, 5, 7, 0),
                        Quality.GOOD
                )
        );
        SleeplessNights analyzer = new SleeplessNights();
        SleepAnalysisResult<Long> result = analyzer.apply(sessions);
        assertEquals(1L, result.getValue());
    }

    @Test
    void testSleeplessNightsFirstDateCorrectionBeforeNoon() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 10, 0),
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        Quality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0),
                        Quality.GOOD
                )
        );
        SleeplessNights analyzer = new SleeplessNights();
        SleepAnalysisResult<Long> result = analyzer.apply(sessions);
        assertEquals(2L, result.getValue());
    }

    @Test
    void testSleeplessNightsFirstDateCorrectionAfterNoon() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0),
                        Quality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 23, 0),
                        LocalDateTime.of(2025, 10, 4, 7, 0),
                        Quality.GOOD
                )
        );
        SleeplessNights analyzer = new SleeplessNights();
        SleepAnalysisResult<Long> result = analyzer.apply(sessions);
        assertEquals(1L, result.getValue());
    }

    @Test
    void testUserTypeEmpty() {
        UserType analyzer = new UserType();
        SleepAnalysisResult<String> result = analyzer.apply(List.of());
        assertEquals("Недостаточно данных", result.getValue());
        assertEquals("Классификация пользователя невозможна", result.getDescription());
    }

    @Test
    void testUserTypeAllLarks() {
        UserType analyzer = new UserType();
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 21, 0),
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        Quality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 21, 30),
                        LocalDateTime.of(2025, 10, 3, 6, 30),
                        Quality.NORMAL
                )
        );
        SleepAnalysisResult<String> result = analyzer.apply(sessions);
        assertEquals("LARK", result.getValue());
    }

    @Test
    void testUserTypeMix() {
        UserType analyzer = new UserType();
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 21, 0),
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        Quality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 30),
                        LocalDateTime.of(2025, 10, 3, 9, 30),
                        Quality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 22, 30),
                        LocalDateTime.of(2025, 10, 4, 8, 0),
                        Quality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 4, 23, 0),
                        LocalDateTime.of(2025, 10, 5, 7, 30),
                        Quality.GOOD
                )
        );
        SleepAnalysisResult<String> result = analyzer.apply(sessions);
        assertEquals("PIGEON", result.getValue());
    }
}