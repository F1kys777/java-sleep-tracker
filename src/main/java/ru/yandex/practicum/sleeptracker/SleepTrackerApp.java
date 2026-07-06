package ru.yandex.practicum.sleeptracker;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SleepTrackerApp {

    public static void main(String[] args) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
        List<SleepingSession> sleepingSessions = null;

        String filePaths = args[0];

        try (BufferedReader reader = new BufferedReader(
                new FileReader(filePaths, StandardCharsets.UTF_8))) {
            List<String> lines = reader.lines().toList();
            sleepingSessions = lines.stream()
                    .map(line -> line.split(";"))
                    .filter(regexLine -> regexLine.length == 3)
                    .map(regexLine -> {
                        try {
                            LocalDateTime startTime = LocalDateTime.parse(regexLine[0], formatter);
                            LocalDateTime endTime = LocalDateTime.parse(regexLine[1], formatter);
                            Quality quality = Quality.valueOf(regexLine[2]);
                            return new SleepingSession(startTime, endTime, quality);
                        } catch (Exception e) {
                            System.err.println("Ошибка парсинга строки: " + String.join(";", regexLine));
                            return null;
                        }
                    })
                    .filter(session -> session != null)

                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }

        List<SleepingSession> sleepingSession = List.copyOf(sleepingSessions);

        List<Function> functionList = new ArrayList<>();
        functionList.add(new AllSessionsCount());
        functionList.add(new MinDurationSessions());
        functionList.add(new MaxDurationSessions());
        functionList.add(new AverageDurationSessions());
        functionList.add(new BadSessionCounter());
        functionList.add(new SleeplessNights());
        functionList.add(new UserType());

        functionList.stream()
                .map(f -> f.apply(sleepingSession))
                .forEach(System.out::println);
    }
}