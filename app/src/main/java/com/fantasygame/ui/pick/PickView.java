package com.fantasygame.ui.pick;

import android.support.annotation.NonNull;

import com.fantasygame.base.BaseView;
import com.fantasygame.data.model.response.SportResponse;

/**
 * Created by HP on 21/06/2017.
 */

public interface PickView extends BaseView {
    public void showResultGetListSport(@NonNull SportResponse response);
}
