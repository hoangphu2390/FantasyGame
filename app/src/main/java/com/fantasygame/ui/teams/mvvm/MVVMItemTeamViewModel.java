package com.fantasygame.ui.teams.mvvm;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.fantasygame.data.model.response.FeatureResponse.Datum;
import com.fantasygame.define.Constant;
import com.fantasygame.utils.Utils;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by HP on 26/07/2017.
 */

public class MVVMItemTeamViewModel extends BaseObservable {

    Context context;
    Datum team;

    public MVVMItemTeamViewModel(Context context, Datum team) {
        this.context = context;
        this.team = team;
    }

    public String getName() {
        return team.name;
    }

    public String getRank() {
        return "Rank: " + team.rank;
    }

    public String getPictureProfile() {
        return team.image;
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(CircleImageView iv_team, String url) {
        Utils.loadAvatarFromURL(iv_team.getContext(), Constant.URL_ADDRESS_SERVER + url, iv_team);
    }

    public void setTeam(Datum team) {
        this.team = team;
        notifyChange();
    }
}
