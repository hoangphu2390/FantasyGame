package com.fantasygame.ui.result;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.fantasygame.R;
import com.fantasygame.base.BaseFragment;
import com.fantasygame.data.model.response.ResultResponse.Datum;
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

public class TabResultsFragment extends BaseFragment implements ResultView, SwipeRefreshLayout.OnRefreshListener {

    int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Bind(R.id.rv_results)
    RecyclerView rv_results;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.layout_header)
    LinearLayout layout_header;
    @Bind(R.id.line_header)
    View line_header;

    List<Datum> results;
    ResultsAdapter resultsAdapter;
    ResultPresenter presenter;
    String sport_code;

    public static TabResultsFragment newInstance(String sport_code) {
        Bundle args = new Bundle();
        args.putString(Constant.ITEM_SPORT, sport_code);
        TabResultsFragment fragment = new TabResultsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.layout_list_results, container, false);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        sport_code = (String) getArguments().getString(Constant.ITEM_SPORT);
        results = new ArrayList<>();
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
        presenter = new ResultPresenter();
        presenter.bindView(this);
        presenter.isLoadMore(false, sport_code);
    }


    private void setupAdapter() {
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_results.setLayoutManager(llm);
        resultsAdapter = new ResultsAdapter(self.getLayoutInflater());
        rv_results.setAdapter(resultsAdapter);
        rv_results.addOnScrollListener(new SwipeRefreshLayoutToggleScrollListener(refreshLayout));
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(R.color.blue);
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
    public void showResultGetListResult(@NonNull List<Datum> resultResponse) {
        if (refreshLayout != null) {
            refreshLayout.setRefreshing(false);
            results = resultResponse;
            resultsAdapter.setDataSource(results);
            resultsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void hideLayoutHeader() {
        layout_header.setVisibility(View.GONE);
        line_header.setVisibility(View.GONE);
    }

    @Override
    public void showResultGetListSport(@NonNull SportResponse response) {

    }
}

