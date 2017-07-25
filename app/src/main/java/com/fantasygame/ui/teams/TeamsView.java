package com.fantasygame.ui.teams;

import android.support.annotation.NonNull;

import com.fantasygame.base.BaseView;
import com.fantasygame.data.model.response.FeatureResponse.Datum;
import com.fantasygame.data.model.response.SportResponse;

import java.util.List;

/**
 * Created by HP on 24/07/2017.
 */

public interface TeamsView extends BaseView {
    public void showResultGetListFeature(@NonNull List<Datum> featureResponse);
    public void showResultGetListSport(@NonNull SportResponse response);
}
