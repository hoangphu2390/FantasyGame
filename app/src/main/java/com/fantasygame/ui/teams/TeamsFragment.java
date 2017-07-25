package com.fantasygame.ui.teams;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fantasygame.R;
import com.fantasygame.base.BaseFragment;
import com.fantasygame.data.model.response.FeatureResponse;
import com.fantasygame.data.model.response.SportResponse;
import com.fantasygame.listener.SimpleTabSelectedListener;
import com.fantasygame.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by HP on 24/07/2017.
 */

public class TeamsFragment extends BaseFragment implements TeamsView {

    @Bind(R.id.vp_teams)
    ViewPager vp_teams;
    @Bind(R.id.tab_teams)
    TabLayout tab_teams;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    TeamsPagerAdapter adapter;
    List<String> NameSports, CodeSports;
    TeamsPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_teams;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUI();
        presenter = new TeamsPresenter();
        presenter.bindView(this);
        presenter.getListSport();

        self.settestMain(self, "TEAMS");
        self.showBack(self);
    }

    public void setupUI() {
        vp_teams.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_teams));
        adapter = new TeamsPagerAdapter(self.getSupportFragmentManager());
        vp_teams.setAdapter(adapter);
        tab_teams.setupWithViewPager(vp_teams);
        vp_teams.setCurrentItem(0);
        tab_teams.setOnTabSelectedListener(new SimpleTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp_teams.setCurrentItem(tab.getPosition());
            }
        });
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

    @Override
    public void showResultGetListFeature(@NonNull List<FeatureResponse.Datum> featureResponse) {

    }

    @Override
    public void showResultGetListSport(@NonNull SportResponse response) {
        if(!response.result) return;
        NameSports = new ArrayList<>();
        CodeSports = new ArrayList<>();
        List<SportResponse.Data> listData = response.data;
        for(SportResponse.Data data : listData){
            NameSports.add(data.name);
            CodeSports.add(data.code);
        }
        adapter.setTabMenu(CodeSports, NameSports);
        for (int i = 0; i < NameSports.size(); i++) {
            TextView textView = (TextView) self.getLayoutInflater().inflate(R.layout.tab_text, null);
            textView.setText(NameSports.get(i));
            textView.setSelected(i == vp_teams.getCurrentItem());
        }
    }
}

