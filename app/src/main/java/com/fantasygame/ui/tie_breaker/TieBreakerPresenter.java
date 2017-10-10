package com.fantasygame.ui.tie_breaker;

import com.fantasygame.api.ServerAPI;
import com.fantasygame.base.Presenter;
import com.fantasygame.api.Dependencies;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by HP on 17/07/2017.
 */

public class TieBreakerPresenter extends Presenter<TieBreakerView> {

    ServerAPI serverAPI;

    public TieBreakerPresenter() {
        serverAPI = Dependencies.getServerAPI();
    }

    public void getTieBreaker(int id) {
        final TieBreakerView view = view();
        if (view != null) {
            view.showLoadingUI();
        }
        subscriptions.add(serverAPI.getTieBreaker(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (view != null) {
                        try {
                            view.hideLoadingUI();
                            if (response.result)
                                view.showResultGetTieBreaker(response);
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
