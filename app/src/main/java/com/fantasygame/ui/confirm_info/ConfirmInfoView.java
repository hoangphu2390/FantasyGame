package com.fantasygame.ui.confirm_info;

import com.fantasygame.base.BaseView;
import com.fantasygame.data.model.response.FinishTeamResponse;

/**
 * Created by HP on 20/07/2017.
 */

public interface ConfirmInfoView extends BaseView{
    public void showResultPostSelectTeams(FinishTeamResponse response);
}
