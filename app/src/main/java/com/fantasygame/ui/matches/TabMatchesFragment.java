package com.fantasygame.ui.matches;

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
import android.widget.Toast;

import com.fantasygame.R;
import com.fantasygame.base.BaseFragment;
import com.fantasygame.data.model.response.MatchesResponse;
import com.fantasygame.data.model.response.MatchesResponse.Datum;
import com.fantasygame.data.model.response.SportResponse;
import com.fantasygame.define.Constant;
import com.fantasygame.utils.SwipeRefreshLayoutToggleScrollListener;
import com.fantasygame.utils.Utils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by HP on 13/07/2017.
 */

public class TabMatchesFragment extends BaseFragment implements MatchesView, SwipeRefreshLayout.OnRefreshListener,
        MatchesAdapter.SelectMatches {

    @Bind(R.id.rv_matches)
    RecyclerView rv_matches;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;


    List<Datum> matches;
    MatchesAdapter matchesAdapter;
    MatchesPresenter presenter;
    String sport_code;

    public static TabMatchesFragment newInstance(String sport_code) {
        Bundle args = new Bundle();
        args.putString(Constant.ITEM_SPORT, sport_code);
        TabMatchesFragment fragment = new TabMatchesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.layout_list_matches, container, false);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        sport_code = (String) getArguments().getString(Constant.ITEM_SPORT);
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
        presenter = new MatchesPresenter();
        presenter.bindView(this);
        presenter.getListMatches(sport_code);
    }


    private void setupAdapter() {
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_matches.setLayoutManager(llm);
        matchesAdapter = new MatchesAdapter(self.getLayoutInflater(), this);
        rv_matches.setAdapter(matchesAdapter);
        rv_matches.addOnScrollListener(new SwipeRefreshLayoutToggleScrollListener(refreshLayout));
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(R.color.blue);
    }

    @Override
    public void onRefresh() {
        presenter.getListMatches(sport_code);
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
    public void showResultGetMatches(MatchesResponse response) {
        Activity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(() -> {
                if (refreshLayout != null) {
                    refreshLayout.setRefreshing(false);
                    matches = response.data;
                    matchesAdapter.setDataSource(matches);
                    matchesAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public void showResultGetListSport(@NonNull SportResponse response) {

    }

    @Override
    public void selectMatches(Datum matches) {

    }
}
