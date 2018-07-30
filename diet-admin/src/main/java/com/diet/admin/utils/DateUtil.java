package com.diet.admin.utils;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * Created by LiuYu
 */
public class DateUtil {

    /**
     * 字符串转日期
     * @param pattern
     * @param strDate
     * @return
     */
    public static LocalDate formatDate(String pattern, String strDate){
        DateTimeFormatter strToDateFormatter = DateTimeFormatter.ofPattern(pattern);
        TemporalAccessor dateTemporal = strToDateFormatter.parse(strDate);
        return LocalDate.from(dateTemporal);
    }

    /**
     * 日期转字符串
     * @param pattern
     * @param date
     * @return
     */
    public static String strToDate(String pattern, LocalDate date){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
        return dateFormatter.format(date);
    }
}
