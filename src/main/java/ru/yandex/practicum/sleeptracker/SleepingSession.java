package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;

public class SleepingSession {
    LocalDateTime startTime;
    LocalDateTime endTime;
    Quality quality;

    public SleepingSession(LocalDateTime startTime, LocalDateTime endTime, Quality quality) {
        this.endTime = endTime;
        this.startTime = startTime;
        this.quality = quality;
    }

    public Quality getQuality() {
        return quality;
    }
}
