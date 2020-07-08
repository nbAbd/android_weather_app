package com.example.demoweatherapp.ui.main;

import com.example.demoweatherapp.data.remote.api.model.Current;
import com.example.demoweatherapp.ui.base.BaseNavigator;


interface MainNavigator extends BaseNavigator {

    void populateCurrentWeather(Current current);

    void showEmptyLayout();

    void hideEmptyLayout();
}
