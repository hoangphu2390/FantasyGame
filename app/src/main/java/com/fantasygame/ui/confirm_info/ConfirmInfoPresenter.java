package com.fantasygame.ui.confirm_info;

import com.fantasygame.api.ServerAPI;
import com.fantasygame.base.Presenter;
import com.fantasygame.api.Dependencies;

import java.util.TreeMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by HP on 20/07/2017.
 */

public class ConfirmInfoPresenter extends Presenter<ConfirmInfoView> {
    ServerAPI serverAPI;

    public ConfirmInfoPresenter() {
        serverAPI = Dependencies.getServerAPI();
    }


    public void betTeams(String game_id, String tie_breaker, String api_token, String transaction_id,
                         String card_num, String access_code, TreeMap<String, String> selected_teams) {
        final ConfirmInfoView view = view();
        if (view != null) {
            view.showLoadingUI();
        }
        subscriptions.add(serverAPI.betGameTeams(game_id, tie_breaker, api_token, transaction_id,
                card_num, access_code, selected_teams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (view != null) {
                        try {
                            view.hideLoadingUI();
                            view.showResultPostSelectTeams(response);
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
