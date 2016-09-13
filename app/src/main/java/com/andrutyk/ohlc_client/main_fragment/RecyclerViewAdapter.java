package com.andrutyk.ohlc_client.main_fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.andrutyk.ohlc_client.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 07.09.2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<List<String>> data;

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvDate) TextView tvDate;
        @BindView(R.id.tvOpen) TextView tvOpen;
        @BindView(R.id.tvClose) TextView tvClose;
        @BindView(R.id.tvHigh) TextView tvHigh;
        @BindView(R.id.tvLow) TextView tvLow;

        public DataViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public RecyclerViewAdapter() {
        data = new ArrayList<>();
    }

    public RecyclerViewAdapter(List<List<String>> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        return createDataViewHolder(parent);
    }

    private RecyclerView.ViewHolder createDataViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);

        return new DataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindDataViewHolder(holder, position);
    }

    private void bindDataViewHolder(RecyclerView.ViewHolder holder, int position) {
        final DataViewHolder dataViewHolder = (DataViewHolder) holder;
        if (getItemCount() > 0) {
            dataViewHolder.tvDate.setText(data.get(position).get(0));
            dataViewHolder.tvOpen.setText(data.get(position).get(1));
            dataViewHolder.tvHigh.setText(data.get(position).get(2));
            dataViewHolder.tvLow.setText(data.get(position).get(3));
            dataViewHolder.tvClose.setText(data.get(position).get(4));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void add(List<String> data) {
        this.data.add(data);
        notifyDataSetChanged();
    }

    public void addAll(List<List<String>> data) {
        for (List<String> dataStr : data) {
            add(dataStr);
        }
    }

    public void remove(List<String> item) {
        int position = data.indexOf(item);
        if (position > -1) {
            data.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        if (data != null) {
            data.clear();
        }
        /*while (getItemCount() > 0) {
            remove(getItem(0));
        }*/
    }

    public List<String> getItem(int position) {
        return data.get(position);
    }

    public List<List<String>> getData() {
        return data;
    }
}
