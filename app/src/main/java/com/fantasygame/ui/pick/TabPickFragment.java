package com.fantasygame.ui.pick;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fantasygame.R;
import com.fantasygame.base.BaseFragment;
import com.fantasygame.data.model.response.SportResponse;
import com.fantasygame.data.model.response.SportResponse.Sport;
import com.fantasygame.define.Constant;
import com.fantasygame.listener.EventBusListener;
import com.fantasygame.ui.main.MainActivity;
import com.fantasygame.ui.pick.sport.SportAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by HP on 14/02/2017.
 */

public class TabPickFragment extends BaseFragment implements PickView, SportAdapter.SelectTeam {

    @Bind(R.id.rvPick)
    RecyclerView rvPick;

    List<Sport> sports;

    EventBus eventBus;
    PickPresenter presenter;
    SportAdapter sportAdapter;
    int position;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab_pick;
    }

    public static TabPickFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(Constant.POSITION, position);
        TabPickFragment fragment = new TabPickFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        position = (int) getArguments().getInt(Constant.POSITION);
        eventBus = EventBusListener.getInstance();
        presenter = new PickPresenter();
        presenter.bindView(this);
        setupAdapter();
    }

    private void setupAdapter() {
        LinearLayoutManager llm = new LinearLayoutManager(MainActivity.self, LinearLayoutManager.VERTICAL, false);
        rvPick.setLayoutManager(llm);
        if (position == Constant.TAB_SPORT) {
            sportAdapter = new SportAdapter(getActivity().getLayoutInflater(), sports ,this);
            rvPick.setAdapter(sportAdapter);
        } else if (position == Constant.TAB_STOCK) {

        }
    }

    @Override
    public void hideLoadingUI() {

    }

    @Override
    public void showLoadingUI() {

    }

    @Override
    public void showErrorLoadingUI(@NonNull Throwable throwable) {

    }

    @Override
    public void showResultGetListSport(@NonNull SportResponse response) {

    }

    @Override
    public void selectedTeam(SportResponse.Sport sport) {

    }
}
