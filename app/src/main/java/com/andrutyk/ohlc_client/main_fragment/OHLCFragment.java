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
import android.widget.Toast;

import com.andrutyk.ohlc_client.R;
import com.andrutyk.ohlc_client.api.OHLCModel;
import com.andrutyk.ohlc_client.mvp.MVPPresenter;
import com.andrutyk.ohlc_client.mvp.MVPPresenterImpl;
import com.andrutyk.ohlc_client.mvp.MVPView;
import com.andrutyk.ohlc_client.recycler_view_decor.DividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 06.09.2016.
 */
public class OHLCFragment extends Fragment implements MVPView {

    @BindView(R.id.spDataProvider)
    Spinner spDataProvider;

    @BindView(R.id.etQuery)
    AutoCompleteTextView etQuery;

    @BindView(R.id.ivSearch)
    ImageView ivSearch;

    @BindView(R.id.rvData)
    RecyclerView rvData;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private MVPPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MVPPresenterImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ohlc_fragment, null);
        ButterKnife.bind(this, view);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.data_provider_array, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDataProvider.setAdapter(arrayAdapter);

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
        adapter = new OHLCAdapter(ohlcData.getDatasetData().getData());
        rvData.setAdapter(adapter);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEmptyData() {

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
    }

    @Override
    public void onPause() {
        presenter.onStop();
        super.onPause();
    }
}