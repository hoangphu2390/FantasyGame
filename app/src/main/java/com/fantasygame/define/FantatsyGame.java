package com.fantasygame.define;

import android.app.Application;
import android.content.Context;

import com.fantasygame.BuildConfig;

import timber.log.Timber;

/**
 * Created by HP on 15/06/2017.
 */

public class FantatsyGame extends Application {

    private static FantatsyGame sInstance;
    private static Context appContext;

    public static FantatsyGame getInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        appContext = getApplicationContext();

        Dependencies.init();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
