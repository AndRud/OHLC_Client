package com.andrutyk.ohlc_client.main_fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andrutyk.ohlc_client.R;
import com.andrutyk.ohlc_client.mvp.MVPPresenter;
import com.andrutyk.ohlc_client.mvp.MVPPresenterImpl;
import com.andrutyk.ohlc_client.mvp.MVPView;
import com.andrutyk.ohlc_client.recycler_view_decor.DividerItemDecoration;
import com.andrutyk.ohlc_client.utils.ConnectionDetector;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 06.09.2016.
 */
public class MainFragment extends Fragment implements MVPView {

    private final static int PAGINATION_DATE_LIMIT = 50;

    private List<List<String>> data;

    private String defProvider;

    String provider;
    DateTime startDate;
    DateTime endDate;

    @BindView(R.id.etQuery)
    AutoCompleteTextView etQuery;

    @BindView(R.id.ivSearch)
    ImageView ivSearch;

    @BindView(R.id.rvData)
    RecyclerView rvData;

    @BindView(R.id.tvNoResults)
    TextView tvNoResults;

    @BindView(R.id.clProgress)
    ContentLoadingProgressBar clProgress;

    private RecyclerViewAdapter adapter;
    private LinearLayoutManager layoutManager;

    private MVPPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        presenter = new MVPPresenterImpl(getActivity(), this);
        resetDate();
        defProvider = getActivity().getResources().getStringArray(R.array.data_provider_array)[0];
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
        if (data != null) {
            adapter = new RecyclerViewAdapter(data);
        } else {
            adapter = new RecyclerViewAdapter();
        }
        rvData.setAdapter(adapter);
        rvData.addOnScrollListener(onScrollListener);

        String[] tickers = getActivity().getResources().getStringArray(R.array.tickers_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, tickers);
        etQuery.setAdapter(adapter);

        return view;
    }

    @Override
    public void showData(List<List<String>> data) {
        clProgress.setVisibility(View.GONE);
        addDateForPagination();
        showLoading(false);
        setAdapterData(data);
    }

    @Override
    public void onPause() {
        if (adapter != null) {
            this.data = adapter.getData();
        }
        super.onPause();
    }

    private void setAdapterData(List<List<String>> data) {
        if (data != null) {
            adapter.addAll(data);
            showEmptyView(false);
        } else {
            showEmptyView(true);
        }
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        showEmptyView(true);
        showLoading(false);
    }

    @Override
    public void showEmptyData() {
        showEmptyView(true);
    }

    @Override
    public String getProvider() {
        if (provider == null || provider.isEmpty()){
            return defProvider;
        } else {
            return provider;
        }
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
        return getDateStr(startDate);
    }

    @Override
    public String getEndDate() {
        return getDateStr(endDate);
    }

    @OnClick(R.id.ivSearch)
    public void search() {
        clearData();
        resetDate();
        if (!isConnected()){
            showSettingDialog();
            showEmptyData();
        } else {
            presenter.onSearch();
            showLoading(true);
        }
    }

    @Override
    public void onStop() {
        if (presenter != null) {
            presenter.onStop();
        }
        super.onStop();
    }

    private void showEmptyView(boolean isVisible) {
        if (isVisible) {
            tvNoResults.setVisibility(View.VISIBLE);
            rvData.setVisibility(View.GONE);
        } else {
            tvNoResults.setVisibility(View.GONE);
            rvData.setVisibility(View.VISIBLE);
        }

    }

    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = layoutManager.getChildCount();
            int firstVisibleItems = layoutManager.findFirstVisibleItemPosition();
            int position = firstVisibleItems + visibleItemCount;

            int totalItemCount = layoutManager.getItemCount();
            int updatePosition = totalItemCount - 1 - (PAGINATION_DATE_LIMIT / 2);

            if (position >= updatePosition) {
                showLoading(true);
                presenter.onSearch();
            }
        }
    };

    private String getDateStr(DateTime dateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        return dateTimeFormatter.print(dateTime);
    }

    private void addDateForPagination() {
        endDate = startDate.minusDays(1);
        startDate = startDate.minusDays(PAGINATION_DATE_LIMIT + 1);
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    private void clearData() {
        if (adapter != null) {
            adapter.clear();
        }
    }

    private void resetDate() {
        endDate = DateTime.now();
        startDate = endDate.minusDays(PAGINATION_DATE_LIMIT);
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }

    public List<List<String>> getData() {
        return data;
    }

    private boolean isConnected() {
        ConnectionDetector connectionDetector = new ConnectionDetector(getActivity());
        return connectionDetector.isConnectingToInternet();
    }

    private void showSettingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.question_internet_conn);
        builder.setPositiveButton(R.string.settings, (dialog, which) ->
                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0));
        builder.setNegativeButton(R.string.dismiss, (dialog, which) -> dialog.cancel());
        builder.create().show();
    }

    private void showLoading(boolean isLoading) {
        if (isLoading) {
            clProgress.setVisibility(View.VISIBLE);
        } else {
            clProgress.setVisibility(View.GONE);
        }
    }
}