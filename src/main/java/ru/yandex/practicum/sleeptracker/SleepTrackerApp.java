package ru.yandex.practicum.sleeptracker;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SleepTrackerApp {

    public static void main(String[] args) throws IOException {
        String filePath;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        Scanner scan = new Scanner(System.in);

        System.out.println("Введите путь к файлу sleep_log.txt");
        filePath = scan.next();

        try (BufferedReader reader = new BufferedReader(
                new FileReader(filePath, StandardCharsets.UTF_8))) {
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
    }
}