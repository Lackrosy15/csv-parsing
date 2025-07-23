package org.example.saving;

import org.example.loading.CsvField;
import org.example.loading.Info;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

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

    /**
     * @param info обьект инфо из которого создаём строку файла. Один инфо -- одна строка
     * @param fields какие поля из инфо будем использовать
     * @param delimiter разделитель между полями в файле
     * @return строку с данными из инфо , разделенные delimiter
     */
    private static String createRow(Info info, List<CsvField> fields, String delimiter) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        StringBuilder row = new StringBuilder();
        for (int i = 0; i < fields.size(); i++) {
            CsvField field = fields.get(i);

            String fieldValue = "";
            if (CsvField.NAME.equals(field)) {
                fieldValue = info.getName() != null ? info.getName() : "";
            } else if (CsvField.PHONE.equals(field)) {
                fieldValue = info.getPhone() != null ? info.getPhone() : "";
            } else if (CsvField.DATE_TIME.equals(field)) {
                fieldValue = info.getDate() != null ? simpleDateFormat.format(info.getDate()) : "";
            } else if (CsvField.EMAIL.equals(field)) {
                fieldValue = info.getEmail() != null ? info.getEmail() : "";
            }
            row.append(fieldValue);
            if (i != fields.size() - 1) {
                row.append(delimiter);
            }
        }
        return row.append("\n").toString();
    }
}
