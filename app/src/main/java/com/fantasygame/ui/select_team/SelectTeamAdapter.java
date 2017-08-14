package com.fantasygame.ui.select_team;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fantasygame.R;
import com.fantasygame.data.model.response.SelectTeamResponse.Team;
import com.fantasygame.define.Constant;
import com.fantasygame.utils.Utils;

import java.util.List;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by HP on 17/07/2017.
 */

public class SelectTeamAdapter extends BaseAdapter {
    Context context;
    List<Team> teams;
    LayoutInflater inflter;

    @Inject
    public SelectTeamAdapter(Context context, List<Team> teams) {
        this.context = context;
        this.teams = teams;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return teams.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        view = inflter.inflate(R.layout.item_selected_team, null);
        TextView tv_team_name = (TextView) view.findViewById(R.id.tv_team_name);
        TextView tv_team_rank = (TextView) view.findViewById(R.id.tv_team_rank);
        CircleImageView iv_team_logo = (CircleImageView) view.findViewById(R.id.iv_team_logo);

        Team team = teams.get(position);
        tv_team_name.setText(team.name);
        String rank = team.rank;
        if (rank == null) rank = "0";
        tv_team_rank.setText("Rank: " + rank);
        Utils.loadAvatarFromURL(view.getContext(), Constant.URL_ADDRESS_SERVER + team.logo, iv_team_logo);
        return view;
    }
}