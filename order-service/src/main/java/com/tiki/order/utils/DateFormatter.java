package com.tiki.order.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatter {
    private static final String DatePattern = "yyyy-MM-dd HH:mm:ss" ;

    public static String convertLocalDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DatePattern);
        String formattedDate = localDateTime.format(formatter);
        return formattedDate;
    }
}
