package com.example.demoweatherapp.ui.base;

import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.example.demoweatherapp.ui.base.BaseActivity;


public abstract class BaseMVVMActivity<T extends ViewDataBinding, V extends BaseViewModel> extends BaseActivity {

    private T mViewDataBinding;
    private V mViewModel;

    @Override
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performDependencyInjection();
        performDataBinding();
        mViewModel.onViewCreated();
    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
    }

    public abstract void performDependencyInjection();

    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    @Override
    protected void onDestroy() {
        mViewModel.onDestroyView();
        super.onDestroy();
    }

    public abstract V getViewModel();

    public abstract int getBindingVariable();

    public abstract @LayoutRes
    int getLayoutId();
}
