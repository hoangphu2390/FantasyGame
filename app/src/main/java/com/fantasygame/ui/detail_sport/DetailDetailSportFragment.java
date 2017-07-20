package com.fantasygame.ui.detail_sport;

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
import com.fantasygame.data.model.response.HowToPlayResponse;
import com.fantasygame.data.model.response.UserWinnerResponse;
import com.fantasygame.data.model.response.UserWinnerResponse.Datum;
import com.fantasygame.listener.ContinueSelectTeamListener;
import com.fantasygame.ui.select_team.Select_3_Team_Fragment;
import com.fantasygame.ui.select_team.Select_4_Team_Fragment;
import com.fantasygame.ui.select_team.Select_5_Team_Fragment;
import com.fantasygame.ui.sport.FeatureAdapter;
import com.fantasygame.ui.sport.AllWinnersAdapter;
import com.fantasygame.utils.CustomViewPager;
import com.fantasygame.utils.DialogFactory;
import com.fantasygame.utils.Utils;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;

/**
 * Created by HP on 22/06/2017.
 */

public class DetailDetailSportFragment extends BaseFragment implements DetailSportView, AllWinnersAdapter.SelectedUserWinner
        , HowToPlayAdapter.SelectHowToPlay {

    final int typeUserWinnerAdapter = 1;
    final int typeFeatureAdapter = 2;

    @Bind(R.id.vp_winner_item_sport)
    CustomViewPager vp_winner_item_sport;
    @Bind(R.id.indicator)
    CirclePageIndicator indicator;
    @Bind(R.id.rv_howtoplay)
    RecyclerView rv_howtoplay;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    List<Datum> usersWinner;
    List<FeatureResponse.Datum> featureData = new ArrayList<>();
    List<HowToPlayResponse.Datum> listHowToPlay;
    WinnerSportAdapter winnerSportAdapter;
    HowToPlayAdapter howToPlayAdapter;
    FeatureAdapter featureAdapter;
    DetailSportPresenter presenter;

    int currentPage = 0, pastVisiblesItems, visibleItemCount, totalItemCount;
    boolean loading = true;
    static boolean _isHandler;
    String title_screen, sport_code;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detail_sport;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            title_screen = getArguments().getString("title_screen");
            sport_code = getArguments().getString("sport_code");
        }
        setupHowToPlayAdapter();

        presenter = new DetailSportPresenter();
        presenter.bindView(this);
        presenter.getAllWinnerOfItemSport(sport_code);

        self.settestMain(self, title_screen);
        self.showBack(self);
    }

    private void setupHowToPlayAdapter() {
        listHowToPlay = new ArrayList<>();
        LinearLayoutManager llm = new LinearLayoutManager(self, LinearLayoutManager.VERTICAL, false);
        rv_howtoplay.setLayoutManager(llm);
        howToPlayAdapter = new HowToPlayAdapter(self.getLayoutInflater(), listHowToPlay, this);
        rv_howtoplay.setAdapter(howToPlayAdapter);

        rv_howtoplay.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = llm.getChildCount();
                    totalItemCount = llm.getItemCount();
                    pastVisiblesItems = llm.findFirstVisibleItemPosition();
                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            presenter.isLoadMore(true, sport_code);
                        }
                    }
                }
            }
        });
    }


    private void updateChangePage(int size) {
        if (!_isHandler) {
            Handler handler = new Handler();
            Runnable update = new Runnable() {
                public void run() {
                    if (currentPage == size) {
                        currentPage = 0;
                    }
                    vp_winner_item_sport.setCurrentItem(currentPage++, true);
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
    public void selectedUser(UserWinnerResponse.Datum userWinner) {

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

    private void setupPageAdapter(int typeAdapter) {
        int size = 0;
        if (typeAdapter == typeUserWinnerAdapter) {
            winnerSportAdapter = new WinnerSportAdapter(self, usersWinner);
            vp_winner_item_sport.setAdapter(winnerSportAdapter);
            size = usersWinner.size();
        } else if (typeAdapter == typeFeatureAdapter) {
            featureAdapter = new FeatureAdapter(self.getLayoutInflater(), featureData);
            vp_winner_item_sport.setAdapter(featureAdapter);
            size = featureData.size();
            indicator.setBackgroundColor(Color.TRANSPARENT);
        }
        vp_winner_item_sport.setCurrentItem(0);
        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(5 * density);
        indicator.setViewPager(vp_winner_item_sport);
        updateChangePage(size);
        presenter.isLoadMore(false, sport_code);
    }

    @Override
    public void showResultGetAllWinner(@NonNull List<Datum> usersWinner) {
        this.usersWinner = usersWinner;
        setupPageAdapter(typeUserWinnerAdapter);
    }

    @Override
    public void showResultGetHowToPlay(@NonNull HowToPlayResponse response, boolean isLoadMore) {
        if (!response.result) return;
        rv_howtoplay.setVisibility(View.VISIBLE);
        if (isLoadMore)
            howToPlayAdapter.appendItems(response.data);
        else {
            howToPlayAdapter.setDataSource(response.data);
        }
        howToPlayAdapter.notifyDataSetChanged();
        loading = true;
    }

    @Override
    public void showResultGetListFeature(@NonNull List<FeatureResponse.Datum> featureResponse) {
        this.featureData = featureResponse;
        setupPageAdapter(typeFeatureAdapter);
    }

    @Override
    public void selectedSport(HowToPlayResponse.Datum game) {
        DialogFactory.createHowToPlayDialog(self, game, new ContinueSelectTeamListener() {
            @Override
            public void onContinueSelectTeam() {

                Fragment fragment;
                int number_option_team = Integer.parseInt(game.number_of_teams);

                if (number_option_team == 3)
                    fragment = new Select_3_Team_Fragment();
                else if (number_option_team == 4)
                    fragment = new Select_4_Team_Fragment();
                else
                    fragment = new Select_5_Team_Fragment();

                Bundle bundle = new Bundle();
                bundle.putString("game_id", game.id);
                bundle.putString("price", game.price);
                bundle.putString("tie_breaker_id", game.tie_breaker_id);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(null)
                        .commit();
            }
        }).show();
    }
}
