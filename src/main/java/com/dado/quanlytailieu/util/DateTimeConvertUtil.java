package com.dado.quanlytailieu.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeConvertUtil {
    public static String convertLocalDateTimeToUniqueNumber(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        String formattedDateTime = dateTime.format(formatter);

        long numericDateTime = Long.parseLong(formattedDateTime);

        return String.valueOf(numericDateTime);
    }
}
