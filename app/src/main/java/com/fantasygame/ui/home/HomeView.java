package com.fantasygame.ui.home;

import android.support.annotation.NonNull;

import com.fantasygame.base.BaseView;
import com.fantasygame.data.model.response.TeamResponse;

/**
 * Created by HP on 20/06/2017.
 */

public interface HomeView extends BaseView {
    public void showResultGetListTeam(@NonNull TeamResponse response, @NonNull boolean isLoadMore);
}
