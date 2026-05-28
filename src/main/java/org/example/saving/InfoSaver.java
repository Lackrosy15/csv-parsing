package org.example.saving;

import org.example.loading.Contact;
import org.example.loading.CsvField;
import org.example.loading.Info;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class InfoSaver {
    /**
     * Сохранение данных их списка инфо
     *
     * @param csvFileName имя файла который будет в папке results
     * @param infos список записей которые нужно сохранить
     * @param fields какие поля из инфо нужно сохранять
     * @param delimiter разделитель
     * @throws IOException
     */
    public static void saveInfos(String csvFileName, List<Info> infos, List<CsvField> fields, String delimiter) throws IOException {
        new File("results").mkdir();
        File csvFile = new File("results/" + csvFileName);
        csvFile.delete();
        csvFile.createNewFile();
        try (FileWriter fileWriter = new FileWriter(csvFile)) {
            for (Info info : infos) {
                fileWriter.write(createRow(info, fields, delimiter));
            }
        }
    }

    public static void saveContacts(String csvFileName, List<Contact> contacts, String delimiter) throws IOException {
        new File("results").mkdirs();
        File csvFile = new File("results", csvFileName);
        csvFile.delete();
        csvFile.createNewFile();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile, StandardCharsets.UTF_8))) {
            for (Contact contact : contacts) {
                String phone = contact.getPhone();
                String dates = contact.getDate().stream()
                        .map(simpleDateFormat::format)
                        .collect(Collectors.joining(";"));
                String totalBudget = contact.getSumBudgets();

                String line = String.join(delimiter, phone, dates, totalBudget);
                writer.write(line);
                writer.newLine();
            }
        }
    }

    /**
     * @param info обьект инфо из которого создаём строку файла. Один инфо -- одна строка
     * @param fields какие поля из инфо будем использовать
     * @param delimiter разделитель между полями в файле
     * @return строку с данными из инфо, разделенные delimiter
     */
    private static String createRow(Info info, List<CsvField> fields, String delimiter) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        StringBuilder row = new StringBuilder();
        for (int i = 0; i < fields.size(); i++) {
            CsvField field = fields.get(i);

            String fieldValue = "";
            if (CsvField.NAME.equals(field)) {
                fieldValue = info.getName() != null ? info.getName() : "";
            } else if (CsvField.ID.equals(field)) {
                fieldValue = info.getId() != null ? info.getId() : "";
            } else if (CsvField.PHONE.equals(field)) {
                fieldValue = info.getPhone() != null ? info.getPhone() : "";
            } else if (CsvField.DATE_TIME.equals(field)) {
                fieldValue = info.getDate() != null ? simpleDateFormat.format(info.getDate()) : "";
            } else if (CsvField.EMAIL.equals(field)) {
                fieldValue = info.getEmail() != null ? info.getEmail() : "";
            } else if (CsvField.CITY.equals(field)) {
                fieldValue = info.getCity() != null ? info.getCity() : "";
            }
            row.append(fieldValue);
            if (i != fields.size() - 1) {
                row.append(delimiter);
            }
        }
        return row.append("\n").toString();
    }

}
