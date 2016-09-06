package com.andrutyk.ohlc_client.mvp;

import com.andrutyk.ohlc_client.api.OHLCModel;

import java.util.List;

/**
 * Created by admin on 06.09.2016.
 */
public interface MVPView {
    void showData(OHLCModel OHLCData);

    void showError(String error);

    void showEmptyData();

    String getQuery();
}
