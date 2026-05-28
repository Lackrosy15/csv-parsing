package org.example;

import org.example.dateDiff.DateDiff;
import org.example.loading.Contact;
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
        List<Info> mainBase = InfoLoader.loadInfosFromKommocsv(List.of(CsvField.ID, CsvField.DATE_TIME, CsvField.EMAIL, CsvField.PHONE));
        //List<Contact> contacts = InfoListUtil.summarizeBudget(mainBase);
        // contacts = InfoListUtil.iniqValuesByPhoneNumber(contacts);
        //    InfoSaver.saveContacts("result.csv", contacts, List.of(CsvField.ID, CsvField.PHONE), "\t");
        //основная база
        //mainBase = InfoListUtil.uniqValuesByPhoneNumber(mainBase);
        List<Info> countGreaterThan = InfoListUtil.filterByCountGreaterOrEqualThan(mainBase, 4);
        countGreaterThan = InfoListUtil.uniqValuesByPhoneNumber(countGreaterThan);
        //List<Info> finalList = InfoListUtil.filterBySumBudget(countGreaterThan, 2000);
        //  countGreaterThan = InfoListUtil.filterByMoreDatePurchase(countGreaterThan, DateDiff.parse(new SimpleDateFormat("dd.MM.yyyy"), "31.12.2024"));
        //countGreaterThan = InfoListUtil.uniqValuesByPhoneNumber(countGreaterThan);

        List<Info> secondBase = InfoLoader.loadInfos(new File("input/prev.csv"), List.of(CsvField.PHONE)); //база с номерами которые надо убрать

        List<Info> diff = InfoListUtil.firstMinusSecond(countGreaterThan, secondBase); //убираю из первой базы вторую по номерам
        //   diff = InfoListUtil.uniqValuesByPhoneNumber(diff);
        //List<Info> intersectionInfo = InfoListUtil.intersectionInfo(mainBase, secondBase);

        //List<Info> countGreaterThan = InfoListUtil.filterByCountGreaterOrEqualThan(mainBase, 2);
        //List<Info> uniq = InfoListUtil.uniqValuesByPhoneNumber(countGreaterThan);
        //List<Info> secondBase = InfoLoader.loadInfos(new File("input/prev.csv"), List.of(CsvField.PHONE));


        //List<Info> diff = InfoListUtil.firstMinusSecond(uniq, secondBase);


        //    List<Info> more = InfoListUtil.filterByMoreDatePurchase(countGreaterThan, DateDiff.parse(new SimpleDateFormat("dd.MM.yyyy"), "01.03.2025"));
        //  List<Info> uniqNumber = InfoListUtil.uniqValuesByPhoneNumber(more);

        //     List<Info> finalList = InfoListUtil.firstMinusSecond(uniqNumber, secondBase);

        // List<Info> latest = InfoListUtil.onlyLatest(countGreaterThan);//создаю список, в котором самые последние записи

        //сохраняю в файл список, определенные поля с нужным разделителем для csv формата (можно поменять на \t
        // чтобы удобнее было копировать и вставлять в гугл таблицы)
        //InfoSaver.saveInfos("result.csv", diff, List.of(CsvField.PHONE), "\t");
        // InfoSaver.saveInfos("result.csv", mainBase, List.of(CsvField.PHONE), "\t");
        InfoSaver.saveInfos("result.csv", diff, List.of(CsvField.ID, CsvField.EMAIL, CsvField.PHONE), "\t");
    }
}