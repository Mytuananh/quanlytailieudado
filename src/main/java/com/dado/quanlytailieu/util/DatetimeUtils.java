package com.dado.quanlytailieu.util;

import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class DatetimeUtils {

    public static List<LocalDate> getDates(LocalDate from, LocalDate to) {
        List<LocalDate> dates = new ArrayList<>();
        var date = to;
        while (!date.isBefore(from)) {
            dates.add(date);
            date = date.minusDays(1);
        }
        return dates;
    }

    public static boolean isWeekend(LocalDate date) {
        var d = date.getDayOfWeek();
        return d == DayOfWeek.SATURDAY || d == DayOfWeek.SUNDAY;
    }

    public static LocalDate min(@Nullable LocalDate a, @Nullable LocalDate b) {
        if (a == null) {
            return b;
        }
        if (b == null || a.isBefore(b)) {
            return a;
        }
        return b;
    }
}
