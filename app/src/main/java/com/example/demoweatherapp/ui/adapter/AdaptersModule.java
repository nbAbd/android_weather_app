package com.example.demoweatherapp.ui.adapter;

import dagger.Module;
import dagger.Provides;

@Module
public class AdaptersModule {

    @Provides
    DailyWeatherAdapter provideDailyWeatherAdapter() {
        return new DailyWeatherAdapter();
    }
}
