package com.fantasygame.ui.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fantasygame.R;
import com.fantasygame.base.BaseAdapter;
import com.fantasygame.base.BaseHolder;
import com.fantasygame.data.model.response.ProfileGameResponse.Datum;
import com.fantasygame.define.Constant;
import com.fantasygame.utils.Utils;

import java.util.List;

import butterknife.Bind;

/**
 * Created by HP on 25/09/2017.
 */

public class GamePlayAdapter extends BaseAdapter<Datum, BaseHolder> {

    PlayNowListener listener;

    public interface PlayNowListener {
        void onPlayNow(Datum game);
    }

    public GamePlayAdapter(LayoutInflater inflater, PlayNowListener listener) {
        super(inflater);
        this.listener = listener;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = R.layout.item_profile_game;
        return new GamePlayHolder(inflater.inflate(layoutId, parent, false));
    }

    public class GamePlayHolder extends BaseHolder<Datum> {
        @Bind(R.id.iv_game)
        ImageView iv_game;
        @Bind(R.id.tv_game_title)
        TextView tv_game_title;
        @Bind(R.id.btn_play_now)
        Button btn_play_now;
        @Bind(R.id.tv_game_price)
        TextView tv_game_price;

        protected Datum item;

        public GamePlayHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(Datum item) {
            this.item = item;
            Utils.loadImageFromURL(itemView.getContext(), Constant.URL_ADDRESS_SERVER + item.image, iv_game);
            tv_game_title.setText(item.name);
            tv_game_price.setText("$" + item.price);
        }

        @Override
        public void bindEvent() {
            btn_play_now.setOnClickListener(v -> {
                listener.onPlayNow(item);
            });
        }
    }
}
