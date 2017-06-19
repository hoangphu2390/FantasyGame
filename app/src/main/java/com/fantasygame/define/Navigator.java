package com.fantasygame.define;

import android.app.Activity;
import android.content.Intent;

import com.fantasygame.ui.main.MainActivity;

/**
 * Created by HP on 18/06/2017.
 */

public class Navigator {
    public static void openMainActivity(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
