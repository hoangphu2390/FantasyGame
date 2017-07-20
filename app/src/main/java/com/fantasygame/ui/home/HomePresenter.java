package com.fantasygame.ui.home;

import com.fantasygame.api.ServerAPI;
import com.fantasygame.base.Presenter;
import com.fantasygame.define.Dependencies;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by HP on 20/06/2017.
 */

public class HomePresenter extends Presenter<HomeView> {

    final int LIMIT_PAGE = 20;

    ServerAPI serverAPI;
    int currentPage = 1;
    boolean isLoadMore;

    public HomePresenter() {
        serverAPI = Dependencies.getServerAPI();
    }

    public void getListTeam(int page, int limit) {
        final HomeView view = view();
        if (view != null) {
            view.showLoadingUI();
        }
        subscriptions.add(serverAPI.getListTeam(page, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (view != null) {
                        try {
                            view.hideLoadingUI();
                            view.showResultGetListTeam(response, isLoadMore);
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

    public void isLoadMore(boolean isLoadMore) {
        if (!isLoadMore) {
            currentPage = 1;
        } else {
            ++currentPage;
        }
        this.isLoadMore = isLoadMore;
        getListTeam(currentPage, LIMIT_PAGE);
    }
}
