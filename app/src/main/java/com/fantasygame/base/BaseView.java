package com.fantasygame.base;

import android.support.annotation.NonNull;

/**
 * Created by Kiet Nguyen on 13-Jan-17.
 */

public interface BaseView {
    void hideLoadingUI();

    void showLoadingUI();

    void showErrorLoadingUI(@NonNull Throwable throwable);
}
