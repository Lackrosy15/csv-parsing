package org.example;

import org.example.dateDiff.DateDiff;
import org.example.loading.CsvField;
import org.example.loading.Info;
import org.example.loading.InfoLoader;
import org.example.saving.InfoSaver;
import org.example.util.InfoListUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Info> mainBase = InfoLoader.loadInfosFromKommocsv(List.of(CsvField.ID, CsvField.NAME, CsvField.PHONE)); //основная база
        mainBase = InfoListUtil.uniqValuesByPhoneNumber(mainBase);

      //  List<Info> secondBase = InfoLoader.loadInfos(new File("input/prev.csv"), List.of(CsvField.PHONE)); //база с номерами которые надо убрать

        // List<Info> diff = InfoListUtil.firstMinusSecond(mainBase, secondBase); //убираю из первой базы вторую по номерам
        //  diff = InfoListUtil.uniqValuesByPhoneNumber(diff);

      //  List<Info> countGreaterThan = InfoListUtil.filterByCountGreaterOrEqualThan(mainBase, 2);


    //    List<Info> more = InfoListUtil.filterByMoreDatePurchase(countGreaterThan, DateDiff.parse(new SimpleDateFormat("dd.MM.yyyy"), "01.03.2025"));
      //  List<Info> uniqNumber = InfoListUtil.uniqValuesByPhoneNumber(more);

   //     List<Info> finalList = InfoListUtil.firstMinusSecond(uniqNumber, secondBase);

        // List<Info> latest = InfoListUtil.onlyLatest(countGreaterThan);//создаю список, в котором самые последние записи

        //сохраняю в файл список, определенные поля с нужным разделителем для csv формата (можно поменять на \t
        // чтобы удобнее было копировать и вставлять в гугл таблицы)
        InfoSaver.saveInfos("result.csv", mainBase, List.of(CsvField.ID, CsvField.NAME, CsvField.PHONE), "\t");
    }
}