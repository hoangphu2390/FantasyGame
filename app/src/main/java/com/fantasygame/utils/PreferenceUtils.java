package com.fantasygame.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

public class PreferenceUtils {

    public static final String PREFS_ApiToken = "__apitokenServer__";
    public static final String PREFS_LogInLogOutCheck = "__loginlogoutcheck__";

    public static void saveToPrefs(Context context, String key, String value) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }

    public static String getFromPrefs(Context context, String key,
                                      String defaultValue) {
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        try {
            return sharedPrefs.getString(key, defaultValue);
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    public static void saveSetToPrefs(Context context, String key, Set<String> value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(key, value);
        editor.commit();
    }

    public static Set<String> getSetFromPrefs(Context context, String key) {
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sharedPrefs.getStringSet(key, new HashSet<String>());
    }
}
