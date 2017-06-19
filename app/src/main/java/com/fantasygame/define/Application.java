package com.fantasygame.define;


import com.fantasygame.BuildConfig;

import timber.log.Timber;

/**
 * Created by Kiet Nguyen on 03-Dec-16.
 */

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Dependencies.init();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}