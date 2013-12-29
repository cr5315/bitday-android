package com.cr5315.bitday;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Calendar;

public class BitDayDrawable {
    private static SharedPreferences preferences;

    public static String MORNING = "morning";
    public static String LATE_MORNING = "late_morning";
    public static String AFTERNOON = "afternoon";
    public static String LATE_AFTERNOON = "late_afternoon";
    public static String EVENING = "evening";
    public static String LATE_EVENING = "late_evening";
    public static String NIGHT = "night";
    public static String LATE_NIGHT = "late_night";

    private static int morningHour, lateMorningHour, afternoonHour, lateAfternoonHour,  eveningHour,
            lateEveningHour, nightHour, lateNightHour;

    public static int get(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        String tag = "BitDay";

        getPreferences();

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        int currentHour = c.get(Calendar.HOUR_OF_DAY);

        while (currentHour != morningHour &&
                currentHour != lateMorningHour &&
                currentHour != afternoonHour &&
                currentHour != lateAfternoonHour &&
                currentHour != eveningHour &&
                currentHour != lateEveningHour &&
                currentHour != nightHour &&
                currentHour != lateNightHour) {
            Log.i(tag, "currentHour = " + currentHour);
            if (currentHour == 0) {
                // it's 12am, set it to 11pm
                currentHour = 23;
            } else {
                currentHour--;
            }
        }

        if (currentHour == morningHour) {
            Log.i(tag, "Morning");
            return R.drawable.morning;
        } else if (currentHour == lateMorningHour) {
            Log.i(tag, "Late Morning");
            return R.drawable.late_morning;
        } else if (currentHour == afternoonHour) {
            Log.i(tag, "Afternoon");
            return R.drawable.afternoon;
        } else if (currentHour == lateAfternoonHour) {
            Log.i(tag, "Late Afternoon");
            return R.drawable.late_afternoon;
        } else if (currentHour == eveningHour) {
            Log.i(tag, "Evening");
            return R.drawable.evening;
        } else if (currentHour == lateEveningHour) {
            Log.i(tag, "Late Evening");
            return R.drawable.late_evening;
        } else if (currentHour == nightHour) {
            Log.i(tag, "Night");
            return R.drawable.night;
        } else if (currentHour == lateNightHour) {
            Log.i(tag, "Late Night");
            return R.drawable.late_night;
        } else {
            Log.e(tag, "No Time Match");
            return R.drawable.splash;
        }

    }

    private static void getPreferences() {
        morningHour = preferences.getInt(MORNING, 5);
        lateMorningHour = preferences.getInt(LATE_MORNING, 8);
        afternoonHour = preferences.getInt(AFTERNOON, 11);
        lateAfternoonHour = preferences.getInt(LATE_AFTERNOON, 14);
        eveningHour = preferences.getInt(EVENING, 17);
        lateEveningHour = preferences.getInt(LATE_EVENING, 20);
        nightHour = preferences.getInt(NIGHT, 23);
        lateNightHour = preferences.getInt(LATE_NIGHT, 2);
    }
}