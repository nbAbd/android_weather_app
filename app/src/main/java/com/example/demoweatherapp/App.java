package com.example.demoweatherapp;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.example.demoweatherapp.di.AppComponent;
import com.example.demoweatherapp.di.DaggerAppComponent;
import com.example.demoweatherapp.di.module.AppModule;
import com.example.demoweatherapp.di.module.NetworkModule;
import com.example.demoweatherapp.utils.Constants;

import timber.log.Timber;

@SuppressLint("Registered")
public class App extends Application {

    private AppComponent mComponent;

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mComponent = prepareAppComponent();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private AppComponent prepareAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule(Constants.BASE_URL))
                .build();
    }

    public AppComponent getComponent() {
        return mComponent;
    }

}
