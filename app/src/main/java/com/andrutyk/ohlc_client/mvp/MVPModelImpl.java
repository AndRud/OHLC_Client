package com.andrutyk.ohlc_client.mvp;

import android.content.Context;

import com.andrutyk.ohlc_client.R;
import com.andrutyk.ohlc_client.api.APIService;
import com.andrutyk.ohlc_client.api.ApiFactory;
import com.andrutyk.ohlc_client.api.OHLCModel;
import com.andrutyk.ohlc_client.api.yahoo_model.Quote;
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

    private static final String API_ENDPOINT_QUANDL = "https://www.quandl.com/";
    private static final String API_ENDPOINT_YAHOO = "https://query.yahooapis.com/";

    @Override
    public Observable<List<OHLCModel>> changeText() {
        return null;
    }

    @Override
    public Observable<List<List<String>>> request(Context context, String provider, String dataSet,
                                                  String startDate, String endDate) {

        String[] providers = context.getResources().getStringArray(R.array.data_provider_array);
        Observable<List<List<String>>> listObservable;
        if (provider.equals(providers[0])) {
            APIService apiService = ApiFactory.getAPIService(API_ENDPOINT_QUANDL);
            listObservable = apiService.requestToQuandl(dataSet, API_KEY_QUANDL, startDate, endDate)
                    .map(ohlcModel -> ohlcModel.getDatasetData().getData())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else if (provider.equals(providers[1])) {
            APIService apiService = ApiFactory.getAPIService(API_ENDPOINT_YAHOO);
            listObservable = apiService.requestToYahoo(getQueryForYahoo(dataSet, startDate, endDate))
                    .map(yahooModel -> yahooModelToList(yahooModel))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            listObservable = Observable.empty();
        }

        return listObservable;
    }

    private List<List<String>> yahooModelToList(YahooModel yahooModel) {
        List<List<String>> lists = new ArrayList<>();
        List<Quote> quote = yahooModel.getQuery().getResults().getQuote();
        int listSize = quote.size();
        for (int i = 0; i < listSize; i++) {
            List<String> strings = new ArrayList<>();
            strings.add(quote.get(i).getDate());
            strings.add(quote.get(i).getOpen());
            strings.add(quote.get(i).getHigh());
            strings.add(quote.get(i).getLow());
            strings.add(quote.get(i).getClose());
            lists.add(strings);
        }
        return lists;
    }

    private String getQueryForYahoo(String dataSet, String startDate, String endDate) {
        return "select * from yahoo.finance.historicaldata where symbol = \"" + dataSet +"\"" +
        " and startDate = \"" + startDate + "\" and endDate = \"" + endDate + "\"";
    }
}
