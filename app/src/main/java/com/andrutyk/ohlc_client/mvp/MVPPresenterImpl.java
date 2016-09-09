package com.andrutyk.ohlc_client.mvp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.andrutyk.ohlc_client.api.OHLCModel;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by admin on 06.09.2016.
 */
public class MVPPresenterImpl implements MVPPresenter {

    private MVPModel mvpModel = new MVPModelImpl();

    private MVPView view;
    private Subscription subscription = Subscriptions.empty();

    private Context context;

    public MVPPresenterImpl(Context context, MVPView view) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void onSearch() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = mvpModel.request(context, view.getProvider(), view.getDataSet(), view.getStartDate(),
                view.getEndDate())
                .subscribe(new Observer<List<List<String>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<List<String>> data) {
                        if (data != null) {
                            view.showData(data);
                        } else {
                            view.showEmptyData();
                        }
                    }
                });
    }

    @Override
    public void onStop() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
