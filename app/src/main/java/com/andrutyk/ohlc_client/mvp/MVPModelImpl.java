package com.andrutyk.ohlc_client.mvp;

import com.andrutyk.ohlc_client.api.ApiFactory;
import com.andrutyk.ohlc_client.api.OHLCModel;
import com.andrutyk.ohlc_client.api.OHLCService;
import com.andrutyk.ohlc_client.api.yahoo_model.YahooModel;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by admin on 06.09.2016.
 */
public class MVPModelImpl implements MVPModel {

    private final static String API_KEY_QUANDL = "sfNaN2tTSBdxzAKoivff";

    @Override
    public Observable<List<OHLCModel>> changeText() {
        return null;
    }

    @Override
    public Observable<List<List<String>>> request(String dataSet, String startDate, String endDate) {
        OHLCService ohlcService = ApiFactory.getOHLCService();
        /*return ohlcService.requestToQuandl(dataSet, API_KEY_QUANDL, startDate, endDate)
                .map(ohlcModel -> ohlcModel.getDatasetData().getData())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());*/

        return ohlcService.requestToYahoo(getQueryForYahoo(dataSet, startDate, endDate))
                .map(yahooModel -> yahooModelToList(yahooModel))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private List<List<String>> yahooModelToList(YahooModel yahooModel) {
        List<List<String>> lists = new ArrayList<>();
        int listSize = yahooModel.getQuery().getResults().getQuote().size();
        for (int i = 0; i < listSize; i++) {
            List<String> strings = new ArrayList<>();
            strings.add(yahooModel.getQuery().getResults().getQuote().get(i).getDate());
            strings.add(yahooModel.getQuery().getResults().getQuote().get(i).getOpen());
            strings.add(yahooModel.getQuery().getResults().getQuote().get(i).getHigh());
            strings.add(yahooModel.getQuery().getResults().getQuote().get(i).getLow());
            strings.add(yahooModel.getQuery().getResults().getQuote().get(i).getClose());
            lists.add(strings);
        }
        return lists;
    }

    private String getQueryForYahoo(String dataSet, String startDate, String endDate) {
        return "select * from yahoo.finance.historicaldata where symbol = \"" + dataSet +"\"" +
        " and startDate = \"" + startDate + "\" and endDate = \"" + endDate + "\"";
    }
}
