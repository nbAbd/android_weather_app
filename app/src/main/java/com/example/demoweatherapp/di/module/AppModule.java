package com.example.demoweatherapp.di.module;

import android.app.Application;
import android.content.Context;

import com.example.demoweatherapp.utils.rx.AppSchedulerProvider;
import com.example.demoweatherapp.utils.rx.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final Application mApplication; // it must be initialized


    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mApplication;
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

}
