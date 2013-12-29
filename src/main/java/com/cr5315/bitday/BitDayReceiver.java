package com.cr5315.bitday;

import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;

public class BitDayReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        int resource = BitDayDrawable.get(context);

        // Set the wallpaper
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        try {
            wallpaperManager.setResource(resource);
        } catch (IOException e) {
            Log.e("BitDay", "Error setting wallpaper");
        }
    }
}