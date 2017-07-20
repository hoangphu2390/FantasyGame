package com.fantasygame.ui.matches;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fantasygame.R;
import com.fantasygame.base.BaseAdapter;
import com.fantasygame.base.BaseHolder;
import com.fantasygame.data.model.response.MatchesResponse.Datum;
import com.fantasygame.define.Constant;
import com.fantasygame.utils.Utils;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by HP on 13/07/2017.
 */

public class MatchesAdapter extends BaseAdapter<Datum, BaseHolder> {

    final String NEW_FORMAT = "MM-dd-yyyy hh:mm a";
    final String OLD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public interface SelectMatches {
        void selectMatches(Datum matches);
    }

    SelectMatches listener;

    public MatchesAdapter(LayoutInflater inflater, SelectMatches listener) {
        super(inflater);
        this.listener = listener;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = R.layout.item_matches;
        return new MatchesHolder(inflater.inflate(layoutId, parent, false));
    }

    public class MatchesHolder extends BaseHolder<Datum> {
        @Bind(R.id.tv_team_home)
        TextView tv_team_home;
        @Bind(R.id.tv_team_away)
        TextView tv_team_away;
        @Bind(R.id.tv_date)
        TextView tv_date;
        @Bind(R.id.tv_time)
        TextView tv_time;

        @Bind(R.id.iv_team_away)
        CircleImageView iv_team_away;
        @Bind(R.id.iv_team_home)
        CircleImageView iv_team_home;

        protected Datum matches;
        String datetime;

        public MatchesHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(Datum matches) {
            this.matches = matches;
            tv_team_home.setText(matches.home_team_name);
            tv_team_away.setText(matches.away_team_name);
            Utils.loadAvatarFromURL(itemView.getContext(), Constant.URL_ADDRESS_SERVER + matches.home_team_logo, iv_team_home);
            Utils.loadAvatarFromURL(itemView.getContext(), Constant.URL_ADDRESS_SERVER + matches.away_team_logo, iv_team_away);

            datetime = Utils.formatDateTime(OLD_FORMAT, NEW_FORMAT, matches.datetime);
            tv_date.setText(datetime.substring(0, 10));
            tv_time.setText(datetime.substring(10,datetime.length()));
        }

        @Override
        public void bindEvent() {
            itemView.setOnClickListener(v -> {
                listener.selectMatches(matches);
            });
        }
    }
}


