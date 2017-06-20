package com.fantasygame.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fantasygame.R;
import com.fantasygame.base.BaseFragment;
import com.fantasygame.data.model.response.TeamResponse;
import com.fantasygame.data.model.response.TeamResponse.Team;
import com.fantasygame.ui.main.MainActivity;
import com.fantasygame.utils.Utils;

import java.util.List;

import butterknife.Bind;

/**
 * Created by HP on 18/06/2017.
 */

public class HomeFragment extends BaseFragment implements HomeView, HomeAdapter.SelectedItem {

    @Bind(R.id.rvTeam)
    RecyclerView rvTeam;

    HomeAdapter adapter;
    HomePresenter presenter;

    List<Team> teams;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    boolean loading = true;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new HomePresenter();
        presenter.bindView(this);
        setupAdapter();
        presenter.isLoadMore(false);

        MainActivity main = new MainActivity();
        main.settestMain(getActivity(), "Home");
        main.hideBack(getActivity());
    }

    @Override
    public void hideLoadingUI() {
        hideProgressDialog();
    }

    @Override
    public void showLoadingUI() {
        loadingProgressDialog();
    }

    @Override
    public void showErrorLoadingUI(@NonNull Throwable throwable) {
        Utils.showToast(throwable.getMessage());
    }

    @Override
    public void showResultGetListTeam(TeamResponse response, boolean isLoadMore) {
        rvTeam.setVisibility(View.VISIBLE);
        if (isLoadMore)
            adapter.appendItems(response.data);
        else {
            adapter.setDataSource(response.data);
        }
        adapter.notifyDataSetChanged();
        loading = true;
    }

    private void setupAdapter() {
        LinearLayoutManager llm = new LinearLayoutManager(MainActivity.self, LinearLayoutManager.VERTICAL, false);
        rvTeam.setLayoutManager(llm);
        adapter = new HomeAdapter(MainActivity.self.getLayoutInflater(), teams, this);
        rvTeam.setAdapter(adapter);

        rvTeam.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = llm.getChildCount();
                    totalItemCount = llm.getItemCount();
                    pastVisiblesItems = llm.findFirstVisibleItemPosition();
                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            presenter.isLoadMore(true);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void selectedTeam(Team team) {
        Utils.showToast(team.name);
    }
}
