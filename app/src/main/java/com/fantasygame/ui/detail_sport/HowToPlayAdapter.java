package com.fantasygame.ui.detail_sport;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fantasygame.R;
import com.fantasygame.base.BaseAdapter;
import com.fantasygame.base.BaseHolder;
import com.fantasygame.data.model.response.HowToPlayResponse.Datum;
import com.fantasygame.define.Constant;
import com.fantasygame.utils.Utils;

import java.util.List;

import butterknife.Bind;

/**
 * Created by HP on 13/07/2017.
 */

public class HowToPlayAdapter extends BaseAdapter<Datum, BaseHolder> {

    List<Datum> howToPlay;
    SelectHowToPlay listener;

    public interface SelectHowToPlay {
        void selectedSport(Datum game);
    }

    public HowToPlayAdapter(LayoutInflater inflater, List<Datum> howToPlay, SelectHowToPlay listener) {
        super(inflater);
        this.howToPlay = howToPlay;
        this.listener = listener;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = R.layout.item_how_to_play;
        return new HowToPlayHolder(inflater.inflate(layoutId, parent, false));
    }

    public class HowToPlayHolder extends BaseHolder<Datum> {
        @Bind(R.id.iv_how_to_play)
        ImageView iv_how_to_play;
        @Bind(R.id.tv_how_to_play)
        TextView tv_how_to_play;
        @Bind(R.id.btn_play_now)
        Button btn_play_now;
        @Bind(R.id.tv_price)
        TextView tv_price;

        protected Datum item;

        public HowToPlayHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(Datum item) {
            this.item = item;
            Utils.loadImageFromURL(itemView.getContext(), Constant.URL_ADDRESS_SERVER + item.image, iv_how_to_play);
            tv_how_to_play.setText(item.description);
            tv_price.setText("$" + item.price);
        }

        @Override
        public void bindEvent() {
            btn_play_now.setOnClickListener(v -> {
                listener.selectedSport(item);
            });
        }
    }
}

