package com.fantasygame.define;

import android.app.Application;

import com.fantasygame.BuildConfig;
import com.fantasygame.di.DaggerMainComponent;
import com.fantasygame.di.MainComponent;
import com.fantasygame.di.MainModule;

import timber.log.Timber;

/**
 * Created by HP on 15/06/2017.
 */

public class FantatsyGame extends Application {

    private static FantatsyGame sInstance;

    public MainComponent getMainComponent() {
        return mainComponent;
    }

    private MainComponent mainComponent;

    public static FantatsyGame getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Dependencies.init();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        sInstance = this;

        mainComponent = DaggerMainComponent.builder().mainModule(new MainModule()).build();
    }
}
