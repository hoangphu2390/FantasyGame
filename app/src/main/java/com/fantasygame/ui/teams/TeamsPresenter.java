package com.fantasygame.ui.teams;

import com.fantasygame.api.ServerAPI;
import com.fantasygame.base.Presenter;
import com.fantasygame.api.Dependencies;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by HP on 24/07/2017.
 */

public class TeamsPresenter extends Presenter<TeamsView> {

    ServerAPI serverAPI;
    final int limit = 10;
    int page = 1;
    boolean isLoadMore = false;

    public TeamsPresenter() {
        serverAPI = Dependencies.getServerAPI();
    }

    public void getListTeam(String code_sport) {
        final TeamsView view = view();
        if (view != null) {
            view.showLoadingUI();
        }
        subscriptions.add(serverAPI.getListTeamByCode(page, limit, code_sport)
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

    public void getListSport() {
        final TeamsView view = view();
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

    public void isLoadMore(boolean isLoadMore, String code_sport) {
        if (!isLoadMore) {
            page = 1;
        } else {
            ++page;
        }
        this.isLoadMore = isLoadMore;
        getListTeam(code_sport);
    }
}
