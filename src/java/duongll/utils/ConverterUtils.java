/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duongll.utils;

import java.util.Calendar;

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
}
