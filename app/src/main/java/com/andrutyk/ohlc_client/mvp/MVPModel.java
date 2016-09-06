package com.andrutyk.ohlc_client.mvp;

import com.andrutyk.ohlc_client.api.OHLCModel;

import java.util.List;

import rx.Observable;

/**
 * Created by admin on 06.09.2016.
 */
public interface MVPModel {
    Observable<List<OHLCModel>> changeText();

    Observable<OHLCModel> request(String query);
}
