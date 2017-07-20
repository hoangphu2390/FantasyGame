package com.fantasygame.ui.confirm_info;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fantasygame.R;
import com.fantasygame.base.BaseAdapter;
import com.fantasygame.base.BaseHolder;
import com.fantasygame.data.model.response.SelectTeamResponse.Team;
import com.fantasygame.define.Constant;
import com.fantasygame.utils.Utils;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by HP on 17/07/2017.
 */

public class ConfirmTeamAdapter extends BaseAdapter<Team, BaseHolder> {


    public ConfirmTeamAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = R.layout.item_confirm_team;
        return new TeamHolder(inflater.inflate(layoutId, parent, false));
    }

    public class TeamHolder extends BaseHolder<Team> {
        @Bind(R.id.tv_team_name)
        TextView tv_team_name;
        @Bind(R.id.tv_team_rank)
        TextView tv_team_rank;
        @Bind(R.id.iv_team_logo)
        CircleImageView iv_team_logo;

        protected Team team;

        public TeamHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(Team team) {
            this.team = team;
            tv_team_name.setText(team.name);
            tv_team_rank.setText("Rank :" + team.rank);
            Utils.loadAvatarFromURL(itemView.getContext(), Constant.URL_ADDRESS_SERVER + team.logo, iv_team_logo);
        }
    }
}