package com.fantasygame.ui.forgot_password;

import com.fantasygame.base.BaseView;
import com.fantasygame.data.model.response.ForgotPasswordResponse;

/**
 * Created by HP on 22/08/2017.
 */

public interface ForgotPasswordView extends BaseView{
    public void showResultForgotPassword(ForgotPasswordResponse response);
}
