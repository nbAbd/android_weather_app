package com.example.demoweatherapp.data.remote.api.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Current implements Serializable {

    @SerializedName("dt")
    @Expose
    private Integer currentTime;

    @SerializedName("sunrise")
    @Expose
    private Integer sunriseTime;


    @SerializedName("sunset")
    @Expose
    private Integer sunsetTime;

    @SerializedName("temp")
    @Expose
    private Double temperature;


    /*Temperature. This temperature parameter accounts for the human perception of weather.
    Units – default: kelvin, metric: Celsius, imperial: Fahrenheit.
    */
    @SerializedName("feels_like")
    @Expose
    private Double feelsLike;


    @SerializedName("pressure")
    @Expose
    private Integer pressure;


    // влажность
    @SerializedName("humidity")
    @Expose
    private Integer humidity;


    /*
    Атмосферная температура (в зависимости от давления и влажности),
    ниже которой капли воды начинают конденсироваться и может образоваться роса.
    Единицы - по умолчанию: Кельвин, метрическая: Цельсия, имперская: Фаренгейт.*/
    @SerializedName("dew_point")
    @Expose
    private Double dewPoint;


    // Ultraviolet radiation.
    @SerializedName("uvi")
    @Expose
    private Double ultraVioletIndex;


    @SerializedName("clouds")
    @Expose
    private Double clouds;


    @SerializedName("visibility")
    @Expose
    private Double visibility;


    @SerializedName("wind_speed")
    @Expose
    private Double windSpeed;


    @SerializedName("wind_deg")
    @Expose
    private Double windDeg;


    @SerializedName("weather")
    @Expose
    private List<Weather> weather = null;


    public Integer getCurrentTime() {
        return currentTime;
    }

    public Integer getSunriseTime() {
        return sunriseTime;
    }

    public Integer getSunsetTime() {
        return sunsetTime;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getFeelsLike() {
        return feelsLike;
    }

    public Integer getPressure() {
        return pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public Double getDewPoint() {
        return dewPoint;
    }

    public Double getUltraVioletIndex() {
        return ultraVioletIndex;
    }

    public Double getClouds() {
        return clouds;
    }

    public Double getVisibility() {
        return visibility;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public Double getWindDeg() {
        return windDeg;
    }

    public List<Weather> getWeather() {
        return weather;
    }
}

