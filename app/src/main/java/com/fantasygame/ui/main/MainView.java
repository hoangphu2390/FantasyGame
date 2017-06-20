package com.fantasygame.ui.main;

import com.fantasygame.base.BaseView;
import com.fantasygame.data.model.response.LogoutResponse;

/**
 * Created by HP on 20/06/2017.
 */

public interface MainView extends BaseView {
    public void showResultLogout(LogoutResponse response);
}
