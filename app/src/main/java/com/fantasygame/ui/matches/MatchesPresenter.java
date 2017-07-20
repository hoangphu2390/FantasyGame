package com.fantasygame.ui.matches;

import com.fantasygame.api.ServerAPI;
import com.fantasygame.base.Presenter;
import com.fantasygame.define.Dependencies;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by HP on 13/07/2017.
 */

public class MatchesPresenter extends Presenter<MatchesView> {

    final int LIMIT = 5;
    ServerAPI serverAPI;

    public MatchesPresenter() {
        serverAPI = Dependencies.getServerAPI();
    }

    public void getListMatches(String sport_code) {
        final MatchesView view = view();
        if (view != null) {
            view.showLoadingUI();
        }
        subscriptions.add(serverAPI.getListMatches(LIMIT, sport_code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (view != null) {
                        try {
                            view.hideLoadingUI();
                            view.showResultGetMatches(response);
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

    public void getListSport() {
        final MatchesView view = view();
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
}

