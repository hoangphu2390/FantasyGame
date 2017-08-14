package com.fantasygame.ui.select_team;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.fantasygame.R;
import com.fantasygame.base.BaseFragment;
import com.fantasygame.data.model.response.SelectTeamResponse;
import com.fantasygame.data.model.response.SelectTeamResponse.Team;
import com.fantasygame.ui.tie_breaker.TieBreakerFragment;
import com.fantasygame.utils.Utils;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by HP on 20/07/2017.
 */

public class SelectTeamFragment extends BaseFragment implements SelectTeamView {

    @Bind(R.id.layout_select_team)
    LinearLayout layout_select_team;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    List<Team> selected_teams;
    List<Spinner> listSpinner;
    List<List<Team>> option_teams;
    TreeMap<String, Team> mapTeams;
    int num_option = 0;

    @Inject
    SelectTeamAdapter adapter;
    SelectTeamPresenter presenter;
    String game_id, tie_breaker_id, game_name;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_select_team;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        selected_teams = new ArrayList<>();
        option_teams = new ArrayList<>();
        mapTeams = new TreeMap<>();
        listSpinner = new ArrayList<>();

        if (getArguments() != null) {
            if (getArguments().containsKey("game_id"))
                game_id = getArguments().getString("game_id");
            if (getArguments().containsKey("tie_breaker_id"))
                tie_breaker_id = getArguments().getString("tie_breaker_id");
            if (getArguments().containsKey("game_name"))
                game_name = getArguments().getString("game_name");
        }

        presenter = new SelectTeamPresenter();
        presenter.bindView(this);
        presenter.getListFeature(Integer.valueOf(game_id));

        self.settestMain(self, game_name);
        self.showBack(self);
    }

    @OnClick(R.id.btnContinue)
    public void clickContinue() {
        for (Map.Entry<String, Team> entry : mapTeams.entrySet()) {
            selected_teams.add(entry.getValue());
        }

        Fragment fragment = new TieBreakerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tie_breaker_id", tie_breaker_id);
        bundle.putParcelable("teams", Parcels.wrap(selected_teams));
        bundle.putString("price", getArguments().getString("price"));
        bundle.putString("game_id", game_id);
        bundle.putString("congratulation", getArguments().getString("congratulation"));
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(null)
                .commit();
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
        if (!response.result || response.data == null || response.data.size() == 0) return;
        num_option = response.data.size();

        for (int i = 0; i < num_option; i++) {
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lparams.setMargins(5, 30, 0, 5);
            lparams.gravity = Gravity.CENTER_VERTICAL;
            TextView tv = new TextView(self);
            tv.setLayoutParams(lparams);
            tv.setText("Select team :");
            tv.setTextSize(15);
            tv.setTextColor(Color.WHITE);
            layout_select_team.addView(tv);

            Spinner spinner = new Spinner(self);
            lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 170);
            lparams.setMargins(5, 10, 0, 0);
            spinner.setId(i);
            spinner.setLayoutParams(lparams);
            spinner.setBackgroundResource(R.drawable.bg_spn_black_border);
            layout_select_team.addView(spinner);

            List<Team> teams = response.data.get(i).teams;
            option_teams.add(teams);
            adapter = new SelectTeamAdapter(self, teams);
            spinner.setAdapter(adapter);
            listSpinner.add(spinner);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int index = spinner.getId();
                    mapTeams.put(String.valueOf(index), option_teams.get(index).get(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    @OnClick(R.id.btnRandom)
    public void clickRandom() {
        for (int i = 0; i < num_option; i++) {
            Spinner spn = listSpinner.get(i);
            int position = randInt(0, spn.getCount());
            if (position < spn.getCount())
                listSpinner.get(i).setSelection(position);
        }
    }

    private int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
