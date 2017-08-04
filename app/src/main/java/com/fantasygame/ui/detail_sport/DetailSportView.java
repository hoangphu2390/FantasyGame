package com.fantasygame.ui.detail_sport;

import android.support.annotation.NonNull;

import com.fantasygame.base.BaseView;
import com.fantasygame.data.model.response.FeatureResponse;
import com.fantasygame.data.model.response.HowToPlayResponse;
import com.fantasygame.data.model.response.UserWinnerResponse.Datum;

import java.util.List;

/**
 * Created by HP on 21/06/2017.
 */

public interface DetailSportView extends BaseView {
    public void showResultGetHowToPlay(@NonNull HowToPlayResponse response, boolean isLoadMore);
}
