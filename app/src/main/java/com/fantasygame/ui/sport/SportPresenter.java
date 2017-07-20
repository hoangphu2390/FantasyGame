package com.fantasygame.ui.sport;

import com.fantasygame.api.ServerAPI;
import com.fantasygame.base.Presenter;
import com.fantasygame.define.Dependencies;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by HP on 21/06/2017.
 */

public class SportPresenter extends Presenter<SportView> {

    final int page = 1, limit = 10;
    ServerAPI serverAPI;

    public SportPresenter() {
        serverAPI = Dependencies.getServerAPI();
    }

    public void getListSport() {
        final SportView view = view();
        if (view != null) {
            view.showLoadingUI();
        }
        subscriptions.add(serverAPI.getListSport()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (view != null) {
                        try {
                            view.hideLoadingUI();
                            view.showResultGetListSport(response);
                        } catch (Exception ex) {
                        }
                    }

                }, throwable -> {
                    if (view != null) {
                        view.hideLoadingUI();
                        view.showErrorLoadingUI(throwable);
                    }
                }));
    }

    public void getAllWinner() {
        final SportView view = view();
        if (view != null) {
            view.showLoadingUI();
        }
        subscriptions.add(serverAPI.getAllWinner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (view != null) {
                        try {
                            view.hideLoadingUI();
                            if (response.result && response.data.size() > 0)
                                view.showResultGetAllWinner(response.data);
                            else
                                getListFeature();
                        } catch (Exception ex) {
                        }
                    }

                }, throwable -> {
                    if (view != null) {
                        view.hideLoadingUI();
                        view.showErrorLoadingUI(throwable);
                    }
                }));
    }

    public void getListFeature() {
        final SportView view = view();
        if (view != null) {
            view.showLoadingUI();
        }
        subscriptions.add(serverAPI.getListFeature(page, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (view != null) {
                        try {
                            view.hideLoadingUI();
                            if (response.result)
                                view.showResultGetListFeature(response.data);
                        } catch (Exception ex) {
                        }
                    }

                }, throwable -> {
                    if (view != null) {
                        view.hideLoadingUI();
                        view.showErrorLoadingUI(throwable);
                    }
                }));
    }
}

