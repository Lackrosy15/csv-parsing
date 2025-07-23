package org.example.setDiff;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SetDiff {

    //удаление из множества set1 множества set2
    public static void main(String[] args) throws IOException {
        Set<String> set1 = Files.lines(Path.of("C:\\Users\\User\\IdeaProjects\\csv-parsing\\input\\set1"), Charset.forName("windows-1251"))
                .filter(Predicate.not(String::isBlank))
                .collect(Collectors.toSet());
        Set<String> set2 = Files.lines(Path.of("C:\\Users\\User\\IdeaProjects\\csv-parsing\\input\\set2"), Charset.forName("windows-1251"))
                .filter(Predicate.not(String::isBlank))
                .collect(Collectors.toSet());
        System.out.println("Set 1 count: " + set1.size());
        System.out.println("Set 2 count: " + set2.size());
        set1.removeAll(set2);
        System.out.println("Result set count: " + set1.size());

        File setDiff = new File("C:\\Users\\User\\IdeaProjects\\csv-parsing\\results\\sets_diff_result");
        setDiff.delete();
        setDiff.createNewFile();

        writeInfo(set1, setDiff);
    }

    private static void writeInfo(Collection<String> phones, File file) {
        try(FileWriter fileWriter = new FileWriter(file)) {
            phones.forEach(phone -> {
                try {
                    fileWriter.write(phone + "\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
