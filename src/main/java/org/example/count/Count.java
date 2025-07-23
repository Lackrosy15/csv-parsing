package org.example.count;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Count {
    public static void main(String[] args) throws IOException {

        //кол-во вхождений
        Files.lines(Path.of("C:\\Users\\User\\IdeaProjects\\csv-parsing\\input\\count"), Charset.forName("UTF-8"))
                .map(s -> s.replaceAll("м\\.\\s*", "")
                        .replaceAll("М\\.\\s*", "")
                        .replaceAll("м\\s+", "")
                        .replaceAll("М\\s+", "")
                        .replaceAll("місто\\s*", "")
                        .replaceAll("Місто\\s*", "")
                        .replaceAll("с\\.\\s*", "")
                        .replaceAll("с\\s*", "")
                        .replaceAll("С\\.\\s*", "")
                        .replaceAll("С\\s*", "")
                        .replaceAll("село\\s*", "")
                        .replaceAll("Село\\s*", "")
                        .replaceAll("смт\\.\\s*", "")
                        .replaceAll("смт\\s*", "")
                        .replaceAll("т\\.\\s*", "")
                        .replaceAll("[\".]", "")
                        .trim())
                .filter(Predicate.not(String::isBlank))
                // Группировка и подсчет
                .collect(Collectors.groupingBy(
                        s -> s,
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                // Сортировка по количеству вхождений (по убыванию)
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                // Форматирование вывода
                .forEach(entry -> System.out.println(entry.getKey() + "\t" + entry.getValue()));

        /*
        //подсчет количества покупок в разных городах и общий бюджет
        String delimiter = ";";
        Files.lines(Path.of("C:\Users\User\IdeaProjects\csv-parsing\input\count"), Charset.forName("UTF-8"))
                .filter(Predicate.not(String::isBlank))
                .map(s -> s.replaceAll("м\\.\\s*", "").replaceAll("М\\.\\s*", "")
                        .replaceAll("м\\s+", "").replaceAll("М\\s+", "")
                        .replaceAll("місто\\s*", "").replaceAll("Місто\\s*", ""))
                .map(s -> s.replaceAll("с\\.\\s*", "").replaceAll("С\\.\\s*", "")
                        .replaceAll("село\\s*", "").replaceAll("Село\\s*", ""))
                .map(s -> s.replaceAll("смт\\.\\s*", "").replaceAll("смт\\s*", ""))
                .map(s -> s.replaceAll("т\\.\\s*", ""))
                .map(s -> s.replaceAll("[\".]", ""))

                //.peek(string -> System.out.println(string))
                .filter(string -> string.split(delimiter).length > 1)
                .map(s -> s.split(delimiter))
                .collect(Collectors.groupingBy(arr -> arr[1], Collectors.summarizingInt(arr-> Integer.parseInt(arr[0]))))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(e-> e.getValue().getCount()))
                .map(e -> e.getKey() + "\t" + e.getValue().getCount() + "\t" + e.getValue().getSum())
                .forEach(System.out::println);
         */
    }
}
