package com.example.demoweatherapp.ui.main;

import androidx.databinding.ObservableField;

import com.example.demoweatherapp.data.remote.api.OpenWeatherMapApi;
import com.example.demoweatherapp.data.remote.api.model.TotalWeatherInfo;
import com.example.demoweatherapp.ui.base.BaseViewModel;
import com.example.demoweatherapp.utils.Constants;
import com.example.demoweatherapp.utils.rx.SchedulerProvider;
import com.google.gson.Gson;

import timber.log.Timber;

public class MainViewModel extends BaseViewModel<MainNavigator> {

    private final OpenWeatherMapApi mApi;

    ObservableField<TotalWeatherInfo> weatherInfo = new ObservableField<>();

    MainViewModel(SchedulerProvider schedulerProvider, OpenWeatherMapApi api) {
        super(schedulerProvider);
        this.mApi = api;
    }

    void fetchWeather(double lat, double lon, String apiKey) {
        getCompositeDisposable().add(
                mApi.getWeather(lat, lon, Constants.UNITS, apiKey, "ru")
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(totalWeatherInfo -> {
                            weatherInfo.set(totalWeatherInfo);
                            Timber.e(new Gson().toJson(totalWeatherInfo.getDaily()));
                            getNavigator().hideEmptyLayout();
                            getNavigator().populateCurrentWeather(totalWeatherInfo.getCurrent());
                        }, throwable -> {
                            throwable.printStackTrace();
                            getNavigator().showEmptyLayout();
                        })
        );
    }


}
