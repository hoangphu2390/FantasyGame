package com.fantasygame.ui.profile;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fantasygame.R;
import com.fantasygame.base.BaseFragment;
import com.fantasygame.data.model.response.ProfileGameResponse.Datum;
import com.fantasygame.define.Constant;
import com.fantasygame.ui.payment.PaymentFragment;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by HP on 14/02/2017.
 */

public class TabGameFragment extends BaseFragment implements GamePlayAdapter.PlayNowListener {

    final String GAME_STATUS_DONE = "1";
    final String GAME_STATUS_PLAY = "0";

    @Bind(R.id.tv_game_done)
    TextView tv_game_done;
    @Bind(R.id.tv_game)
    TextView tv_game;

    @Bind(R.id.rv_game_done)
    RecyclerView rv_game_done;
    @Bind(R.id.rv_game)
    RecyclerView rv_game;

    GamePlayAdapter adapter, adapter_done;
    int position;
    List<Datum> listGame, listGameDone;

    public static TabGameFragment newInstance(int position, List<Datum> listGame) {
        Bundle args = new Bundle();
        args.putInt(Constant.POSITION, position);
        args.putParcelable(Constant.GAMES, Parcels.wrap(listGame));
        TabGameFragment fragment = new TabGameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.layout_tab_game, container, false);
        return fragmentView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        showData();
    }

    private void filterStatusGames() {
        for (Datum game : listGame) {
            if (game.status.equals(GAME_STATUS_DONE)) {
                listGameDone.add(game);
                listGame.remove(game);
            }
        }
    }

    private void setupGameAdapter(RecyclerView rv, GamePlayAdapter adapter, List<Datum> games) {
        LinearLayoutManager llm = new LinearLayoutManager(self, LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(llm);
        adapter = new GamePlayAdapter(self.getLayoutInflater(), this);
        rv.setAdapter(adapter);
        adapter.setDataSource(games);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPlayNow(Datum game) {
        Fragment fragment = new PaymentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("game_id", game.id);
        bundle.putString("game_name", game.name);
        bundle.putString("price", game.price);
        bundle.putString("tie_breaker_id", game.tie_breaker_id);
        bundle.putString("congratulation", game.congratulation);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment)
                .commit();
    }

    private void showData() {
        listGame = new ArrayList<>();
        listGameDone = new ArrayList<>();

        position = (int) getArguments().getInt(Constant.POSITION);
        Parcelable parcelable = getArguments().getParcelable(Constant.GAMES);
        listGame = (List<Datum>) Parcels.unwrap(parcelable);
        filterStatusGames();
        if (listGameDone.size() == 0) {
            tv_game_done.setVisibility(View.GONE);
            rv_game_done.setVisibility(View.GONE);
        } else
            setupGameAdapter(rv_game_done, adapter_done, listGameDone);

        if (listGame.size() == 0) {
            tv_game.setVisibility(View.GONE);
            rv_game.setVisibility(View.GONE);
        } else
            setupGameAdapter(rv_game, adapter, listGame);
    }
}
