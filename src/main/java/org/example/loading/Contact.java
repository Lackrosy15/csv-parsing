package org.example.loading;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class Contact {
    private String phone;
    private List<Date> date;
    private String sumBudgets;
}
