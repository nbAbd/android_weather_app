package com.example.demoweatherapp.ui.base;

import android.annotation.SuppressLint;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.demoweatherapp.R;

import java.util.Objects;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    private Toast mToast;
    private MaterialDialog mDialog;

    // Toast
    public void showToast(@StringRes int message) {
        showToast(getString(message));
    }

    public void showToast(String message) {
        if (mToast != null) {
            mToast = null;
        }
        mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        mToast.show();
    }

    // Progress
    public void showProgress(@Nullable String title, @Nullable String content) {
        hideDialog();
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .cancelable(true)
                .progress(true, 0);

        if (title != null) {
            builder.title(title);
        }
        if (content != null) {
            builder.content(content);
        }
        mDialog = builder.build();
        if (!this.isFinishing()) {
            mDialog.show();
        }
    }

    public void showProgress(@StringRes int title, @StringRes int content) {
        showProgress(getString(title), getString(content));
    }

    public void hideProgress() {
        hideDialog();
    }

    // ActionBar title
   /* In some cases we have to use activity toolbar on fragments that's why we
    should check whether actionbar exist or not
    */

    public void setActionBarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    public void setActionBarTitle(@StringRes int title) {
        setActionBarTitle(getString(title));
    }

    // ActionBar visibility
    public void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
        }
    }

    public void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    // Toolbar
    public void initToolbar(Toolbar toolbar, @Nullable String title) {
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        if (title != null) {
            AppCompatTextView textView = toolbar.findViewById(R.id.toolbar_title);
            textView.setText(title);
        }
    }

    public void initToolbar(@IdRes int id, @Nullable String title) {
        Toolbar toolbar = findViewById(id);
        setSupportActionBar(toolbar);
        if (title != null) {
            AppCompatTextView textView = toolbar.findViewById(R.id.toolbar_title);
            textView.setText(title);
        }
    }

    public void initToolbar() {
        initToolbar(R.id.toolbar, null);
    }

    public void initToolbar(String title) {
        initToolbar(R.id.toolbar, title);
    }

    public void initToolbar(@StringRes int title) {
        initToolbar(getString(title));
    }

    public void setHomeAsUp(boolean status) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(status);
        }
    }


    public void hideDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
