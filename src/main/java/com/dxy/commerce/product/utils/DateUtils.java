package com.dxy.commerce.product.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * 时间工具类
 * @author dingxy
 * @date 2021/2/28 12:44 上午 
 * @return 
 */
public class DateUtils {

    public static final DateTimeFormatter DATE_TIME_SECOND_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter DATE_FORMATTER_ = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    public static final DateTimeFormatter DATE_FORMATTER_NO_LINE = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static LocalDateTime getLocalDateTime(String str) {

        LocalDate firstDay = LocalDate.parse(str, DATE_FORMATTER_);

        return LocalDateTime.of(firstDay, LocalTime.of(0, 0, 1));
    }

    public static String getFirstDateOfMonth(String monthStr) {

        // 指定月份的第一天
        LocalDate firstDay =
            LocalDate.parse(monthStr + "-01", DATE_FORMATTER).with(TemporalAdjusters.firstDayOfMonth());

        return LocalDateTime.of(firstDay, LocalTime.MIN).format(DATE_TIME_SECOND_FORMATTER);
    }

    public static LocalDateTime getFirstDateTimeOfMonth(String monthStr) {

        // 指定月份的第一天
        LocalDate firstDay =
            LocalDate.parse(monthStr + "-01", DATE_FORMATTER).with(TemporalAdjusters.firstDayOfMonth());

        return LocalDateTime.of(firstDay, LocalTime.MIN);
    }

    public static String getLastDateOfMonth(String monthStr) {

        // 指定月份的最后一天
        LocalDate lastDay = LocalDate.parse(monthStr + "-01", DATE_FORMATTER).with(TemporalAdjusters.lastDayOfMonth());

        return LocalDateTime.of(lastDay, LocalTime.MAX).format(DATE_TIME_SECOND_FORMATTER);
    }

    public static String getLastDateAfter12Month(String monthStr) {

        // 指定月份的最后一天
        LocalDate lastDay =
            LocalDate.parse(monthStr + "-01", DATE_FORMATTER).plusMonths(12).with(TemporalAdjusters.lastDayOfMonth());

        return LocalDateTime.of(lastDay, LocalTime.MAX).format(DATE_TIME_SECOND_FORMATTER);
    }

    public static LocalDateTime getLastDateTimeOfMonth(String monthStr) {

        // 指定月份的最后一天
        LocalDate lastDay = LocalDate.parse(monthStr + "-01", DATE_FORMATTER).with(TemporalAdjusters.lastDayOfMonth());

        return LocalDateTime.of(lastDay, LocalTime.MAX);
    }

    public static String getFirstTimeOfDay(String dayStr) {

        // 指定某天的开始时间
        LocalDate day = LocalDate.parse(dayStr, DATE_FORMATTER);

        return LocalDateTime.of(day, LocalTime.MIN).format(DATE_TIME_SECOND_FORMATTER);
    }

    public static String getEndTimeOfDay(String dayStr) {

        // 指定某天的结束时间
        LocalDate day = LocalDate.parse(dayStr, DATE_FORMATTER);

        return LocalDateTime.of(day, LocalTime.MAX).format(DATE_TIME_SECOND_FORMATTER);
    }

    public static List<String> getBetweenDate(LocalDateTime startDate, LocalDateTime endDate) {
        List<String> list = new ArrayList<>();

        long distance = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        if (distance < 1) {
            return list;
        }
        Stream.iterate(startDate, d -> {
            return d.plusDays(1);
        }).limit(distance).forEach(f -> {
            list.add(f.format(DATE_FORMATTER));
        });
        return list;
    }

    public static String getTodayDate() {
        return LocalDateTime.now().format(DATE_FORMATTER);
    }

    public static String getLastDayDate() {
        return LocalDateTime.now().minusDays(1).format(DATE_FORMATTER);
    }

    public static Long convertTimeToLong(String time) {

        DateTimeFormatter ftf = null;
        if (time.trim().length() == 10) {
            time = time + " 00:00:00";
        }
        ftf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        LocalDateTime parse = LocalDateTime.parse(time, ftf);
        return LocalDateTime.from(parse).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static String unixTimeStampToDate(Long times) {
        Instant timestamp = Instant.ofEpochMilli(times);
        ZonedDateTime losAngelesTime = timestamp.atZone(ZoneId.of("Asia/Shanghai"));

        return losAngelesTime.toLocalDateTime().format(DATE_FORMATTER_);
    }


    public static String getDateFormatterString(String date){

        String yyyy = date.substring(0,4);
        String mm = date.substring(4,6);
        String dd = date.substring(6,8);
        return yyyy + "/" + mm + "/" + dd;
    }

}
