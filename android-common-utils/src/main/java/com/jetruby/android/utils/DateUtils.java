package com.jetruby.android.utils;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;



public class DateUtils {

    public static CharSequence formatDate(Date postDate) {
        if (postDate == null)
            return null;

        final CharSequence dayOfTheWeek = DateFormat.format("EEEE", postDate);
        final CharSequence day = DateFormat.format("dd", postDate);
        final CharSequence monthString = DateFormat.format("MMM", postDate);
        final CharSequence time = DateFormat.format("hh:mm aa", postDate);

        //Monday, Jan 16, 8:00pm
        return new StringBuilder()
                .append(dayOfTheWeek)
                .append(", ")
                .append(monthString)
                .append(" ")
                .append(day)
                .append(", ")
                .append(time);
    }

    public static String dateToRegularString(Date date) {
        SimpleDateFormat sdfRegular = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return sdfRegular.format(date);
    }

    public static String dateToSimpleString(Date date) {
        return dateToSimpleString(date, Locale.getDefault());
    }

    public static String dateToSimpleString(Date date, Locale locale) {
        SimpleDateFormat sdfSimple = new SimpleDateFormat("MMM, yy", locale);
        return sdfSimple.format(date);
    }

    public static String dateToSimpleString(Date date, String datePattern) {
        return dateToSimpleString(date, datePattern, Locale.getDefault());
    }

    public static String dateToSimpleString(Date date, String datePattern, Locale locale) {
        SimpleDateFormat sdfSimple = new SimpleDateFormat(datePattern, locale);
        return sdfSimple.format(date);
    }

    public static String dateToMonthOnly(Date date) {
        return dateToMonthOnly(date, Locale.getDefault());
    }

    public static String dateToMonthOnly(Date date, Locale locale) {
        SimpleDateFormat sdfMonth = new SimpleDateFormat("MMM", locale);
        return sdfMonth.format(date);
    }

    public static String getDayFromDate(Date unformattedDate) {
        String day = (String) DateFormat.format("dd", unformattedDate);
        return day;
    }

    public static String getMonthFromDate(Date unformattedDate) {
        String month = (String) DateFormat.format("MMM", unformattedDate);
        return month;
    }

    public static int daysBetween(Calendar firstDay, Calendar lastDay){
        Calendar dayOne = (Calendar) firstDay.clone();
        Calendar dayTwo = (Calendar) lastDay.clone();

        if (dayOne.get(Calendar.YEAR) == dayTwo.get(Calendar.YEAR)) {
            return Math.abs(dayOne.get(Calendar.DAY_OF_YEAR) - dayTwo.get(Calendar.DAY_OF_YEAR));
        } else {
            if (dayTwo.get(Calendar.YEAR) > dayOne.get(Calendar.YEAR)) {
                //swap them
                Calendar temp = dayOne;
                dayOne = dayTwo;
                dayTwo = temp;
            }
            int extraDays = 0;

            int dayOneOriginalYearDays = dayOne.get(Calendar.DAY_OF_YEAR);

            while (dayOne.get(Calendar.YEAR) > dayTwo.get(Calendar.YEAR)) {
                dayOne.add(Calendar.YEAR, -1);
                // getActualMaximum() important for leap years
                extraDays += dayOne.getActualMaximum(Calendar.DAY_OF_YEAR);
            }

            return extraDays - dayTwo.get(Calendar.DAY_OF_YEAR) + dayOneOriginalYearDays ;
        }
    }
}
