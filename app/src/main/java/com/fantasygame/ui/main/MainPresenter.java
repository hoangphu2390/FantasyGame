package com.fantasygame.ui.main;

import com.fantasygame.api.ServerAPI;
import com.fantasygame.base.Presenter;
import com.fantasygame.define.Dependencies;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by HP on 20/06/2017.
 */

public class MainPresenter extends Presenter<MainView> {

    ServerAPI serverAPI;

    public MainPresenter() {
        serverAPI = Dependencies.getServerAPI();
    }

    public void logout(String api_token) {
        final MainView view = view();
        if (view != null) {
            view.showLoadingUI();
        }
        subscriptions.add(serverAPI.logout(api_token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (view != null) {
                        try {
                            view.hideLoadingUI();
                            view.showResultLogout(response);
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

