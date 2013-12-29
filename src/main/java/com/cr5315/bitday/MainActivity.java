package com.cr5315.bitday;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends Activity {
    private SharedPreferences.Editor editor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        toggleButton.setChecked(preferences.getBoolean("isRunning", false));
        toggleButton.setOnCheckedChangeListener(new ToggleCheckedChangeListener());

        ImageView background = (ImageView) findViewById(R.id.background);
        background.setImageResource(BitDayDrawable.get(this));

        TextView textView = (TextView) findViewById(R.id.textView);
        String text = "BitDay Beta";
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            text += " v" + pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("BitDay", "Unable to get version name");
        }
        textView.setText(text);

    }

    private class ToggleCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                startAlarms();
                editor.putBoolean("isRunning", true);
            } else {
                stopAlarms();
                editor.putBoolean("isRunning", false);
            }
            editor.commit();
        }
    }

    private void startAlarms() {
        // 3600000 hour in millis

        Intent receiverIntent = new Intent(this, BitDayReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(this, 5315, receiverIntent, 0);

        Calendar c = Calendar.getInstance();

        c.add(Calendar.HOUR_OF_DAY, 1);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        String tag = "BitDay";
        Log.i(tag, c.getTime().toString());

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC, c.getTimeInMillis(), 1000 * 60 * 60, sender);
        Log.i(tag, "Hourly alarm started");

        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        try {
            wallpaperManager.setResource(BitDayDrawable.get(this));
        } catch (IOException e) {
            Log.e("BitDay", "Error setting wallpaper");
        }
    }

    private void stopAlarms() {
        Intent receiverIntent = new Intent(this, BitDayReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(this, 5315, receiverIntent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}