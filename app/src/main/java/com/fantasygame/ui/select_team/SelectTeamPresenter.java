package com.fantasygame.ui.select_team;

import com.fantasygame.api.ServerAPI;
import com.fantasygame.base.Presenter;
import com.fantasygame.api.Dependencies;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by HP on 17/07/2017.
 */

public class SelectTeamPresenter extends Presenter<SelectTeamView> {

    ServerAPI serverAPI;

    public SelectTeamPresenter() {
        serverAPI = Dependencies.getServerAPI();
    }

    public void getListFeature(int game_id) {
        final SelectTeamView view = view();
        if (view != null) {
            view.showLoadingUI();
        }
        subscriptions.add(serverAPI.getListTeamSelect(game_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (view != null) {
                        try {
                            view.hideLoadingUI();
                            if (response.result)
                                view.showResultGetTeamSelect(response);
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
