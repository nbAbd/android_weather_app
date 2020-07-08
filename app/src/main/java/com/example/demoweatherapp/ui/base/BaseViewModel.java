package com.example.demoweatherapp.ui.base;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.ViewModel;

import com.example.demoweatherapp.utils.rx.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel<N extends BaseNavigator> extends ViewModel {
    private N mNavigator;

    private final SchedulerProvider mSchedulerProvider;

    public BaseViewModel(SchedulerProvider schedulerProvider) {
        this.mSchedulerProvider = schedulerProvider;
    }

    private CompositeDisposable mCompositeDisposable;

    public void setNavigator(N navigator) {
        this.mNavigator = navigator;
    }

    public N getNavigator() {
        return mNavigator;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

    public void onViewCreated() {
        this.mCompositeDisposable = new CompositeDisposable();
    }

    public void onDestroyView() {
        mCompositeDisposable.dispose();
    }

    @VisibleForTesting
    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        this.mCompositeDisposable = compositeDisposable;
    }
}
