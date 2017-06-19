package com.fantasygame.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fantasygame.define.FantatsyGame;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by LoiHo on 1/14/2016.
 */
public abstract class Utils {
    private static int screenWidth = 0;
    private static int screenHeight = 0;

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

    public static int getScreenHeight(Context c) {
        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenHeight = size.y;
        }

        return screenHeight;
    }

    public static int getScreenWidth(Context c) {
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }

        return screenWidth;
    }

    public static void hideSoftKeyboard(Activity context) {
        View view = context.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void hideKeyboard(Activity context, View aView){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(aView.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 300);
    }

    public static void showSoftKeyboard(Activity context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public static boolean isCheckShowSoftKeyboard(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();
    }

    public static boolean isInteger(String s) {
        Pattern pattern = Pattern.compile("\\d*");
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }

    public static boolean isSpecialCharacter(CharSequence s) {
        String specialChars = "1234567890+-/*!@#$%^&*()\"{}_[]|\\?/<>,.";
        if (specialChars.contains(s)) {
            return true;
        }
        return false;
    }

    public static Drawable getDrawable(String normal, String selected) {
        return getDrawable(normal, selected, null);
    }

    public static Drawable getDrawable(String normal, String selected, int[] colors) {
        StateListDrawable states = new StateListDrawable() {
            @Override
            protected boolean onStateChange(int[] states) {
                if (colors != null) {
                    boolean isSelected = false;
                    for (int state : states) {
                        if (state == android.R.attr.state_selected) {
                            isSelected = true;
                        }
                    }
                    if (isSelected)
                        setColorFilter(colors[0], PorterDuff.Mode.SRC_ATOP);
                    else {
                        clearColorFilter();
                        setColorFilter(colors[1], PorterDuff.Mode.SRC_ATOP);
                    }
                }
                return super.onStateChange(states);
            }
        };
        Drawable selectedDrawable = Drawable.createFromPath(selected);
        Drawable normalDrawable = Drawable.createFromPath(normal);

        states.addState(new int[]{android.R.attr.state_selected}, selectedDrawable);
        states.addState(new int[]{}, normalDrawable);
        return states;
    }

    public static Drawable getDrawable(Drawable normal, Drawable selected) {
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_selected}, selected);
        states.addState(new int[]{}, normal);
        return states;
    }

    public static void setImageDrawable(ImageView imageView, String normal, String selected, int[] colors) {
        Drawable drawable = getDrawable(normal, selected, colors);
        imageView.setImageDrawable(drawable);
    }

    public static void setImageDrawable(ImageView imageView, String normal, String selected) {
        setImageDrawable(imageView, normal, selected, null);
    }

    public static Drawable getShapeDrawableColor(LinearLayout lnTags, String color) {
        GradientDrawable drawable = (GradientDrawable) lnTags.getBackground();
        drawable.setColor(Color.parseColor(color));
        return drawable;
    }

    public static Drawable getShapeDrawableColor(TextView textView, String color) {
        GradientDrawable drawable = (GradientDrawable) textView.getBackground();
        drawable.setColor(Color.parseColor(color));
        return drawable;
    }

    public static Drawable getShapeDrawableColor(ImageView view, String color) {
        GradientDrawable drawable = (GradientDrawable) view.getBackground();
        if (drawable == null) {
            drawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[] {Color.parseColor(color)});
        } else {
            drawable.setColor(Color.parseColor(color));
        }
        return drawable;
    }

    public static boolean isOnline(Activity context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (wifiInfo.isConnected() || mobileInfo.isConnected()) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static Drawable getDrawable(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getDrawable(id, context.getTheme());
        }
        return context.getResources().getDrawable(id);
    }

    //Check email is valid
    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static void showToast(String content){
        Toast.makeText(FantatsyGame.getInstance(), content, Toast.LENGTH_SHORT).show();
    }
}
