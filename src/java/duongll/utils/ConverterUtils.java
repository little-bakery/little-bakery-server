/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duongll.utils;

import java.util.Calendar;
import java.util.Random;

/**
 *
 * @author duong
 */
public class ConverterUtils {

    public static String convertQuantity(String unit) {
        if (unit.isEmpty() || unit.equals("")) {
            return "";
        }
        return unit.replaceAll("Â", "");
    }

    public static String convertTime(String prepareTime, String cookTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        int indexOfHour, hour, indexOfMinute, min = 0;
        if (!prepareTime.equals("No Time")) {
            indexOfHour = prepareTime.indexOf("H");
            hour = Integer.parseInt(prepareTime.substring(0, indexOfHour));
            indexOfMinute = prepareTime.indexOf("M");
            min = Integer.parseInt(prepareTime.substring((indexOfHour + 1), indexOfMinute));
            calendar.add(Calendar.HOUR, hour);
            calendar.add(Calendar.MINUTE, min);
        }
        if (!cookTime.equals("No Time")) {
            indexOfHour = cookTime.indexOf("H");
            hour = Integer.parseInt(cookTime.substring(0, indexOfHour));
            indexOfMinute = cookTime.indexOf("M");
            min = Integer.parseInt(cookTime.substring((indexOfHour + 1), indexOfMinute));
            calendar.add(Calendar.HOUR, hour);
            calendar.add(Calendar.MINUTE, min);
        }
        return calendar.get(Calendar.HOUR) + "H" + calendar.get(Calendar.MINUTE) + "M";
    }

    public static Long randomViews() {
        return Long.parseLong(new Random().nextInt(1000000) + "");
    }

    public static int convertTimeToMinute(String times) {
        int indexOfHour, hour, indexOfMinute, min = 0, indexOfDay, day;
        if (times.contains("D")) {
            indexOfDay = times.indexOf("D");
            day = Integer.parseInt(times.substring(0, indexOfDay));
            min += day * 1440;
            indexOfHour = times.indexOf("H");
            hour = Integer.parseInt(times.substring((indexOfDay + 1), indexOfHour));
            indexOfMinute = times.indexOf("M");
            min += Integer.parseInt(times.substring((indexOfHour + 1), indexOfMinute));
            min += (hour * 60);
        } else {
            if (times.contains("H")) {
                indexOfHour = times.indexOf("H");
                hour = Integer.parseInt(times.substring(0, indexOfHour));
                indexOfMinute = times.indexOf("M");
                min = Integer.parseInt(times.substring((indexOfHour + 1), indexOfMinute));
                min += (hour * 60);
            } else {
                indexOfMinute = times.indexOf("M");
                min = Integer.parseInt(times.substring(0, indexOfMinute));
            }
        }
        return min;
    }
}
