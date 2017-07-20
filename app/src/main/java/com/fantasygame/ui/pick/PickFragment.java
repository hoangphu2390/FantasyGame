package com.fantasygame.ui.pick;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.fantasygame.R;
import com.fantasygame.base.BaseFragment;
import com.fantasygame.ui.main.MainActivity;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by HP on 21/06/2017.
 */

public class PickFragment extends BaseFragment {

    final int TYPE_SPORT = 1;
    final int TYPE_STOCK = 2;

    @Bind(R.id.vp_pick)
    ViewPager vp_pick;
    @Bind(R.id.tv_sport)
    TextView tv_sport;
    @Bind(R.id.tv_stock)
    TextView tv_stock;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pick;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity main = new MainActivity();
        main.settestMain(MainActivity.self, "Pick");
        main.hideBack(MainActivity.self);
    }
    @OnClick(R.id.tv_sport)
    public void clickTabSport(){
        selectTabPick(TYPE_SPORT);
    }

    @OnClick(R.id.tv_stock)
    public void clickTabStock(){
        selectTabPick(TYPE_STOCK);
    }

    private void selectTabPick(int type){
        int red = getResources().getColor(R.color.red_orange);
        int black = getResources().getColor(R.color.black);
        tv_sport.setBackgroundColor(type==TYPE_SPORT ? red : black);
        tv_stock.setBackgroundColor(type==TYPE_STOCK ? red : black);
    }


}
