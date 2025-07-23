package org.example.dateDiff;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

public class DateDiff {
    private static final SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy");
    //считает разницу между датами в днях. Пример записи в date_diff.csv - 12.03.2012;22.12.2022
    public static void main(String[] args) throws IOException {
        Stream<String> stringStream = Files.lines(Path.of("C:\\Users\\User\\IdeaProjects\\csv-parsing\\input\\date_diff"), Charset.forName("windows-1251"))
                //.skip(1)
                .map(line -> line.split(";"))
                .map(arr -> {
                    Date date1 = parse(formatDate, arr[0]);
                    Date date2 = parse(formatDate, arr[1]);
                    long diff = Duration.between(date1.toInstant(), date2.toInstant()).toDays();
                    return arr[0] + "\t" + arr[1] + "\t" + diff;
                });
        try (FileWriter fileWriter = new FileWriter("C:\\Users\\User\\IdeaProjects\\csv-parsing\\results\\date_diff_result")) {
            stringStream.forEach(string -> writeInfo(fileWriter, string));
        }
    }

    private static void writeInfo(FileWriter fileWriter, String line) {
        try {
            fileWriter.write(line + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Date parse(SimpleDateFormat format, String string) {
        try {
            return format.parse(string);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
