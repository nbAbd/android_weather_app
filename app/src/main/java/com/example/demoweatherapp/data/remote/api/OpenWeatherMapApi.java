package com.example.demoweatherapp.data.remote.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.demoweatherapp.data.remote.api.model.TotalWeatherInfo;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherMapApi {

    @GET("2.5/onecall")
    Single<TotalWeatherInfo> getWeather(@Query("lat") Double latitude,
                                        @Query("lon") Double longitude,
                                        @Query("units") @Nullable String units,
                                        @Query("appid") @NonNull String apiKey,
                                        @Query("lang") String lang);

}
