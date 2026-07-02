package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class BadSessionCounter implements Function {

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sessions) {
        long bad = sessions.stream()
                .filter(s -> s.getQuality() == Quality.BAD)
                .count();
        return new SleepAnalysisResult<>("Количество сессий с плохим качеством", bad);
    }
}
