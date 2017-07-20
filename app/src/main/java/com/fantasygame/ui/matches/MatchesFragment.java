package com.fantasygame.ui.matches;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fantasygame.R;
import com.fantasygame.base.BaseFragment;
import com.fantasygame.data.model.response.MatchesResponse;
import com.fantasygame.data.model.response.SportResponse;
import com.fantasygame.data.model.response.SportResponse.Data;
import com.fantasygame.listener.SimpleTabSelectedListener;
import com.fantasygame.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by HP on 13/07/2017.
 */

public class MatchesFragment extends BaseFragment implements MatchesView{

    @Bind(R.id.vp_matches)
    ViewPager vp_matches;
    @Bind(R.id.tab_matches)
    TabLayout tab_matches;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    MatchesPagerAdapter adapter;
    List<String> NameSports, CodeSports;
    MatchesPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_matches;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUI();
        presenter = new MatchesPresenter();
        presenter.bindView(this);
        presenter.getListSport();

        self.settestMain(self, "MATCHES");
        self.showBack(self);
    }

    public void setupUI() {
        vp_matches.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_matches));
        adapter = new MatchesPagerAdapter(self.getSupportFragmentManager());
        vp_matches.setAdapter(adapter);
        tab_matches.setupWithViewPager(vp_matches);
        vp_matches.setCurrentItem(0);
        tab_matches.setOnTabSelectedListener(new SimpleTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp_matches.setCurrentItem(tab.getPosition());
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
    public void showResultGetMatches(@NonNull MatchesResponse response) {

    }

    @Override
    public void showResultGetListSport(@NonNull SportResponse response) {
        if(!response.result) return;
        NameSports = new ArrayList<>();
        CodeSports = new ArrayList<>();
        List<Data> listData = response.data;
        for(Data data : listData){
            NameSports.add(data.name);
            CodeSports.add(data.code);
        }
        adapter.setTabMenu(CodeSports, NameSports);
        for (int i = 0; i < NameSports.size(); i++) {
            TextView textView = (TextView) self.getLayoutInflater().inflate(R.layout.tab_text, null);
            textView.setText(NameSports.get(i));
            textView.setSelected(i == vp_matches.getCurrentItem());
        }
    }
}
