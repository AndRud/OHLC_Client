package com.andrutyk.ohlc_client.api;

import android.support.annotation.NonNull;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 06.09.2016.
 */
public class ApiFactory {

    private static final OkHttpClient CLIENT = new OkHttpClient();

    private static final Gson GSON = new GsonBuilder()
            .create();

    @NonNull
    private static Retrofit getRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(GSON))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(CLIENT)
                .build();
    }

    @NonNull
    public static APIService getAPIService(String baseUrl) {
        return getRetrofit(baseUrl).create(APIService.class);
    }
}
