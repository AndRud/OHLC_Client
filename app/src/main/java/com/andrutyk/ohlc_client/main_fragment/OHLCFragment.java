package com.andrutyk.ohlc_client.main_fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.andrutyk.ohlc_client.R;
import com.andrutyk.ohlc_client.api.OHLCModel;
import com.andrutyk.ohlc_client.mvp.MVPPresenter;
import com.andrutyk.ohlc_client.mvp.MVPPresenterImpl;
import com.andrutyk.ohlc_client.mvp.MVPView;

import java.util.List;

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
    EditText etQuery;

    @BindView(R.id.ivSearch)
    ImageView ivSearch;

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
        return view;
    }

    @Override
    public void showData(OHLCModel OHLCData) {

    }

    @Override
    public void showError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEmptyData() {

    }

    @Override
    public String getQuery() {
        return etQuery.getText().toString();
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