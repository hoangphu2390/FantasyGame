package com.fantasygame.ui.select_team;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.fantasygame.R;
import com.fantasygame.base.BaseFragment;
import com.fantasygame.data.model.response.SelectTeamResponse;
import com.fantasygame.data.model.response.SelectTeamResponse.Team;
import com.fantasygame.ui.congrat.CongratFragment;
import com.fantasygame.ui.tie_breaker.TieBreakerFragment;
import com.fantasygame.utils.Utils;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by HP on 17/07/2017.
 */

public class Select_4_Team_Fragment extends BaseFragment implements SelectTeamView {

    @Bind(R.id.spn_option_1)
    Spinner spn_option_1;
    @Bind(R.id.spn_option_2)
    Spinner spn_option_2;
    @Bind(R.id.spn_option_3)
    Spinner spn_option_3;
    @Bind(R.id.spn_option_4)
    Spinner spn_option_4;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    List<Team> selected_teams, option_two_teams, option_three_teams, option_one_teams, option_four_teams;
    Team team_one = null, team_two = null, team_three = null, team_four = null;

    SelectTeamAdapter adapter;
    SelectTeamPresenter presenter;
    String game_id, tie_breaker_id;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_select_4_team;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        selected_teams = new ArrayList<>();
        option_two_teams = new ArrayList<>();
        option_three_teams = new ArrayList<>();
        option_one_teams = new ArrayList<>();
        option_four_teams = new ArrayList<>();

        if (getArguments() != null) {
            if (getArguments().containsKey("game_id"))
                game_id = getArguments().getString("game_id");
            if (getArguments().containsKey("tie_breaker_id"))
                tie_breaker_id = getArguments().getString("tie_breaker_id");
        }

        presenter = new SelectTeamPresenter();
        presenter.bindView(this);
        presenter.getListFeature(Integer.valueOf(game_id));

        spn_option_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                team_one = option_one_teams.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spn_option_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                team_two = option_two_teams.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spn_option_3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                team_three = option_three_teams.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spn_option_4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                team_four = option_four_teams.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick(R.id.btnContinue)
    public void clickContinue() {
        if (team_one == null) {
            Utils.showToast("Please select team in option number 1");
            return;
        }
        if (team_two == null) {
            Utils.showToast("Please select team in option number 2");
            return;
        }
        if (team_three == null) {
            Utils.showToast("Please select team in option number 3");
            return;
        }
        if (team_four == null) {
            Utils.showToast("Please select team in option number 4");
            return;
        }
        selected_teams.add(team_one);
        selected_teams.add(team_two);
        selected_teams.add(team_three);
        selected_teams.add(team_four);

        Fragment fragment = new TieBreakerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tie_breaker_id", tie_breaker_id);
        bundle.putParcelable("teams", Parcels.wrap(selected_teams));
        bundle.putString("price", getArguments().getString("price"));
        bundle.putString("game_id", game_id);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(null)
                .commit();
    }

    private void setupAdapter(Spinner spinner, List<Team> teams_option) {
        adapter = new SelectTeamAdapter(self, teams_option);
        spinner.setAdapter(adapter);
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
    public void showResultGetTeamSelect(@NonNull SelectTeamResponse response) {
        if (!response.result || response.data == null || response.data.size() != 4) return;
        option_one_teams = response.data.get(0).teams;
        option_two_teams = response.data.get(1).teams;
        option_three_teams = response.data.get(2).teams;
        option_four_teams = response.data.get(3).teams;

        setupAdapter(spn_option_1, option_one_teams);
        setupAdapter(spn_option_2, option_two_teams);
        setupAdapter(spn_option_3, option_three_teams);
        setupAdapter(spn_option_4, option_four_teams);
    }
}

