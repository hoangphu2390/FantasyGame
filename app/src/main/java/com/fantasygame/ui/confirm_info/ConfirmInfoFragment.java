package com.fantasygame.ui.confirm_info;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fantasygame.R;
import com.fantasygame.base.BaseFragment;
import com.fantasygame.data.model.response.FinishTeamResponse;
import com.fantasygame.data.model.response.SelectTeamResponse.Team;
import com.fantasygame.ui.congrat.CongratFragment;
import com.fantasygame.utils.PreferenceUtils;
import com.fantasygame.utils.Utils;

import org.parceler.Parcels;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by HP on 20/07/2017.
 */

public class ConfirmInfoFragment extends BaseFragment implements ConfirmInfoView {

    final int MAX_NUMBER_TEAM = 5;

    @Bind(R.id.rv_teams)
    RecyclerView rv_teams;
    @Bind(R.id.tv_tie_breaker)
    TextView tv_tie_breaker;
    @Bind(R.id.tv_price)
    TextView tv_price;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    ConfirmTeamAdapter adapter;
    List<Team> teams;
    String content_tie_breaker, price, game_id;
    ConfirmInfoPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_confirm_info;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null && getArguments().containsKey("teams")) {
            Parcelable parcelable = getArguments().getParcelable("teams");
            teams = (List<Team>) Parcels.unwrap(parcelable);
        }
        if (getArguments() != null && getArguments().containsKey("tie_breaker")) {
            content_tie_breaker = getArguments().getString("tie_breaker");
        }
        if (getArguments() != null && getArguments().containsKey("price")) {
            price = getArguments().getString("price");
        }
        if (getArguments() != null && getArguments().containsKey("game_id")) {
            game_id = getArguments().getString("game_id");
        }
        setupUI();
        presenter = new ConfirmInfoPresenter();
        presenter.bindView(this);
    }

    private void setupUI() {
        setupAdapter();
        tv_tie_breaker.setText(content_tie_breaker);
        tv_price.setText("$" + price);
    }

    private void setupAdapter() {
        LinearLayoutManager llm = new LinearLayoutManager(self, LinearLayoutManager.VERTICAL, false);
        rv_teams.setLayoutManager(llm);
        adapter = new ConfirmTeamAdapter(self.getLayoutInflater());
        rv_teams.setAdapter(adapter);
        adapter.setDataSource(teams);
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btnFinish)
    public void clickFinish() {
        int add_number_team = MAX_NUMBER_TEAM - teams.size();
        if (add_number_team > 0) addTeams(add_number_team);
        String api_token = PreferenceUtils.getFromPrefs(self, PreferenceUtils.PREFS_ApiToken, "");
        presenter.betTeams(game_id, content_tie_breaker, teams, api_token);
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
        progressBar.setVisibility(View.GONE);
        Utils.showToast(throwable.getMessage());
    }

    @Override
    public void showResultPostSelectTeams(FinishTeamResponse response) {
        if (response.result) {
            Fragment fragment = new CongratFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(null)
                    .commit();
        }
    }

    private void addTeams(int add_number_team) {
        for (int i = 0; i < add_number_team; i++) {
            Team team = new Team();
            team.code = "";
            teams.add(team);
        }
    }
}
