package com.fantasygame.ui.sport;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.fantasygame.R;
import com.fantasygame.base.BaseFragment;
import com.fantasygame.data.model.response.FeatureResponse;
import com.fantasygame.data.model.response.SportResponse;
import com.fantasygame.data.model.response.SportResponse.Data;
import com.fantasygame.data.model.response.UserWinnerResponse.Datum;
import com.fantasygame.ui.detail_sport.DetailDetailSportFragment;
import com.fantasygame.utils.CustomViewPager;
import com.fantasygame.utils.Utils;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;


/**
 * Created by HP on 21/06/2017.
 */

public class SportFragment extends BaseFragment implements SportView, SportAdapter.SelectSport {

    final int typeUserWinnerAdapter = 1;
    final int typeFeatureAdapter = 2;

    @Bind(R.id.rvSport)
    RecyclerView rvSport;
    @Bind(R.id.vp_all_winner)
    CustomViewPager vp_all_winner;
    @Bind(R.id.indicator)
    CirclePageIndicator indicator;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    List<Data> sports = new ArrayList<>();
    List<Datum> usersWinner = new ArrayList<>();
    List<FeatureResponse.Datum> featureData = new ArrayList<>();
    AllWinnersAdapter allWinnersAdapter;
    FeatureAdapter featureAdapter;
    SportAdapter sportAdapter;
    SportPresenter presenter;

    int currentPage = 0;
    static boolean _isHandler;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sport;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupAdapter();
        presenter = new SportPresenter();
        presenter.bindView(this);
        presenter.getListSport();
        self.settestMain(self, "SPORT");
        self.hideBack(self);
    }

    private void setupPageAdapter(int typeAdapter) {
        int size = 0;
        if (typeAdapter == typeUserWinnerAdapter) {
            allWinnersAdapter = new AllWinnersAdapter(self.getLayoutInflater(), usersWinner);
            vp_all_winner.setAdapter(allWinnersAdapter);
            size = usersWinner.size();
        } else if (typeAdapter == typeFeatureAdapter) {
            featureAdapter = new FeatureAdapter(self.getLayoutInflater(), featureData);
            vp_all_winner.setAdapter(featureAdapter);
            indicator.setBackgroundColor(Color.TRANSPARENT);
            size = featureData.size();
        }
        vp_all_winner.setCurrentItem(0);
        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(5 * density);
        indicator.setViewPager(vp_all_winner);
        updateChangePage(size);
    }

    private void setupAdapter() {
        LinearLayoutManager llm = new LinearLayoutManager(self, LinearLayoutManager.VERTICAL, false);
        rvSport.setLayoutManager(llm);
        sportAdapter = new SportAdapter(self.getLayoutInflater(), sports, this);
        rvSport.setAdapter(sportAdapter);
    }

    private void updateChangePage(int size) {
        if (!_isHandler) {
            Handler handler = new Handler();
            Runnable update = new Runnable() {
                public void run() {
                    if (currentPage == size) {
                        currentPage = 0;
                    }
                    vp_all_winner.setCurrentItem(currentPage++, true);
                }
            };
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);
                }
            }, 500, 3000);
            _isHandler = true;
        }
    }

    @Override
    public void selectedSport(Data sport) {
        Fragment fragment = new DetailDetailSportFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title_screen", sport.name);
        bundle.putString("sport_code", sport.code);
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
        Utils.showToast(throwable.getMessage());
    }

    @Override
    public void showResultGetListSport(@NonNull SportResponse response) {
        sports = response.data;
        sportAdapter.setDataSource(sports);
        sportAdapter.notifyDataSetChanged();
        // presenter.getAllWinner();
    }

    @Override
    public void showResultGetAllWinner(@NonNull List<Datum> usersWinner) {
        this.usersWinner = usersWinner;
        setupPageAdapter(typeUserWinnerAdapter);
    }

    @Override
    public void showResultGetListFeature(@NonNull List<FeatureResponse.Datum> featureResponse) {
        featureData = featureResponse;
        setupPageAdapter(typeFeatureAdapter);
    }
}
