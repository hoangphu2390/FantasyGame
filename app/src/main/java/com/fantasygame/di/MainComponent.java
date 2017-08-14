package com.fantasygame.di;


import com.fantasygame.ui.login.LoginActivity;
import com.fantasygame.ui.register.RegisterActivity;
import com.fantasygame.ui.select_team.SelectTeamFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by AhmedEltaher on 5/12/2016
 */
@Singleton
@Component(modules = MainModule.class)
public interface MainComponent {
    void inject(LoginActivity activity);
    void inject(RegisterActivity activity);
}
