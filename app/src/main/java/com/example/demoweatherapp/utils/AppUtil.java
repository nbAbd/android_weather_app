package com.example.demoweatherapp.utils;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.ConnectivityManager;

import com.example.demoweatherapp.R;

import java.util.Calendar;
import java.util.Locale;

public class AppUtil {

    /**
     * Get animation file according to weather status code
     *
     * @param weatherCode int weather status code
     * @return id of animation json file
     */
    public static int getWeatherAnimation(int weatherCode) {
        if (weatherCode / 100 == 2) {
            return R.raw.storm_weather;
        } else if (weatherCode / 100 == 3) {
            return R.raw.rainy_weather;
        } else if (weatherCode / 100 == 5) {
            return R.raw.rainy_weather;
        } else if (weatherCode / 100 == 6) {
            return R.raw.snow_weather;
        } else if (weatherCode / 100 == 7) {
            return R.raw.unknown;
        } else if (weatherCode == 800) {
            return R.raw.clear_day;
        } else if (weatherCode == 801) {
            return R.raw.few_clouds;
        } else if (weatherCode == 803) {
            return R.raw.broken_clouds;
        } else if (weatherCode / 100 == 8) {
            return R.raw.cloudy_weather;
        }
        return R.raw.unknown;
    }


    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public static int getRandomMaterialColor(Resources r) {
        TypedArray colors = r.obtainTypedArray(R.array.mdcolor_600);
        int randomIndex = (int) (Math.random() * colors.length());
        int returnColor = colors.getColor(randomIndex, Color.BLACK);
        colors.recycle();
        return returnColor;
    }

    public static String getTime(Integer timestamp, Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp * 1000L);

        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        String hourString;
        if (hour < 10) {
            hourString = String.format(Locale.getDefault(), context.getString(R.string.zero_label), hour);
        } else {
            hourString = String.format(Locale.getDefault(), "%d", hour);
        }
        String minuteString;
        if (minute < 10) {
            minuteString = String.format(Locale.getDefault(), context.getString(R.string.zero_label), minute);
        } else {
            minuteString = String.format(Locale.getDefault(), "%d", minute);
        }
        return hourString + ":" + minuteString;
    }

    public static boolean isNight(int sunset) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(sunset * 1000L);
        return calendar.get(Calendar.HOUR_OF_DAY) >= calendar.getTimeInMillis();
    }
}
