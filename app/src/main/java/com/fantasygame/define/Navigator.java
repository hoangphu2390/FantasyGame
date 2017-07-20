package com.fantasygame.define;

import android.app.Activity;
import android.content.Intent;

import com.fantasygame.ui.login.LoginActivity;
import com.fantasygame.ui.main.MainActivity;
import com.fantasygame.ui.register.RegisterActivity;

/**
 * Created by HP on 18/06/2017.
 */

public class Navigator {
    public static void openMainActivity(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void openRegisterActivity(Activity activity) {
        Intent intent = new Intent(activity, RegisterActivity.class);
        activity.startActivity(intent);
<<<<<<< HEAD
=======
        activity.finish();
>>>>>>> ad8485e904013f72180e461e82a80c8da759f7cd
    }

    public static void openLoginActivity(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
