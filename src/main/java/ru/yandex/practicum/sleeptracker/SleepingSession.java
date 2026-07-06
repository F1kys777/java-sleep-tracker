package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.time.LocalDateTime;

public class SleepingSession implements Comparable<SleepingSession> {
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

    public long getSessionDuration() {
        Duration sessionTime = Duration.between(startTime, endTime);
        return sessionTime.toMinutes();
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public int compareTo(SleepingSession other) {
        if (this.getSessionDuration() < other.getSessionDuration()) return -1;
        if (this.getSessionDuration() > other.getSessionDuration()) return 1;
        return 0;
    }
}
