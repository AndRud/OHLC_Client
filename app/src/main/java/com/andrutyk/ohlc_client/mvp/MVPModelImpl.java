package com.andrutyk.ohlc_client.mvp;

import com.andrutyk.ohlc_client.api.ApiFactory;
import com.andrutyk.ohlc_client.api.OHLCModel;
import com.andrutyk.ohlc_client.api.OHLCService;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by admin on 06.09.2016.
 */
public class MVPModelImpl implements MVPModel {

    private final static String API_KEY = "sfNaN2tTSBdxzAKoivff";

    @Override
    public Observable<List<OHLCModel>> changeText() {
        return null;
    }

    @Override
    public Observable<OHLCModel> request(String dataSet) {
        OHLCService ohlcService = ApiFactory.getOHLCService();
        return ohlcService.ohlcRequest(dataSet, API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
