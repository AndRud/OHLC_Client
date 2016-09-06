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
    public void onSearchClick() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscription = mvpModel.request("")
                .subscribe(new Observer<List<OHLCModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<OHLCModel> data) {
                        if (data != null && !data.isEmpty()) {
                            view.showList(data);
                        } else {
                            view.showEmptyList();
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
