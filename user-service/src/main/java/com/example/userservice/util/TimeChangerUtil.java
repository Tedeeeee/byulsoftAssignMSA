package com.example.userservice.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeChangerUtil {
    public static String timeChange(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return time.format(formatter);
    }
}
