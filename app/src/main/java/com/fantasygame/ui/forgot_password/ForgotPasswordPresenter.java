package com.fantasygame.ui.forgot_password;

import com.fantasygame.api.ServerAPI;
import com.fantasygame.base.Presenter;
import com.fantasygame.define.Dependencies;
import com.fantasygame.ui.confirm_info.ConfirmInfoView;

import java.util.TreeMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by HP on 22/08/2017.
 */

public class ForgotPasswordPresenter extends Presenter<ForgotPasswordView> {

    ServerAPI serverAPI;

    public ForgotPasswordPresenter() {
        serverAPI = Dependencies.getServerAPI();
    }


    public void forgotPassword(String email) {
        final ForgotPasswordView view = view();
        if (view != null) {
            view.showLoadingUI();
        }
        subscriptions.add(serverAPI.forgotPassword(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (view != null) {
                        try {
                            view.hideLoadingUI();
                            view.showResultForgotPassword(response);
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
