package com.andrutyk.ohlc_client.api;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by admin on 06.09.2016.
 */
public interface OHLCService {

    @GET("/api/v3/datasets/WIKI/{dataSet}/data.json")
    Observable<OHLCModel> requestToQuandl(@Path("dataSet") String dataSet,
                                      @Query("api_key") String api_key,
                                      @Query("start_date") String start_date,
                                      @Query("end_date") String end_date);

    @GET("/finance/historical?output=csv")
    Observable<OHLCModel> requestToGoogle(@Query("q") String dataSet/*,
                                      @Query("start_date") String start_date,
                                      @Query("end_date") String end_date*/);

    @GET("/v1/public/yql?q=select%20*%20from%20yahoo.finance.historicaldata%20where%20symbol%20%3D%20%22{dataSet}%22" +
            "%20and%20startDate%20%3D%20%22{start_date}%22%20and%20endDate%20%3D%20%22{end_date}%22&format=json" +
            "&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=")
    Observable<OHLCModel> requestToYahoo(@Path("dataSet") String dataSet,
                                         @Path("start_date") String start_date,
                                         @Path("end_date") String end_date);
}
