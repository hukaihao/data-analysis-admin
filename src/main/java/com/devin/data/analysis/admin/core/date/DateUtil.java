package com.devin.data.analysis.admin.core.date;


import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 原生支持LocalDateTime
 */
public class DateUtil {
    public static final String DATA_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATA_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ss";

    public static final String HOUR_MINUTE_FORMAT = "HH:mm";


    public static final DateTimeFormatter DATA_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATA_TIME_FORMAT);
    public static final DateTimeFormatter DATA_FORMATTER = DateTimeFormatter.ofPattern(DATA_FORMAT);
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);
    public static final DateTimeFormatter HOUR_MINUTE_FORMATTER = DateTimeFormatter.ofPattern(HOUR_MINUTE_FORMAT);


    public static LocalDateTime stringToDate(String date,String pattern){
        return LocalDateTime.parse(date,DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDateTime stringToDate(String date){
        return LocalDateTime.parse(date,DATA_FORMATTER);
    }





}
