package com.example.demoweatherapp.ui.base;

public interface BaseNavigator {

    void handleNetworkError(Throwable throwable);

    void showProgress();

    void hideProgress();
}
