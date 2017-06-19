package com.fantasygame.ui.login;

import android.support.annotation.NonNull;

import com.fantasygame.base.BaseView;
import com.fantasygame.data.model.User;

/**
 * Created by HP on 18/06/2017.
 */

public interface LoginView extends BaseView{
    void showLoginSuccessful(@NonNull User user, @NonNull String message);
}
