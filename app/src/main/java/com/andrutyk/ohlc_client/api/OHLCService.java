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

    @GET("/api/v3/datasets/WIKI/{dataSet}/data.json?start_date=2015-05-24&end_date=2015-05-28")
    Observable<OHLCModel> ohlcRequest(@Path("dataSet") String dataSet, @Query("api_key") String api_key);
}
