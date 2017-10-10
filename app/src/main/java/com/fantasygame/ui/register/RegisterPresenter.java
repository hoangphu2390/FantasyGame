package com.fantasygame.ui.register;

import com.fantasygame.api.ServerAPI;
import com.fantasygame.base.Presenter;
import com.fantasygame.api.Dependencies;

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

    public void register(String firstname, String lastname, String password, String email,
                         String phone_number, String address, String display_name) {
        final RegisterView view = view();
        if (view != null) {
            view.showLoadingUI();
        }
        subscriptions.add(serverAPI.register(firstname, lastname, password, email, phone_number,
                address, display_name)

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