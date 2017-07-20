package com.fantasygame.ui.detail_sport;

/**
 * Created by Kiet Nguyen on 9/27/2016.
 */

import android.content.Context;
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
import com.squareup.picasso.Picasso;

import java.util.List;

public class WinnerSportAdapter extends PagerAdapter {

    LayoutInflater inflater;
    Context context;
    List<Datum> usersWinner;

    public WinnerSportAdapter(Context context, List<Datum> usersWinner) {
        this.context = context;
        this.usersWinner = usersWinner;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return usersWinner.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View view_winner = inflater.inflate(R.layout.item_user_winner, view, false);
        Datum winner = usersWinner.get(position);

        TextView tv_user_winner_name = (TextView) view_winner
                .findViewById(R.id.tv_user_winner_name);
        ImageView iv_user_winner = (ImageView) view_winner
                .findViewById(R.id.iv_user_winner);
        TextView tv_user_winner_description = (TextView) view_winner
                .findViewById(R.id.tv_user_winner_description);

        tv_user_winner_name.setText(winner.player.toUpperCase());
        //  tv_user_winner_description.setText("WINNER ( " + winner.start_date + " - " + winner.end_date + " )");
        tv_user_winner_description.setText("WINNER ( 10/08  - 17/08 )");
        Picasso.with(view.getContext()).load(winner.image).into(iv_user_winner); Utils.loadImageFromURL(view.getContext(),
                Constant.URL_ADDRESS_SERVER + winner.image, iv_user_winner);
        view.addView(view_winner);
        return view_winner;
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
