package com.fantasygame.ui.register;

import com.fantasygame.base.BaseView;
import com.fantasygame.data.model.response.RegisterResponse;

/**
 * Created by HP on 19/06/2017.
 */

public interface RegisterView extends BaseView{
    public void showResultRegister(RegisterResponse response);
}
