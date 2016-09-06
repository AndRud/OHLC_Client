package com.andrutyk.ohlc_client.api;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by admin on 06.09.2016.
 */
public interface OHLCService {

    @GET("/api/v3/datasets/WIKI/AAPL.json?start_date=1985-05-01&end_date=1997-07-01&order=asc&column_index=4&collapse=quarterly&transformation=rdiff")
    Observable<List<OHLCModel>> ohlcRequest();
}
