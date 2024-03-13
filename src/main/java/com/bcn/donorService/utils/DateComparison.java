package com.bcn.donorService.utils;

import java.time.LocalDate;
//import java.util.Date;
import java.sql.Date;

public class DateComparison {
    public static boolean isGapGreaterThanOrEqualToMonths(String dateStr1, String dateStr2, int monthGap) {

        LocalDate date1 = LocalDate.parse(dateStr1);
        LocalDate date2 = LocalDate.parse(dateStr2);

        System.out.println("date1 - " + date1);
        System.out.println("date2 - " + date2);

        Date sqlDate1 = Date.valueOf(date1);
        Date sqlDate2 = Date.valueOf(date2);

        sqlDate1 = Date.valueOf(sqlDate1.toLocalDate());
        sqlDate2 = Date.valueOf(sqlDate2.toLocalDate());

        long monthsBetween = calculateMonthDifference(sqlDate1.toLocalDate(), sqlDate2.toLocalDate());

        return monthsBetween >= monthGap;
    }

    public static long calculateMonthDifference(LocalDate start, LocalDate end) {
        int monthsInAYear = 12;
        int startYear = start.getYear();
        int startMonth = start.getMonthValue();
        int endYear = end.getYear();
        int endMonth = end.getMonthValue();

        return (endYear - startYear) * monthsInAYear + (endMonth - startMonth);
    }
}


