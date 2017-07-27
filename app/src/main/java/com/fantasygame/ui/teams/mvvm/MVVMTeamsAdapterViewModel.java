package com.fantasygame.ui.teams.mvvm;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.fantasygame.R;
import com.fantasygame.base.BaseAdapter;
import com.fantasygame.base.BaseHolder;
import com.fantasygame.data.model.response.FeatureResponse.Datum;
import com.fantasygame.databinding.ItemTeamViewModelMvvmBinding;
import com.fantasygame.databinding.ItemTeamsMvvmBinding;
import com.fantasygame.define.Constant;
import com.fantasygame.utils.Utils;

/**
 * Created by HP on 25/07/2017.
 */

public class MVVMTeamsAdapterViewModel extends BaseAdapter<Datum, BaseHolder> {

    public MVVMTeamsAdapterViewModel(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemTeamViewModelMvvmBinding binding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_team_view_model_mvvm, parent, false);
        return new TeamsHolder(binding);
    }

    public class TeamsHolder extends BaseHolder<Datum> {
        private ItemTeamViewModelMvvmBinding binding;

        public TeamsHolder(ItemTeamViewModelMvvmBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void bindData(Datum teams) {
            if (binding.getMvvmitemteamviewmodel() == null) {
                binding.setMvvmitemteamviewmodel(new MVVMItemTeamViewModel(itemView.getContext(), teams));
            } else {
                binding.getMvvmitemteamviewmodel().setTeam(teams);
            }
        }
    }
}