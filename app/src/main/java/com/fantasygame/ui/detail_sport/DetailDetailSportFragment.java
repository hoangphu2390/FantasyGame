package com.fantasygame.ui.detail_sport;

import android.os.Bundle;
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
import com.fantasygame.data.model.response.HowToPlayResponse;
import com.fantasygame.listener.ContinueSelectTeamListener;
import com.fantasygame.ui.select_team.SelectTeamFragment;
import com.fantasygame.utils.DialogFactory;
import com.fantasygame.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by HP on 22/06/2017.
 */

public class DetailDetailSportFragment extends BaseFragment implements DetailSportView, HowToPlayAdapter.SelectHowToPlay {

    @Bind(R.id.rv_howtoplay)
    RecyclerView rv_howtoplay;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    List<HowToPlayResponse.Datum> listHowToPlay;
    HowToPlayAdapter howToPlayAdapter;
    DetailSportPresenter presenter;

    int pastVisiblesItems, visibleItemCount, totalItemCount;
    boolean loading = true;
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
        presenter.isLoadMore(false, sport_code);

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
    public void selectedSport(HowToPlayResponse.Datum game) {
        DialogFactory.createHowToPlayDialog(self, game, new ContinueSelectTeamListener() {
            @Override
            public void onContinueSelectTeam() {
                Fragment fragment = new SelectTeamFragment();
                Bundle bundle = new Bundle();
                bundle.putString("game_id", game.id);
                bundle.putString("game_name", game.name);
                bundle.putString("price", game.price);
                bundle.putString("tie_breaker_id", game.tie_breaker_id);
                bundle.putString("congratulation", game.congratulation);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(null)
                        .commit();
            }
        }).show();
    }
}
