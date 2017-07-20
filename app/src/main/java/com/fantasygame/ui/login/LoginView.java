package com.fantasygame.ui.login;

import com.fantasygame.base.BaseView;
import com.fantasygame.data.model.response.LoginResponse;

/**
 * Created by HP on 18/06/2017.
 */

public interface LoginView extends BaseView{
    void showResultLogin(LoginResponse response);
}
