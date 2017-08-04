package com.fantasygame.ui.teams;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fantasygame.R;
import com.fantasygame.base.BaseAdapter;
import com.fantasygame.base.BaseHolder;
import com.fantasygame.data.model.response.FeatureResponse.Datum;
import com.fantasygame.define.Constant;
import com.fantasygame.utils.Utils;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by HP on 24/07/2017.
 */

public class TeamsAdapter extends BaseAdapter<Datum, BaseHolder> {

    public interface SelectTeams {
        void selectTeams(Datum teams);
    }

    TeamsAdapter.SelectTeams listener;

    public TeamsAdapter(LayoutInflater inflater, TeamsAdapter.SelectTeams listener) {
        super(inflater);
        this.listener = listener;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = R.layout.item_teams;
        return new TeamsHolder(inflater.inflate(layoutId, parent, false));
    }

    public class TeamsHolder extends BaseHolder<Datum> {
        @Bind(R.id.tv_team_name)
        TextView tv_team_name;
        @Bind(R.id.tv_team_rank)
        TextView tv_team_rank;

        @Bind(R.id.iv_team)
        CircleImageView iv_team;

        protected Datum teams;

        public TeamsHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(Datum teams) {
            this.teams = teams;
            tv_team_name.setText(teams.name);
            String rank = teams.rank;
            if (rank == null) rank = "0";
            tv_team_rank.setText("Rank: " + rank);
            Utils.loadAvatarFromURL(itemView.getContext(), Constant.URL_ADDRESS_SERVER + teams.image, iv_team);
        }

        @Override
        public void bindEvent() {
            itemView.setOnClickListener(v -> {
                listener.selectTeams(teams);
            });
        }
    }
}


