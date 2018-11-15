package com.em_projects.tweetings.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by eyalmuchtar on 01/10/2017.
 */

public class TimeUtils {

    /**
     * Format the given millis to date and time string
     *
     * @param currentTime the time to be formatted
     * @return date and time string
     */
    public static String timeToFormatedString(long currentTime) {
        String out = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss").format(new Date(currentTime));
        return out;
    }

    public static String getDateStr(long millis) {
        String out = new SimpleDateFormat("dd-MM-yyyy").format(new Date(millis));
        return out;
    }

    public static String getRegDateStr(long millis) {
        String out = new SimpleDateFormat("yyyy-MM-dd").format(new Date(millis));
        return out;
    }

    public static String getTimeStr(long millis) {
        String out = new SimpleDateFormat("hh:mm").format(new Date(millis));
        return out;
    }

    public static String getDateStr(Date date) {
        String out = new SimpleDateFormat("dd-MM-yyyy").format(date);
        return out;
    }

    public static String getRegDateStr(Date date) {
        String out = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return out;
    }

    public static String getTimeStr(Date date) {
        String out = new SimpleDateFormat("HH:mm").format(date);
        return out;
    }

    public static String imageFormatedTime(long currentTime) {
        String out = new SimpleDateFormat("yyyyMMdd_hhmmss").format(new Date(currentTime));
        return out;
    }

    public static Date parseToDate(String dateTime) throws ParseException {
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = sdf.parse(dateTime);
        return date;
    }
}
