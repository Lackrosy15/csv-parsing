package org.example;

import org.example.loading.CsvField;
import org.example.loading.Info;
import org.example.loading.InfoLoader;
import org.example.saving.InfoSaver;
import org.example.util.InfoListUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Info> base = InfoLoader.loadInfosFromKommocsv(List.of(CsvField.NAME, CsvField.DATE_TIME, CsvField.PHONE)); //основная база

        List<Info> secondBase = InfoLoader.loadInfos(new File("base2.csv"), List.of(CsvField.PHONE)); //база с номерами которые надо убрать

        List<Info> diff = InfoListUtil.firstMinusSecond(base, secondBase); //убираю из первой базы вторую по номерам

        List<Info> countGreaterThan = InfoListUtil.filterByCountGreaterOrEqualThan(diff, 5); //оставляю только записи, номеров которых больше 5

        List<Info> latest = InfoListUtil.onlyLatest(countGreaterThan);//создаю список в котором самые последние записи

        //сохраняю в файл список, определенные поля с нужным разделителем для csv формата (можно поменять на \t
        // чтобы удобнее было копировать и вставлять в гугл таблицы)
        InfoSaver.saveInfos("result.csv", latest, List.of(CsvField.NAME, CsvField.PHONE, CsvField.DATE_TIME), "\t");
    }
}