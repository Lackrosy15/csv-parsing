package org.example.util;

import org.example.loading.Info;

import java.util.*;
import java.util.stream.Collectors;

public class InfoListUtil {

    public static List<Info> firstMinusSecond(List<Info> infos1, List<Info> infos2) {
        Set<String> phoneSet = infos2.stream()
                .map(Info::getPhone)
                .collect(Collectors.toSet());

        return infos1.stream()
                .filter(info -> !phoneSet.contains(info.getPhone()))
                .toList();
    }

    public static List<Info> filterByCountGreaterOrEqualThan(List<Info> infos, int count) {
        Map<String, List<Date>> map = buildDatePurchaseMap(infos);

        return infos.stream()
                .filter(info -> map.get(info.getPhone()).size() >= count)
                .toList();
    }

    public static List<Info> onlyLatest(List<Info> infos) {
        Map<String, List<Date>> map = buildDatePurchaseMap(infos);

        Map<String, Date> phoneLastDateMap = new HashMap<>();
        for (String key : map.keySet()) {
            Date latestDate = map.get(key).stream().max(Date::compareTo).get();
            phoneLastDateMap.put(key, latestDate);
        }

        return infos.stream()
                .filter(info -> phoneLastDateMap.get(info.getPhone()).equals(info.getDate()))
                .toList();
    }

    public static List<Info> uniqValuesByPhoneNumber(List<Info> infos) {
        var map = new HashMap<String, Info>();
        infos.forEach(info -> map.put(info.getPhone(), info));
        return new ArrayList<>(map.values());
    }


    private static Map<String, List<Date>> buildDatePurchaseMap(Collection<Info> infoCollection) {
        var map = infoCollection.stream()
                .collect(Collectors.groupingBy(Info::getPhone, Collectors.mapping(Info::getDate, Collectors.toList())));
        return map;
    }
}
