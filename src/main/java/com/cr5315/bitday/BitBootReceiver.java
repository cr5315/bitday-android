package com.cr5315.bitday;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;
import java.util.Calendar;

public class BitBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String tag = "BitDay";

        if (preferences.getBoolean("isRunning", false)) {
            Intent receiverIntent = new Intent(context, BitDayReceiver.class);
            PendingIntent sender = PendingIntent.getBroadcast(context, 5315, receiverIntent, 0);

            Calendar c = Calendar.getInstance();

            c.add(Calendar.HOUR_OF_DAY, 1);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);

            Log.i(tag, c.getTime().toString());

            AlarmManager alarmManager = (AlarmManager)
                    context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC, c.getTimeInMillis(),
                    1000 * 60 * 60, sender);
            Log.i(tag, "Hourly alarm started");

            WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
            try {
                wallpaperManager.setResource(BitDayDrawable.get(context));
            } catch (IOException e) {
                Log.e("BitDay", "Error setting wallpaper");
            }
        }
    }
}
