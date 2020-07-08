package com.example.demoweatherapp.utils;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.graphics.ColorUtils;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.demoweatherapp.R;
import com.example.demoweatherapp.data.remote.api.model.Daily;
import com.example.demoweatherapp.data.remote.api.model.Weather;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static java.util.Calendar.DAY_OF_WEEK;

public class BindingsAdapter {

    @BindingAdapter(value = "android:icon")
    public static void setWeatherIcon(AppCompatImageView imageView, List<Weather> weather) {
        String icon = weather.get(0).getIcon();
        String imageUrl = String.format(imageView.getRootView().getContext().getString(R.string.imageUrl), icon);
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .into(imageView);
    }

    @BindingAdapter(value = "android:dayOfWeek")
    public static void setDayOfWeek(AppCompatTextView textView, Daily day) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(day.getDt() * 1000L);
        textView.setText(Constants.DAYS_OF_WEEK[calendar.get(DAY_OF_WEEK) - 1]);
    }

    @BindingAdapter(value = "android:maxTemperature")
    public static void setMaxTemperatureOfDay(AppCompatTextView textView, Daily day) {

        if (day.getTemp().getMax() < 0 && day.getTemp().getMax() > -0.5) {
            day.getTemp().setMax(0.0);
        }

        textView.setText(String.format(Locale.getDefault(), "%.0f°", day.getTemp().getMax()));

    }

    @BindingAdapter(value = "android:minTemperature")
    public static void setMinTemperatureOfDay(AppCompatTextView textView, Daily day) {
        if (day.getTemp().getMin() < 0 && day.getTemp().getMin() > -0.5) {
            day.getTemp().setMin(0.0);
        }
        textView.setText(String.format(Locale.getDefault(), "%.0f°", day.getTemp().getMin()));

    }

    @BindingAdapter(value = "android:temperature")
    public static void setCurrentTemperature(AppCompatTextView textView, Daily day) {
        textView.setText(String.format(Locale.getDefault(), "%.0f°", day.getTemp().getDay()));
    }


}
