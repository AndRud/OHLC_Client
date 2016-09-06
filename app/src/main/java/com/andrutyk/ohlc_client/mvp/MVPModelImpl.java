package com.andrutyk.ohlc_client.mvp;

import com.andrutyk.ohlc_client.api.ApiFactory;
import com.andrutyk.ohlc_client.api.OHLCModel;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by admin on 06.09.2016.
 */
public class MVPModelImpl implements MVPModel {

    @Override
    public Observable<List<OHLCModel>> changeText() {
        return null;
    }

    @Override
    public Observable<List<OHLCModel>> request(String query) {
        return ApiFactory.getOHLCService().ohlcRequest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
