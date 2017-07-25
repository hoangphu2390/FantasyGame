package com.fantasygame.ui.confirm_info;

import com.fantasygame.api.ServerAPI;
import com.fantasygame.base.Presenter;
import com.fantasygame.data.model.response.SelectTeamResponse.Team;
import com.fantasygame.define.Dependencies;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public void betTeams(String game_id, String tie_breaker, List<Team> teams, String api_token) {
        final ConfirmInfoView view = view();
        if (view != null) {
            view.showLoadingUI();
        }
        subscriptions.add(serverAPI.betGameTeams(game_id, tie_breaker,
                teams.get(0).code,
                teams.get(1).code,
                teams.get(2).code,
                teams.get(3).code,
                teams.get(4).code, api_token)
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

    public void betTeams(String game_id, String tie_breaker, String api_token, TreeMap<String, String> selected_teams) {
        final ConfirmInfoView view = view();
        if (view != null) {
            view.showLoadingUI();
        }
        subscriptions.add(serverAPI.betGameTeams(game_id, tie_breaker, api_token, selected_teams)
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
