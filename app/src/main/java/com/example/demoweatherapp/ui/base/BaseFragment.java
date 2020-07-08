package com.example.demoweatherapp.ui.base;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {
    public Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setActionBarTitle(String title) {
        BaseActivity activity = (BaseActivity) getActivity();
        if (activity != null) {
            activity.setActionBarTitle(title);
        }
    }

    public void hideActionBar() {
        BaseActivity activity = (BaseActivity) getActivity();
        if (activity != null) {
            activity.hideActionBar();
        }
    }

    public void showActionBar() {
        BaseActivity activity = (BaseActivity) getActivity();
        if (activity != null) {
            activity.showActionBar();
        }
    }

    public void setHomeAsUp(boolean status) {
        BaseActivity activity = (BaseActivity) getActivity();
        if (activity != null) {
            activity.setHomeAsUp(status);
        }
    }


    // TOAST
    public void showToast(String message) {
        Activity activity = getActivity();
        if (activity != null && !activity.isFinishing()) {
            if (activity instanceof BaseActivity) {
                ((BaseActivity) activity).showToast(message);
            } else {
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void showToast(@StringRes int message) {
        Activity activity = getActivity();
        if (activity != null && !activity.isFinishing()) {
            if (activity instanceof BaseActivity) {
                ((BaseActivity) activity).showToast(message);
            } else {
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
