package com.andrutyk.ohlc_client.mvp;

import com.andrutyk.ohlc_client.api.OHLCModel;

import java.util.List;

/**
 * Created by admin on 06.09.2016.
 */
public interface MVPView {
    void showData(List<List<String>> data);

    void showError(String error);

    void showEmptyData();

    String getProvider();

    String getDataSet();

    String getStartDate();

    String getEndDate();
}
