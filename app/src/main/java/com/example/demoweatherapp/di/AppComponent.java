package com.example.demoweatherapp.di;

import com.example.demoweatherapp.di.module.ApiModule;
import com.example.demoweatherapp.di.module.AppModule;
import com.example.demoweatherapp.di.module.NetworkModule;
import com.example.demoweatherapp.ui.adapter.AdaptersModule;
import com.example.demoweatherapp.ui.main.MainActivity;
import com.example.demoweatherapp.ui.main.MainModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApiModule.class,
        AppModule.class,
        NetworkModule.class,
        MainModule.class,
        AdaptersModule.class
})

public interface AppComponent {

    void inject(MainActivity activity);

}
