package com.example.demoweatherapp.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoweatherapp.App;
import com.example.demoweatherapp.BR;
import com.example.demoweatherapp.R;
import com.example.demoweatherapp.data.remote.api.model.Current;
import com.example.demoweatherapp.databinding.ActivityMainBinding;
import com.example.demoweatherapp.ui.adapter.DailyWeatherAdapter;
import com.example.demoweatherapp.ui.base.BaseMVVMActivity;
import com.example.demoweatherapp.ui.main.details.ActivityDailyDetails;
import com.example.demoweatherapp.utils.AppUtil;
import com.example.demoweatherapp.utils.TextViewFactory;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import timber.log.Timber;

public class MainActivity extends BaseMVVMActivity<ActivityMainBinding, MainViewModel> implements MainNavigator {

    @Inject
    MainViewModel mViewModel;

    @Inject
    DailyWeatherAdapter mAdapter;

    ActivityMainBinding mBinding;
    private static final int REQUEST_LOCATION_CODE = 1;
    private double mLongtitude, mLatitude;
    private Typeface typeface;

    FusedLocationProviderClient fusedLocationProviderClient;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = getViewDataBinding();
        mViewModel.setNavigator(this);

        setUp();
        setupTextSwitchers();
        initRecyclerView();
        initValues();

    }


    public void setUp() {
        typeface = Typeface.createFromAsset(getAssets(), "fonts/Vazir.ttf");
        checkForLocationPermission();
    }


    @Override
    public void performDependencyInjection() {
        App.get(this).getComponent().inject(this);
    }

    @Override
    public MainViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void handleNetworkError(Throwable throwable) {

    }

    @Override
    public void showProgress() {

    }

    @SuppressLint("ResourceAsColor")
    private void initValues() {
        mBinding.swipeRefresh.setColorSchemeColors(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mBinding.swipeRefresh.setOnRefreshListener(this::fetchWeather);
    }

    private void setupTextSwitchers() {
        mBinding.contentMain.tempTextView.setFactory(new TextViewFactory(this, R.style.TempTextView, true, typeface));
        mBinding.contentMain.tempTextView.setInAnimation(this, R.anim.slide_in_right);
        mBinding.contentMain.tempTextView.setOutAnimation(this, R.anim.slide_out_left);
        mBinding.contentMain.descriptionTextView.setFactory(new TextViewFactory(this, R.style.DescriptionTextView, true, typeface));
        mBinding.contentMain.descriptionTextView.setInAnimation(this, R.anim.slide_in_right);
        mBinding.contentMain.descriptionTextView.setOutAnimation(this, R.anim.slide_out_left);
        mBinding.contentMain.humidityTextView.setFactory(new TextViewFactory(this, R.style.HumidityTextView, false, typeface));
        mBinding.contentMain.humidityTextView.setInAnimation(this, R.anim.slide_in_bottom);
        mBinding.contentMain.humidityTextView.setOutAnimation(this, R.anim.slide_out_top);
        mBinding.contentMain.windTextView.setFactory(new TextViewFactory(this, R.style.WindSpeedTextView, false, typeface));
        mBinding.contentMain.windTextView.setInAnimation(this, R.anim.slide_in_bottom);
        mBinding.contentMain.windTextView.setOutAnimation(this, R.anim.slide_out_top);
        mBinding.contentMain.pressureTextView.setFactory(new TextViewFactory(this, R.style.WindSpeedTextView, false, typeface));
        mBinding.contentMain.pressureTextView.setInAnimation(this, R.anim.slide_in_bottom);
        mBinding.contentMain.pressureTextView.setOutAnimation(this, R.anim.slide_out_top);
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        RecyclerView recyclerView = mBinding.contentMain.recyclerView;
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setFocusable(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setListener(day -> startActivity(ActivityDailyDetails.getStartIntent(
                this,
                day,
                AppUtil.getRandomMaterialColor(getResources()))));
    }

    private void fetchWeather() {
        if (AppUtil.isNetworkConnected(this)) {
            if (mLongtitude != 0 && mLatitude != 0) {
                mViewModel.fetchWeather(mLatitude, mLongtitude, getString(R.string.openweathermap_api_key));
            } else {
                mBinding.swipeRefresh.setRefreshing(false);
            }
        } else {
            showToast("Please check internet connection");
            mBinding.swipeRefresh.setRefreshing(false);
        }
    }

    public void checkForLocationPermission() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            // ask for permission
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION_CODE);
        } else {
            getCurrentLocation();
        }
    }

    public void getCurrentLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {

            // initialize location
            Location location = task.getResult();
            if (location != null) {

                try {
                    List<Address> addresses = new Geocoder(MainActivity.this, Locale.getDefault())
                            .getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                    mLatitude = addresses.get(0).getLatitude();
                    mLongtitude = addresses.get(0).getLongitude();
                    Timber.d("adresses%s", addresses.toString());
                    fetchWeather();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void populateCurrentWeather(Current current) {
        initToolbar(Objects.requireNonNull(mViewModel.weatherInfo.get()).getTimezone());
        mBinding.contentMain.tempTextView.setText(String.format(Locale.getDefault(), "%.0f°", current.getTemperature()));
        mBinding.contentMain.descriptionTextView.setText(current.getWeather().get(0).getDescription());
        mBinding.contentMain.humidityTextView.setText(String.format(Locale.getDefault(), "%d%%", current.getHumidity()));
        mBinding.contentMain.windTextView.setText(String.format(Locale.getDefault(), getResources().getString(R.string.wind_unit_label), current.getWindSpeed()));
        mBinding.contentMain.pressureTextView.setText(String.format(Locale.getDefault(), getResources().getString(R.string.pressure_unit_label), current.getPressure()));

        //set animation
        mBinding.contentMain.animationView.setAnimation(AppUtil.getWeatherAnimation(current.getWeather().get(0).getId()));
        mBinding.contentMain.animationView.playAnimation();
        mAdapter.setItems(Objects.requireNonNull(mViewModel.weatherInfo.get()).getDaily());


    }

    @Override
    public void showEmptyLayout() {
        mBinding.contentEmpty.setVisibility(View.VISIBLE);
        mBinding.contentMain.nestedScrollView.setVisibility(View.GONE);
        mBinding.swipeRefresh.setRefreshing(false);
    }

    @Override
    public void hideEmptyLayout() {
        mBinding.contentEmpty.setVisibility(View.GONE);
        mBinding.contentMain.nestedScrollView.setVisibility(View.VISIBLE);
        mBinding.swipeRefresh.setRefreshing(false);
    }
}
