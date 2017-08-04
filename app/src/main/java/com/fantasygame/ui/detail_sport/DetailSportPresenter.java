package com.fantasygame.ui.detail_sport;

import com.fantasygame.api.ServerAPI;
import com.fantasygame.base.Presenter;
import com.fantasygame.define.Dependencies;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by HP on 21/06/2017.
 */

public class DetailSportPresenter extends Presenter<DetailSportView> {

    final int LIMIT_PAGE = 10, STATUS = 1, page = 1, limit = 10;

    int currentPage = 1;
    boolean isLoadMore;

    ServerAPI serverAPI;

    public DetailSportPresenter() {
        serverAPI = Dependencies.getServerAPI();
    }

    public void getHowToPlay(int page, int limit, String code_sport) {
        final DetailSportView view = view();
        if (view != null) {
            view.showLoadingUI();
        }
        subscriptions.add(serverAPI.getHowToPlay(page, limit, code_sport)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (view != null) {
                        try {
                            view.hideLoadingUI();
                            view.showResultGetHowToPlay(response, isLoadMore);
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
            currentPage = 1;
        } else {
            ++currentPage;
        }
        this.isLoadMore = isLoadMore;
        getHowToPlay(currentPage, LIMIT_PAGE, code_sport);
    }
}

