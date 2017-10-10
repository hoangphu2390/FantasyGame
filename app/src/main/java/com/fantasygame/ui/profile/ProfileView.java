package com.fantasygame.ui.profile;

import com.fantasygame.base.BaseView;
import com.fantasygame.data.model.response.ProfileGameResponse;
import com.fantasygame.data.model.response.ProfileSportResponse;

/**
 * Created by HP on 26/09/2017.
 */

public interface ProfileView extends BaseView{
    public void showResultSports(ProfileSportResponse response);

    public void showResultGames(ProfileGameResponse response);
}
