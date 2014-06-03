package org.pi2.core.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * User: ikka
 * Date: 12/31/13
 * Time: 4:30 AM
 */
public class DateUtils {
  private static Map<String, Integer> monthsMap = new HashMap<>();
  static {
    monthsMap.put("ЯНВ", Calendar.JANUARY);
    monthsMap.put("JAN", Calendar.JANUARY);
    monthsMap.put("ФЕВ", Calendar.FEBRUARY);
    monthsMap.put("FEB", Calendar.FEBRUARY);
    monthsMap.put("FEV", Calendar.FEBRUARY);
  }
  public static Date getDateWithoutTime(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTime();
  }

  public static Date getTomorrowDate(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.DATE, 1);
    return getDateWithoutTime(cal.getTime());
  }

  public static int getCalendarMonth(String monthName) {
    return monthsMap.get(monthName.toUpperCase());
  }
}
