package com.fantasygame.ui.teams.mvvm;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.fantasygame.R;
import com.fantasygame.base.BaseAdapter;
import com.fantasygame.base.BaseHolder;
import com.fantasygame.data.model.response.FeatureResponse.Datum;
import com.fantasygame.databinding.ItemTeamsMvvmBinding;
import com.fantasygame.define.Constant;
import com.fantasygame.utils.Utils;

/**
 * Created by HP on 25/07/2017.
 */

public class MVVMTeamsAdapter extends BaseAdapter<Datum, BaseHolder> {

    public MVVMTeamsAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemTeamsMvvmBinding binding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_teams_mvvm, parent, false);
        return new TeamsHolder(binding);
    }

    public class TeamsHolder extends BaseHolder<Datum> {
        private ItemTeamsMvvmBinding binding;

        public TeamsHolder(ItemTeamsMvvmBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void bindData(Datum teams) {
            binding.setDatum(teams);
            Utils.loadAvatarFromURL(itemView.getContext(), Constant.URL_ADDRESS_SERVER + teams.image, binding.ivTeam);
        }
    }
}