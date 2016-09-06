
package com.andrutyk.ohlc_client.api;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatasetData {

    @SerializedName("limit")
    @Expose
    private Object limit;
    @SerializedName("transform")
    @Expose
    private Object transform;
    @SerializedName("column_index")
    @Expose
    private Object columnIndex;
    @SerializedName("column_names")
    @Expose
    private List<String> columnNames = new ArrayList<String>();
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("frequency")
    @Expose
    private String frequency;
    @SerializedName("data")
    @Expose
    private List<List<String>> data = new ArrayList<List<String>>();
    @SerializedName("collapse")
    @Expose
    private Object collapse;
    @SerializedName("order")
    @Expose
    private Object order;

    /**
     * 
     * @return
     *     The limit
     */
    public Object getLimit() {
        return limit;
    }

    /**
     * 
     * @param limit
     *     The limit
     */
    public void setLimit(Object limit) {
        this.limit = limit;
    }

    /**
     * 
     * @return
     *     The transform
     */
    public Object getTransform() {
        return transform;
    }

    /**
     * 
     * @param transform
     *     The transform
     */
    public void setTransform(Object transform) {
        this.transform = transform;
    }

    /**
     * 
     * @return
     *     The columnIndex
     */
    public Object getColumnIndex() {
        return columnIndex;
    }

    /**
     * 
     * @param columnIndex
     *     The column_index
     */
    public void setColumnIndex(Object columnIndex) {
        this.columnIndex = columnIndex;
    }

    /**
     * 
     * @return
     *     The columnNames
     */
    public List<String> getColumnNames() {
        return columnNames;
    }

    /**
     * 
     * @param columnNames
     *     The column_names
     */
    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    /**
     * 
     * @return
     *     The startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * 
     * @param startDate
     *     The start_date
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * 
     * @return
     *     The endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * 
     * @param endDate
     *     The end_date
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * 
     * @return
     *     The frequency
     */
    public String getFrequency() {
        return frequency;
    }

    /**
     * 
     * @param frequency
     *     The frequency
     */
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    /**
     * 
     * @return
     *     The data
     */
    public List<List<String>> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<List<String>> data) {
        this.data = data;
    }

    /**
     * 
     * @return
     *     The collapse
     */
    public Object getCollapse() {
        return collapse;
    }

    /**
     * 
     * @param collapse
     *     The collapse
     */
    public void setCollapse(Object collapse) {
        this.collapse = collapse;
    }

    /**
     * 
     * @return
     *     The order
     */
    public Object getOrder() {
        return order;
    }

    /**
     * 
     * @param order
     *     The order
     */
    public void setOrder(Object order) {
        this.order = order;
    }

}
