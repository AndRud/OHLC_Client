package com.andrutyk.ohlc_client.main_fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andrutyk.ohlc_client.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 07.09.2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<List<String>> data;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvDate) TextView tvDate;
        @BindView(R.id.tvOpen) TextView tvOpen;
        @BindView(R.id.tvClose) TextView tvClose;
        @BindView(R.id.tvHigh) TextView tvHigh;
        @BindView(R.id.tvLow) TextView tvLow;

        private View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
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
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvDate.setText(data.get(position).get(0));
        holder.tvOpen.setText(data.get(position).get(1));
        holder.tvHigh.setText(data.get(position).get(2));
        holder.tvLow.setText(data.get(position).get(3));
        holder.tvClose.setText(data.get(position).get(4));
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

    public void clear() {
        if (data != null) {
            data.clear();
        }
    }

    public List<List<String>> getData() {
        return data;
    }
}
