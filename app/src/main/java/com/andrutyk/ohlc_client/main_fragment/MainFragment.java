package com.andrutyk.ohlc_client.main_fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andrutyk.ohlc_client.R;
import com.andrutyk.ohlc_client.api.OHLCModel;
import com.andrutyk.ohlc_client.mvp.MVPPresenter;
import com.andrutyk.ohlc_client.mvp.MVPPresenterImpl;
import com.andrutyk.ohlc_client.mvp.MVPView;
import com.andrutyk.ohlc_client.recycler_view_decor.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 06.09.2016.
 */
public class MainFragment extends Fragment implements MVPView {

    String dataSet;

    @BindView(R.id.etQuery)
    AutoCompleteTextView etQuery;

    @BindView(R.id.ivSearch)
    ImageView ivSearch;

    @BindView(R.id.rvData)
    RecyclerView rvData;

    @BindView(R.id.tvNoResults)
    TextView tvNoResults;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private MVPPresenter presenter;

    List<List<String>> data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        presenter = new MVPPresenterImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, null);
        ButterKnife.bind(this, view);

        rvData.setHasFixedSize(true);
        rvData.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        layoutManager = new LinearLayoutManager(getActivity());
        rvData.setLayoutManager(layoutManager);

        String[] tickers = getActivity().getResources().getStringArray(R.array.tickers_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, tickers);
        etQuery.setAdapter(adapter);

        return view;
    }

    @Override
    public void showData(OHLCModel ohlcData) {
        setData(ohlcData.getDatasetData().getData());
        setAdapterData();
    }

    private void setAdapterData() {
        if (data != null) {
            adapter = new OHLCAdapter(data);
            rvData.setAdapter(adapter);
            showNoResult(false);
        } else {
            showNoResult(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setAdapterData();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        showNoResult(true);
    }

    @Override
    public void showEmptyData() {
        showNoResult(true);
    }

    @Override
    public String getDataSet() {
        return getTickerName(etQuery.getText().toString());
    }

    private String getTickerName(String s) {
        int index = s.indexOf(",");
        if (index > 0) {
            return s.substring(0, index);
        } else {
            return s;
        }
    }

    @Override
    public String getStartDate() {
        return "2015-01-24";
    }

    @Override
    public String getEndDate() {
        return "2015-05-28";
    }

    @OnClick(R.id.ivSearch)
    public void search(View view) {
        presenter.onSearchClick();
        Toast.makeText(getActivity(), dataSet, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        presenter.onStop();
        super.onPause();
    }

    public void setDataSet(String dataSet) {
        this.dataSet = dataSet;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }

    public List<List<String>> getData() {
        return data;
    }

    private void showNoResult(boolean isVisible) {
        if (isVisible) {
            tvNoResults.setVisibility(View.VISIBLE);
            rvData.setVisibility(View.GONE);
        } else {
            tvNoResults.setVisibility(View.GONE);
            rvData.setVisibility(View.VISIBLE);
        }

    }
}