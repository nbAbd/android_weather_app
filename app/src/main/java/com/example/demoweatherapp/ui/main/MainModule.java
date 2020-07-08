package com.example.demoweatherapp.ui.main;

import com.example.demoweatherapp.data.remote.api.OpenWeatherMapApi;
import com.example.demoweatherapp.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    @Provides
    MainViewModel provideMainViewModel(SchedulerProvider schedulerProvider, OpenWeatherMapApi api) {
        return new MainViewModel(schedulerProvider, api);
    }
}
