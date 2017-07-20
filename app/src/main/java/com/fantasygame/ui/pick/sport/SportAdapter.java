package com.fantasygame.ui.pick.sport;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fantasygame.R;
import com.fantasygame.base.BaseAdapter;
import com.fantasygame.base.BaseHolder;
import com.fantasygame.data.model.response.SportResponse.Sport;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;

/**
 * Created by HP on 20/06/2017.
 */

public class SportAdapter extends BaseAdapter<Sport, BaseHolder> {

    List<Sport> sports;
    SelectTeam listener;
    int index = 0;
    int[] images = {R.drawable.football, R.drawable.baseball, R.drawable.basketball, R.drawable.golf};

    public interface SelectTeam {
        void selectedTeam(Sport sport);
    }

    public SportAdapter(LayoutInflater inflater, List<Sport> sports, SelectTeam listener) {
        super(inflater);
        this.sports = sports;
        this.listener = listener;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = R.layout.item_pick;
        return new SlideMenuHolder(inflater.inflate(layoutId, parent, false));
    }

    public class SlideMenuHolder extends BaseHolder<Sport> {
        @Bind(R.id.iv_item_sport)
        ImageView iv_item_sport;

        protected Sport sport;

        public SlideMenuHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(Sport sport) {
            this.sport = sport;
            //   Utils.loadImageFromURL(itemView.getContext(), Constant.URL_ADDRESS_SERVER + sport.logo, iv_item_sport);
            if (index == 4) index = 0;
            Picasso.with(itemView.getContext()).load(images[index++]).into(iv_item_sport);
        }

        @Override
        public void bindEvent() {
            itemView.setOnClickListener(v -> {
                listener.selectedTeam(sport);
            });
        }
    }
}

