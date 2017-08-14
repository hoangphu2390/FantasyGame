package com.fantasygame.di;


import android.content.SharedPreferences;

import com.fantasygame.ui.login.LoginPresenter;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by AhmedEltaher on 5/12/2016
 */

@Module
public class MainModule {

    @Provides
    @Singleton
    EventBus provideEventBus() {
        return EventBus.getDefault();
    }
}
