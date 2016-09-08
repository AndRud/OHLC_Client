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

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 06.09.2016.
 */
public class MainFragment extends Fragment implements MVPView {

    private final static int PAGINATION_LIMIT = 5;

    String dataSet;
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

    private RecyclerView.Adapter adapter;
    private LinearLayoutManager layoutManager;

    private MVPPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        presenter = new MVPPresenterImpl(this);
        endDate = DateTime.now();
        startDate = endDate.minusDays(PAGINATION_LIMIT);
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
        rvData.addOnScrollListener(onScrollListener);

        String[] tickers = getActivity().getResources().getStringArray(R.array.tickers_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, tickers);
        etQuery.setAdapter(adapter);

        return view;
    }

    @Override
    public void showData(List<List<String>> data) {
        addDateForPagination();
        setAdapterData(data);
    }

    private void setAdapterData(List<List<String>> data) {
        if (data != null) {
            if (adapter == null) {
                adapter = new OHLCAdapter(data);
            } else {
                ((OHLCAdapter) adapter).addItems(data);
                adapter.notifyItemInserted(adapter.getItemCount() - 1);
            }
            rvData.setAdapter(adapter);
            showEmptyView(false);
        } else {
            showEmptyView(true);
        }
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        showEmptyView(true);
    }

    @Override
    public void showEmptyData() {
        showEmptyView(true);
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
    public void search(View view) {
        presenter.onSearch();
        //Toast.makeText(getActivity(), dataSet, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        if (presenter != null) {
            presenter.onStop();
        }
        super.onStop();
    }

    public void setDataSet(String dataSet) {
        this.dataSet = dataSet;
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
            int updatePosition = totalItemCount - 1 - (PAGINATION_LIMIT / 2);

            if (position >= updatePosition) {
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
        startDate = startDate.minusDays(PAGINATION_LIMIT + 1);
    }
}