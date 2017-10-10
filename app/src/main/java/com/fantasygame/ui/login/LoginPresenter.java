package com.fantasygame.ui.login;

import com.fantasygame.api.ServerAPI;
import com.fantasygame.base.Presenter;
import com.fantasygame.api.Dependencies;


import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Double T on 1/12/2017.
 */

public class LoginPresenter extends Presenter<LoginView> {

    ServerAPI serverAPI;

    public LoginPresenter() {
        serverAPI = Dependencies.getServerAPI();
    }

    public void login(String email, String password) {
        final LoginView view = view();
        if (view != null) {
            view.showLoadingUI();
        }
        subscriptions.add(serverAPI.login(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (view != null) {
                        try {
                            view.hideLoadingUI();
                            view.showResultLogin(response);
                        } catch (Exception ex) {
                        }
                    }

                }, throwable -> {
                    if (view != null) {
                        view.showErrorLoadingUI(throwable);
                    }
                }));
    }
}
