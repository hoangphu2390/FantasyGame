package com.fantasygame.ui.sport;

import android.support.annotation.NonNull;

import com.fantasygame.base.BaseView;
import com.fantasygame.data.model.response.FeatureResponse;
import com.fantasygame.data.model.response.SportResponse;
import com.fantasygame.data.model.response.UserWinnerResponse.Datum;

import java.util.List;

/**
 * Created by HP on 21/06/2017.
 */

public interface SportView extends BaseView {
    public void showResultGetListSport(@NonNull SportResponse response);
    public void showResultGetAllWinner(@NonNull List<Datum> usersWinner);
    public void showResultGetListFeature(@NonNull List<FeatureResponse.Datum> featureResponse);
}
