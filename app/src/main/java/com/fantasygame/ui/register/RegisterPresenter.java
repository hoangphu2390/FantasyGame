package com.fantasygame.ui.register;

import com.fantasygame.api.ServerAPI;
import com.fantasygame.base.Presenter;
import com.fantasygame.define.Dependencies;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by HP on 19/06/2017.
 */

public class RegisterPresenter extends Presenter<RegisterView> {

    ServerAPI serverAPI;

    public RegisterPresenter() {
        serverAPI = Dependencies.getServerAPI();
    }

    public void register(String username, String password, String display_name, String email,
                         String phone_number, String address) {
        final RegisterView view = view();
        if (view != null) {
            view.showLoadingUI();
        }
        subscriptions.add(serverAPI.register(username, password, display_name, email,
                phone_number, address)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (view != null) {
                        try {
                            view.hideLoadingUI();
                            view.showResultRegister(response);
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