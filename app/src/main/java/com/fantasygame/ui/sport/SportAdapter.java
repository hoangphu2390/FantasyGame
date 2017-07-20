package com.fantasygame.ui.sport;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fantasygame.R;
import com.fantasygame.base.BaseAdapter;
import com.fantasygame.base.BaseHolder;
import com.fantasygame.data.model.response.SportResponse.Data;
import com.fantasygame.define.Constant;
import com.fantasygame.utils.Utils;

import java.util.List;

import butterknife.Bind;

/**
 * Created by HP on 20/06/2017.
 */

public class SportAdapter extends BaseAdapter<Data, BaseHolder> {

    List<Data> sports;
    SelectSport listener;

    public interface SelectSport {
        void selectedSport(Data sport);
    }

    public SportAdapter(LayoutInflater inflater, List<Data> sports, SelectSport listener) {
        super(inflater);
        this.sports = sports;
        this.listener = listener;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = R.layout.item_sport;
        return new SlideMenuHolder(inflater.inflate(layoutId, parent, false));
    }

    public class SlideMenuHolder extends BaseHolder<Data> {
        @Bind(R.id.iv_item_sport)
        ImageView iv_item_sport;

        protected Data sport;

        public SlideMenuHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(Data sport) {
            this.sport = sport;
            Utils.loadImageFromURL(itemView.getContext(), Constant.URL_ADDRESS_SERVER + sport.image, iv_item_sport);
       //     Utils.loadImageFromURL(itemView.getContext(), "http://grbee.kennjdemo.com/img/sport/basketball-nba.png", iv_item_sport);
        }

        @Override
        public void bindEvent() {
            itemView.setOnClickListener(v -> {
                listener.selectedSport(sport);
            });
        }
    }
}

