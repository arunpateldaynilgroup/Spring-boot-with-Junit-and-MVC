package com.arun.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static final String DEFAULT_DATE_TIME_FORMATE = "dd-MM-yyyy hh:mm:ss a";

    public static String getDefaultDateAndTimeFormat(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(DEFAULT_DATE_TIME_FORMATE).format(date);
    }

}
