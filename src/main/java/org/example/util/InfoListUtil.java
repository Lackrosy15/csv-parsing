package org.example.util;

import org.example.loading.Contact;
import org.example.loading.Info;

import java.util.*;
import java.util.stream.Collectors;

public class InfoListUtil {

    public static List<Info> intersectionInfo(List<Info> infos1, List<Info> infos2) {
        Set<String> phoneSet = infos2.stream()
                .map(Info::getPhone)
                .collect(Collectors.toSet());

        return infos1.stream()
                .filter(info -> phoneSet.contains(info.getPhone()))
                .toList();
    }

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

    public static List<Info> filterBySumBudget(List<Info> infos, int sum) {
        Map<String, List<String>> budgetMap = buildBudgetMap(infos);

        return infos.stream()
                .filter(info -> {
                    List<String> budgetStrings = budgetMap.get(info.getPhone());
                    if (budgetStrings == null || budgetStrings.isEmpty()) {
                        return false;
                    }

                    int total = budgetStrings.stream()
                            .mapToInt(s -> {
                                try {
                                    return Integer.parseInt(s.trim());
                                } catch (NumberFormatException e) {
                                    // Игнорируем некорректные значения или считаем как 0 — зависит от требований
                                    return 0;
                                }
                            })
                            .sum();

                    return total >= sum;
                })
                .toList();
    }

    public static List<Info> filterByMoreDatePurchase(List<Info> infos, Date date) {
        Map<String, List<Date>> map = buildDatePurchaseMap(infos);

        return infos.stream()
                .filter(info -> {
                    List<Date> dates = map.get(info.getPhone());
                    return dates != null &&
                            dates.stream()
                                    .filter(Objects::nonNull)
                                    .anyMatch(d -> d.after(date));
                })
                .collect(Collectors.toList());
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

    public static List<Contact> iniqValuesByPhoneNumber(List<Contact> contacts) {
        var map = new HashMap<String, Contact>();
        contacts.forEach(contact -> map.put(contact.getPhone(), contact));
        return new ArrayList<>(map.values());
    }

    public static List<Contact> summarizeBudget(List<Info> infos) {
        if (infos == null || infos.isEmpty()) {
            return List.of();
        }

        Map<String, List<Date>> dateMap = buildDatePurchaseMap(infos);
        Map<String, List<String>> budgetMap = buildBudgetMap(infos);

        return dateMap.entrySet().stream()
                .map(entry -> {
                    String phone = entry.getKey();
                    List<Date> dates = entry.getValue();
                    List<String> budgets = budgetMap.getOrDefault(phone, List.of());

                    // Предполагается, что бюджет представлен в виде строк, которые можно преобразовать в числа.
                    // Для упрощения считаем, что все строки корректны и содержат числа (например, "100.50").
                    double totalBudget = budgets.stream()
                            .mapToDouble(Double::parseDouble)
                            .sum();

                    return new Contact(phone, dates, String.valueOf(totalBudget));
                })
                .collect(Collectors.toList());
    }


    private static Map<String, List<Date>> buildDatePurchaseMap(Collection<Info> infoCollection) {
        var map = infoCollection.stream()
                .collect(Collectors.groupingBy(Info::getPhone, Collectors.mapping(Info::getDate, Collectors.toList())));
        return map;
    }

    private static Map<String, List<String>> buildBudgetMap(Collection<Info> infoCollection) {
        var map = infoCollection.stream()
                .collect(Collectors.groupingBy(Info::getPhone, Collectors.mapping(Info::getBudget, Collectors.toList())));
        return map;
    }
}
