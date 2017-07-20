package com.fantasygame.ui.select_team;

import android.support.annotation.NonNull;

import com.fantasygame.base.BaseView;
import com.fantasygame.data.model.response.SelectTeamResponse;

/**
 * Created by HP on 17/07/2017.
 */

public interface SelectTeamView extends BaseView{
    public void showResultGetTeamSelect(@NonNull SelectTeamResponse response);
}
