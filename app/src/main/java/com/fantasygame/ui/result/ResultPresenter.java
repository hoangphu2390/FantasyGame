package com.fantasygame.ui.result;

import com.fantasygame.api.ServerAPI;
import com.fantasygame.base.Presenter;
import com.fantasygame.define.Dependencies;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by HP on 24/07/2017.
 */

public class ResultPresenter extends Presenter<ResultView> {

    ServerAPI serverAPI;
    final int limit = 10;
    int page = 1;
    boolean isLoadMore = false;

    public ResultPresenter() {
        serverAPI = Dependencies.getServerAPI();
    }

    public void getListResult(String code_sport) {
        final ResultView view = view();
        if (view != null) {
            view.showLoadingUI();
        }
        subscriptions.add(serverAPI.getListResults(code_sport)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (view != null) {
                        try {
                            view.hideLoadingUI();
                            if (response.result)
                                view.showResultGetListResult(response.data);
                            else
                                view.hideLayoutHeader();
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
        final ResultView view = view();
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
        getListResult(code_sport);
    }
}
