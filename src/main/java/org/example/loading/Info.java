package org.example.loading;

import lombok.Getter;
import lombok.ToString;
import org.apache.commons.csv.CSVRecord;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

@Getter
@ToString
public class Info {
    private Date date;
    private String phone;
    private String email;
    private String name;
    private String id;

    //создание объекта и фильтрация телефона
    public Info(CSVRecord csvRecord) {
        //DateTimeFormatter format = DateTimeFormatter.o("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        try {
            if (csvRecord.isMapped(CsvField.NAME.name())) {
                name = csvRecord.get(CsvField.NAME);
            }
            if (csvRecord.isMapped(CsvField.ID.name())) {
                id = csvRecord.get(CsvField.ID);
            }
            if (csvRecord.isMapped(CsvField.DATE_TIME.name())) {
                String dateStr = csvRecord.get(CsvField.DATE_TIME);
                if (dateStr != null && !dateStr.trim().isEmpty()) {
                    date = format.parse(dateStr);
                }
            }
            if (csvRecord.isMapped(CsvField.PHONE.name())) {
                phone = loadPhone(csvRecord.get(CsvField.PHONE));
            }
            if (csvRecord.isMapped(CsvField.EMAIL.name())) {
                email = csvRecord.get(CsvField.EMAIL);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //очистка номеров и выбор последнего
    private String loadPhone(String phonesString) {
        phonesString = phonesString.replaceAll("[\"'\\s-_()+.]", "");
        var phones = new ArrayList<>(Arrays.stream(phonesString.split(",")).map(String::trim).toList());
        Collections.reverse(phones);
        return phones.stream()
                .filter(p -> p.startsWith("380") && p.length() == 12)
                .findFirst()
                .orElse(phones.stream().filter(p -> p.startsWith("0") && p.length() == 10).map(s -> "38" + s).findFirst().orElse(""));
    }
}

