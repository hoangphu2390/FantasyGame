package com.fantasygame.ui.teams.mvvm;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fantasygame.api.ServerAPI;
import com.fantasygame.data.model.response.FeatureResponse.Datum;
import com.fantasygame.define.Dependencies;
import com.fantasygame.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by HP on 25/07/2017.
 */

public class MVVMTeamsViewModel extends Observable {

    final int limit = 10;
    int page = 1;
    boolean isLoadMore = false;

    public ObservableInt teamsProgress;
    public ObservableInt teamsRecycler;
    public ObservableBoolean teamsRefreshLayout;

    public ObservableField<String> firstName = new ObservableField<>();
    public ObservableField<String> lastName = new ObservableField<>();

    List<Datum> teams;
    Context context;
    Subscription subscription;
    ServerAPI serverAPI;

    public MVVMTeamsViewModel(@NonNull Context context) {
        this.context = context;
        this.teams = new ArrayList<>();
        teamsProgress = new ObservableInt(View.GONE);
        teamsRecycler = new ObservableInt(View.VISIBLE);
        teamsRefreshLayout = new ObservableBoolean(false);
        serverAPI = Dependencies.getServerAPI();
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

    private void getListTeam(String code_sport) {
        teamsProgress.set(View.VISIBLE);

        subscription = serverAPI.getListTeamByCode(page, limit, code_sport)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    teamsProgress.set(View.GONE);
                    teamsRefreshLayout.set(false);
                    if (response.data != null && response.data.size() > 0)
                        changeTeamsDataSet(response.data);
                }, throwable -> {
                    teamsProgress.set(View.GONE);
                    teamsRefreshLayout.set(false);
                });
    }


    private void changeTeamsDataSet(List<Datum> listTeam) {
        teams.addAll(listTeam);
        setChanged();
        notifyObservers(teams);
    }

    private void unSubscribeFromObservable() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public void reset() {
        unSubscribeFromObservable();
        subscription = null;
        context = null;
    }

    public void buttonClicked() {
        Utils.showToast(String.format("Hello %s %s !", firstName.get(), lastName.get()));
    }
}
