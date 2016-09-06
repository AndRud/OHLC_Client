
package com.andrutyk.ohlc_client.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OHLCModel {

    @SerializedName("dataset_data")
    @Expose
    private DatasetData datasetData;

    /**
     * 
     * @return
     *     The datasetData
     */
    public DatasetData getDatasetData() {
        return datasetData;
    }

    /**
     * 
     * @param datasetData
     *     The dataset_data
     */
    public void setDatasetData(DatasetData datasetData) {
        this.datasetData = datasetData;
    }

}
