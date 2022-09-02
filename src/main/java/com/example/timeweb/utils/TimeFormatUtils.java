package com.example.timeweb.utils;

import java.util.Calendar;

/**
 * Get week information
 */
public class TimeFormatUtils {
    public static String getWeekDay(Calendar c) {
        if (c == null) {
            return "Mo";
        }

        if (Calendar.MONDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "Mo";
        }
        if (Calendar.TUESDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "Tu";
        }
        if (Calendar.WEDNESDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "We";
        }
        if (Calendar.THURSDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "Th";
        }
        if (Calendar.FRIDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "Fr";
        }
        if (Calendar.SATURDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "Sa";
        }
        if (Calendar.SUNDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "So";
        }

        return "Mo";
    }
}
