package com.fantasygame.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fantasygame.R;
import com.fantasygame.base.BaseAdapter;
import com.fantasygame.base.BaseHolder;
import com.fantasygame.data.model.response.TeamResponse.Team;
import com.fantasygame.define.Constant;
import com.fantasygame.utils.Utils;

import java.util.List;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by HP on 20/06/2017.
 */

public class HomeAdapter extends BaseAdapter<Team, BaseHolder> {

    private List<Team> teams;

    public interface SelectedItem {
        void selectedTeam(Team team);
    }

    SelectedItem listener;

    public HomeAdapter(LayoutInflater inflater, List<Team> teams, SelectedItem listener) {
        super(inflater);
        this.teams = teams;
        this.listener = listener;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = R.layout.item_team;
        return new SlideMenuHolder(inflater.inflate(layoutId, parent, false));
    }

    public class SlideMenuHolder extends BaseHolder<Team> {
        @Bind(R.id.tv_name)
        TextView tv_name;
        @Bind(R.id.tv_city)
        TextView tv_city;
        @Bind(R.id.iv_avatar)
        CircleImageView iv_avatar;

        protected Team team;

        public SlideMenuHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(Team team) {
            this.team = team;
            tv_name.setText(team.name);
            tv_city.setText(team.city);
            Utils.loadAvatarFromURL(itemView.getContext(), Constant.URL_ADDRESS_SERVER + team.logo, iv_avatar);
        }

        @Override
        public void bindEvent() {
            itemView.setOnClickListener(v -> {
                listener.selectedTeam(team);
            });
        }
    }
}

