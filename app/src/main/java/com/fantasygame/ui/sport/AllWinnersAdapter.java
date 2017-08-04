package com.fantasygame.ui.sport;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.fantasygame.R;
import com.fantasygame.data.model.response.UserWinnerResponse.Datum;
import com.fantasygame.define.Constant;
import com.fantasygame.utils.Utils;

import java.util.List;

/**
 * Created by HP on 20/06/2017.
 */

public class AllWinnersAdapter extends PagerAdapter {

    List<Datum> userWinners;
    LayoutInflater inflater;

    public AllWinnersAdapter(LayoutInflater inflater, List<Datum> userWinners) {
        this.inflater = inflater;
        this.userWinners = userWinners;
    }


    @Override
    public int getCount() {
        return userWinners.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View layout = inflater.inflate(R.layout.item_user_winner, view, false);
        TextView tv_user_winner_name = (TextView) layout.findViewById(R.id.tv_user_winner_name);
        TextView tv_user_winner_description = (TextView) layout.findViewById(R.id.tv_user_winner_description);
        ImageView iv_user_winner = (ImageView) layout.findViewById(R.id.iv_user_winner);

        Datum userWinner = userWinners.get(position);
        tv_user_winner_name.setText(userWinner.player);
        tv_user_winner_description.setText(userWinner.description);
        Utils.loadImageFromURL(view.getContext(), Constant.URL_ADDRESS_SERVER + userWinner.image, iv_user_winner);
        view.addView(layout);
        return layout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((FrameLayout) object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }
}

