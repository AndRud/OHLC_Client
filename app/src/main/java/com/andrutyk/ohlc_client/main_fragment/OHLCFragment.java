package com.andrutyk.ohlc_client.main_fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andrutyk.ohlc_client.R;

/**
 * Created by admin on 06.09.2016.
 */
public class OHLCFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.ohlc_fragment, null);

        return view;
    }
}