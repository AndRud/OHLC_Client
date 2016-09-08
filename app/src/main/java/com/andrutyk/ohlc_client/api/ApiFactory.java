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

    private static final String API_ENDPOINT_QUANDL = "https://www.quandl.com/";
    private static final String API_ENDPOINT_GOOGLE = "http://www.google.com/";
    private static final String API_ENDPOINT_YAHOO = "https://query.yahooapis.com/";

    private static final OkHttpClient CLIENT = new OkHttpClient();

    private static final Gson GSON = new GsonBuilder()
            .create();

    @NonNull
    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                //.baseUrl(API_ENDPOINT_QUANDL)
                .baseUrl(API_ENDPOINT_YAHOO)
                .addConverterFactory(GsonConverterFactory.create(GSON))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(CLIENT)
                .build();
    }

    @NonNull
    public static OHLCService getOHLCService() {
        return getRetrofit().create(OHLCService.class);
    }
}
