package com.fantasygame.ui.tie_breaker;

import android.support.annotation.NonNull;

import com.fantasygame.base.BaseView;
import com.fantasygame.data.model.response.TieBreakerResponse;

/**
 * Created by HP on 17/07/2017.
 */

public interface TieBreakerView extends BaseView{
    public void showResultGetTieBreaker(@NonNull TieBreakerResponse response);

}
