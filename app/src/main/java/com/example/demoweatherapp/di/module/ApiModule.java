package com.example.demoweatherapp.di.module;

import com.example.demoweatherapp.data.remote.api.OpenWeatherMapApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ApiModule {

    @Provides
    OpenWeatherMapApi provideOpenWeatherMapApi(Retrofit retrofit) {
        return retrofit.create(OpenWeatherMapApi.class);
    }


}
