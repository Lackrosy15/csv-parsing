package org.example.loading;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class InfoLoader {

    /**
     * Метод создает список инфо из файла в папке kommocsv. Вызывает метод {@link #loadInfos}
     *
     * @param fields список полей которые есть в файле
     * @return список инфо с заполнеными полями которые указаны в списке fields
     * @throws IOException
     */
    public static List<Info> loadInfosFromKommocsv(List<CsvField> fields) throws IOException {
        File sourceDir = new File("kommocsv");
        if (!sourceDir.exists()) {
            sourceDir.mkdir();
        }
        if(sourceDir.listFiles().length == 0) {
            throw new RuntimeException("В папке kommocsv нет файла");
        }
        if(sourceDir.listFiles().length > 1) {
            throw new RuntimeException("В папке kommocsv больше одного файла");
        }
        File csvFile = sourceDir.listFiles()[0];
        return loadInfos(csvFile, fields);
    }

    /**
     * Метод создает список инфо из файла и оставляет только те записи, в которых есть номер
     *
     * @param csvFile файл из которого грузим базу
     * @param fields список полей которые есть в файле
     * @return список инфо с заполнеными полями которые указаны в списке fields
     * @throws IOException
     */
    public static List<Info> loadInfos(File csvFile, List<CsvField> fields) throws IOException {
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(fields.stream().map(Enum::name).toList().toArray(new String[]{}))
                .setSkipHeaderRecord(false)
                .setDelimiter(";")
                .setTrim(true)
                .get();

        CSVParser csvParser = csvFormat.parse(new FileReader(csvFile, Charset.forName("windows-1251")));
        return csvParser.stream()
                .map(csvRecord -> new Info(csvRecord))
                .filter(info -> info.getPhone()!=null)
                .toList();
    }
}
