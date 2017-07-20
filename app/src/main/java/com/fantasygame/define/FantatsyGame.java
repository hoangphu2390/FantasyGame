package com.fantasygame.define;

import android.app.Application;

import com.fantasygame.BuildConfig;

import timber.log.Timber;

/**
 * Created by HP on 15/06/2017.
 */

public class FantatsyGame extends Application {

    private static FantatsyGame sInstance;

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
    }
}
