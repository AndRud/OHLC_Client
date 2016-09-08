package com.andrutyk.ohlc_client.mvp;

import android.app.Activity;
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

    public MVPPresenterImpl(MVPView view) {
        this.view = view;
    }

    @Override
    public void onSearch() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = mvpModel.request(view.getDataSet(), view.getStartDate(),
                view.getEndDate())
                .subscribe(new Observer<OHLCModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(OHLCModel data) {
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
