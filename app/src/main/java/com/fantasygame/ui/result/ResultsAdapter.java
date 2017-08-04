package com.fantasygame.ui.result;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fantasygame.R;
import com.fantasygame.base.BaseAdapter;
import com.fantasygame.base.BaseHolder;
import com.fantasygame.data.model.response.ResultResponse.Datum;
import com.fantasygame.utils.Utils;

import butterknife.Bind;

/**
 * Created by HP on 24/07/2017.
 */

public class ResultsAdapter extends BaseAdapter<Datum, BaseHolder> {

    final String NEW_FORMAT = "MM-dd-yyyy hh:mm a";
    final String OLD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public ResultsAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = R.layout.item_results;
        return new ResultsHolder(inflater.inflate(layoutId, parent, false));
    }

    public class ResultsHolder extends BaseHolder<Datum> {
        @Bind(R.id.tv_game)
        TextView tv_game;
        @Bind(R.id.tv_user_name)
        TextView tv_user_name;
        @Bind(R.id.tv_points)
        TextView tv_points;
        @Bind(R.id.tv_datetime)
        TextView tv_datetime;


        protected Datum teams;

        public ResultsHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(Datum teams) {
            this.teams = teams;
            tv_game.setText(teams.gamename);
            String points = teams.score;
            if (points == null) points = "0";
            else if(points.contains(".")) points = Utils.formatPoints(Double.parseDouble(points));
            tv_points.setText(points);
            tv_user_name.setText(teams.username);

            String datetime = Utils.formatDateTime(OLD_FORMAT, NEW_FORMAT, teams.last_edited_date);
            tv_datetime.setText(datetime);
        }
    }
}


