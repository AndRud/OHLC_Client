package com.andrutyk.ohlc_client.api;

import com.andrutyk.ohlc_client.api.yahoo_model.YahooModel;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by admin on 06.09.2016.
 */
public interface APIService {

    @GET("/api/v3/datasets/WIKI/{dataSet}/data.json")
    Observable<OHLCModel> requestToQuandl(@Path("dataSet") String dataSet,
                                      @Query("api_key") String api_key,
                                      @Query("start_date") String start_date,
                                      @Query("end_date") String end_date);

    @GET("/v1/public/yql?format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=")
    Observable<YahooModel> requestToYahoo(@Query("q") String query);
}
