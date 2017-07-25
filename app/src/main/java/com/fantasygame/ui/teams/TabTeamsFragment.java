package com.fantasygame.ui.teams;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.fantasygame.R;
import com.fantasygame.base.BaseFragment;
import com.fantasygame.data.model.response.FeatureResponse;
import com.fantasygame.data.model.response.FeatureResponse.Datum;
import com.fantasygame.data.model.response.SportResponse;
import com.fantasygame.define.Constant;
import com.fantasygame.utils.SwipeRefreshLayoutToggleScrollListener;
import com.fantasygame.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by HP on 24/07/2017.
 */

public class TabTeamsFragment extends BaseFragment implements TeamsView, SwipeRefreshLayout.OnRefreshListener,
        TeamsAdapter.SelectTeams {

    int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Bind(R.id.rv_teams)
    RecyclerView rv_teams;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    List<Datum> teams;
    TeamsAdapter teamsAdapter;
    TeamsPresenter presenter;
    String sport_code;

    public static TabTeamsFragment newInstance(String sport_code) {
        Bundle args = new Bundle();
        args.putString(Constant.ITEM_SPORT, sport_code);
        TabTeamsFragment fragment = new TabTeamsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.layout_list_teams, container, false);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        sport_code = (String) getArguments().getString(Constant.ITEM_SPORT);
        teams = new ArrayList<>();
        setupPresenter();
        setupAdapter();
    }

    @Override
    public void hideLoadingUI() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingUI() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorLoadingUI(@NonNull Throwable throwable) {
        Utils.showToast(throwable.getMessage());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    private void setupPresenter() {
        presenter = new TeamsPresenter();
        presenter.bindView(this);
        presenter.isLoadMore(false, sport_code);
    }


    private void setupAdapter() {
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_teams.setLayoutManager(llm);
        teamsAdapter = new TeamsAdapter(self.getLayoutInflater(), this);
        rv_teams.setAdapter(teamsAdapter);
        rv_teams.addOnScrollListener(new SwipeRefreshLayoutToggleScrollListener(refreshLayout));
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(R.color.blue);

        rv_teams.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = llm.getChildCount();
                    totalItemCount = llm.getItemCount();
                    pastVisiblesItems = llm.findFirstVisibleItemPosition();
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        presenter.isLoadMore(true, sport_code);
                    }
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        presenter.isLoadMore(false, sport_code);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (refreshLayout != null) {
            refreshLayout.setRefreshing(false);
            refreshLayout.destroyDrawingCache();
            refreshLayout.clearAnimation();
        }
    }

    @Override
    public void showResultGetListFeature(@NonNull List<Datum> featureResponse) {
        if (refreshLayout != null) {
            refreshLayout.setRefreshing(false);
            teams.addAll(featureResponse);
            teamsAdapter.setDataSource(teams);
            teamsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showResultGetListSport(@NonNull SportResponse response) {

    }

    @Override
    public void selectTeams(FeatureResponse.Datum teams) {

    }
}

