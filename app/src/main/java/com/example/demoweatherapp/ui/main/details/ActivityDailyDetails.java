package com.example.demoweatherapp.ui.main.details;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.airbnb.lottie.LottieAnimationView;
import com.example.demoweatherapp.R;
import com.example.demoweatherapp.data.remote.api.model.Daily;
import com.example.demoweatherapp.ui.base.BaseActivity;
import com.example.demoweatherapp.utils.AppUtil;
import com.example.demoweatherapp.utils.BindingsAdapter;
import com.google.android.material.card.MaterialCardView;

import java.util.Locale;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class ActivityDailyDetails extends BaseActivity {

    public static Intent getStartIntent(Context context, Daily day, int cardColor) {
        Intent intent = new Intent(context, ActivityDailyDetails.class);
        intent.putExtra("day", day);
        intent.putExtra("cardColor", cardColor);
        return intent;
    }

    MaterialCardView mCardViewMain, mCardViewDetails;
    AppCompatTextView mDayNameTextView;
    AppCompatTextView mTempTextView;
    AppCompatTextView minTempTextView;
    AppCompatTextView maxTempTextView;
    LottieAnimationView mAnimationView;
    AppCompatTextView mSunriseTextView, mSunsetTextView, mHumidityTextView;
    AppCompatTextView mCloudinessTextView, mWindTextView, mPressureTextView;
    Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_details);

        initViews();
        setVariables();
        initToolbar(toolbar, "Details");
        setHomeAsUp(true);
    }


    private void initViews() {
        mCardViewMain = findViewById(R.id.card_view_main);
        mCardViewDetails = findViewById(R.id.card_view_details);
        mDayNameTextView = findViewById(R.id.day_name_text_view);
        minTempTextView = findViewById(R.id.min_temp_text_view);
        maxTempTextView = findViewById(R.id.max_temp_text_view);
        mAnimationView = findViewById(R.id.animation_view);
        mTempTextView = findViewById(R.id.temp_text_view);

        // detailed views
        mWindTextView = findViewById(R.id.wind_text_view);
        mHumidityTextView = findViewById(R.id.humidity_text_view);
        mCloudinessTextView = findViewById(R.id.cloudiness_text_view);
        mPressureTextView = findViewById(R.id.pressure_text_view);
        mSunriseTextView = findViewById(R.id.sunrise_textview);
        mSunsetTextView = findViewById(R.id.sunset_textview);
        toolbar = findViewById(R.id.toolbar);
    }

    @SuppressLint("StringFormatInvalid")
    private void setVariables() {
        Intent intent = getIntent();


        Daily mDay = (Daily) intent.getSerializableExtra("day");
        int cardColor = intent.getIntExtra("cardColor", 0);
        mCardViewMain.setCardBackgroundColor(cardColor);
        mCardViewDetails.setCardBackgroundColor(AppUtil.getRandomMaterialColor(getResources()));

        if (mDay != null) {
            BindingsAdapter.setDayOfWeek(mDayNameTextView, mDay);
            BindingsAdapter.setMaxTemperatureOfDay(maxTempTextView, mDay);
            BindingsAdapter.setMinTemperatureOfDay(minTempTextView, mDay);
            BindingsAdapter.setCurrentTemperature(mTempTextView, mDay);

            mAnimationView.setAnimation(AppUtil.getWeatherAnimation(mDay.getWeather().get(0).getId()));
            mAnimationView.playAnimation();

            mWindTextView.setText(String.format(Locale.getDefault(), getString(R.string.wind_unit_label), mDay.getWindSpeed()));


            mSunsetTextView.setText(AppUtil.getTime(mDay.getSunset(), this));

            mSunriseTextView.setText(AppUtil.getTime(mDay.getSunrise(), this));

            mPressureTextView.setText(String.format(Locale.getDefault(), getString(R.string.pressure_unit_label), mDay.getPressure()));

            String humidity = mDay.getHumidity().toString() + " %";
            mHumidityTextView.setText(humidity);

            String cloudiness = mDay.getClouds().toString() + " %";
            mCloudinessTextView.setText(cloudiness);

            toolbar.findViewById(R.id.toolbar_title).setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        }


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
}
