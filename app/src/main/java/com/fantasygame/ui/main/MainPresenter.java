package com.fantasygame.ui.main;

import com.fantasygame.api.ServerAPI;
import com.fantasygame.base.Presenter;
import com.fantasygame.define.Dependencies;
<<<<<<< HEAD
=======
import com.fantasygame.ui.home.HomeView;
>>>>>>> ad8485e904013f72180e461e82a80c8da759f7cd

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

<<<<<<< HEAD
    public void logout(String api_token) {
=======
    public void logout() {
>>>>>>> ad8485e904013f72180e461e82a80c8da759f7cd
        final MainView view = view();
        if (view != null) {
            view.showLoadingUI();
        }
<<<<<<< HEAD
        subscriptions.add(serverAPI.logout(api_token)
=======
        subscriptions.add(serverAPI.logout()
>>>>>>> ad8485e904013f72180e461e82a80c8da759f7cd
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

