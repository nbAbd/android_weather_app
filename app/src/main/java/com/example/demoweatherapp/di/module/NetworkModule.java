package com.example.demoweatherapp.di.module;

import com.example.demoweatherapp.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    private String mUrl;

    public NetworkModule(String url) {
        this.mUrl = url;
    }

    @Provides
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor()
                .setLevel(BuildConfig.DEBUG ?
                        HttpLoggingInterceptor.Level.BODY :
                        HttpLoggingInterceptor.Level.NONE);
    }

    @Provides
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor) {

        // Necessary because our servers don't have the right cipher suites.
        // https://github.com/square/okhttp/issues/4053
        List<CipherSuite> cipherSuites = new ArrayList<>();
        List<CipherSuite> modernCipherSuites = ConnectionSpec.MODERN_TLS.cipherSuites();
        if (modernCipherSuites != null) {
            cipherSuites.addAll(modernCipherSuites);
        }
        cipherSuites.add(CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA);
        cipherSuites.add(CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA);

        ConnectionSpec legacyTls = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .cipherSuites(cipherSuites.toArray(new CipherSuite[0]))
                .build();

        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .retryOnConnectionFailure(true)
                .connectionSpecs(Arrays.asList(legacyTls, ConnectionSpec.CLEARTEXT))
                .build();
    }

    @Provides
    Gson provideGson() {
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(OkHttpClient client, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(mUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
