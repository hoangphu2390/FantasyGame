package com.fantasygame.ui.result;

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
import com.fantasygame.data.model.response.ResultResponse;
import com.fantasygame.data.model.response.SportResponse;
import com.fantasygame.listener.SimpleTabSelectedListener;
import com.fantasygame.ui.teams.TeamsPagerAdapter;
import com.fantasygame.ui.teams.TeamsPresenter;
import com.fantasygame.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by HP on 24/07/2017.
 */

public class ResultFragment extends BaseFragment implements ResultView {

    @Bind(R.id.vp_result)
    ViewPager vp_result;
    @Bind(R.id.tab_result)
    TabLayout tab_result;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    ResultsPagerAdapter adapter;
    List<String> NameSports, CodeSports;
    ResultPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_result;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUI();
        presenter = new ResultPresenter();
        presenter.bindView(this);
        presenter.getListSport();

        self.settestMain(self, "RESULTS");
        self.showBack(self);
    }

    public void setupUI() {
        vp_result.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_result));
        adapter = new ResultsPagerAdapter(self.getSupportFragmentManager());
        vp_result.setAdapter(adapter);
        tab_result.setupWithViewPager(vp_result);
        vp_result.setCurrentItem(0);
        tab_result.setOnTabSelectedListener(new SimpleTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp_result.setCurrentItem(tab.getPosition());
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
    public void showResultGetListSport(@NonNull SportResponse response) {
        if (!response.result) return;
        NameSports = new ArrayList<>();
        CodeSports = new ArrayList<>();
        List<SportResponse.Data> listData = response.data;
        for (SportResponse.Data data : listData) {
            NameSports.add(data.name);
            CodeSports.add(data.code);
        }
        adapter.setTabMenu(CodeSports, NameSports);
        for (int i = 0; i < NameSports.size(); i++) {
            TextView textView = (TextView) self.getLayoutInflater().inflate(R.layout.tab_text, null);
            textView.setText(NameSports.get(i));
            textView.setSelected(i == vp_result.getCurrentItem());
        }
    }

    @Override
    public void showResultGetListResult(@NonNull List<ResultResponse.Datum> resultResponse) {

    }

    @Override
    public void hideLayoutHeader() {

    }
}

