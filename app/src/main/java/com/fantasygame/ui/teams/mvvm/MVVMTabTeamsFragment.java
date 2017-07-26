package com.fantasygame.ui.teams.mvvm;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fantasygame.R;
import com.fantasygame.data.model.response.FeatureResponse.Datum;
import com.fantasygame.databinding.LayoutListTeamsMvvmBinding;
import com.fantasygame.define.Constant;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by HP on 25/07/2017.
 */

public class MVVMTabTeamsFragment extends Fragment implements Observer, SwipeRefreshLayout.OnRefreshListener {

    LayoutListTeamsMvvmBinding binding;
    MVVMTeamsViewModel teamsViewModel;
    MVVMTeamsAdapter adapter;

    String sport_code;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    public static MVVMTabTeamsFragment newInstance(String sport_code) {
        Bundle args = new Bundle();
        args.putString(Constant.ITEM_SPORT, sport_code);
        MVVMTabTeamsFragment fragment = new MVVMTabTeamsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.layout_list_teams_mvvm, container, false);
        teamsViewModel = new MVVMTeamsViewModel(getActivity());
        binding.setMvvmteamsviewmodel(teamsViewModel);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sport_code = (String) getArguments().getString(Constant.ITEM_SPORT);
        setupAdapter(binding.rvTeams);
        setupObserver(teamsViewModel);
        teamsViewModel.isLoadMore(false, sport_code);
    }

    private void setupAdapter(RecyclerView rv_teams) {
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        adapter = new MVVMTeamsAdapter(getActivity().getLayoutInflater());
        rv_teams.setAdapter(adapter);
        rv_teams.setLayoutManager(llm);
        rv_teams.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = llm.getChildCount();
                    totalItemCount = llm.getItemCount();
                    pastVisiblesItems = llm.findFirstVisibleItemPosition();
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        teamsViewModel.isLoadMore(true, sport_code);
                    }
                }
            }
        });
    }

    private void setupObserver(Observable observable) {
        observable.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object arg) {
        adapter.setDataSource((List<Datum>) arg);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        teamsViewModel.reset();
    }

    @Override
    public void onRefresh() {
        teamsViewModel.isLoadMore(false, sport_code);
    }
}
