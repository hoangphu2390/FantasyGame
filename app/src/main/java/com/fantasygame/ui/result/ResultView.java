package com.fantasygame.ui.result;

import android.support.annotation.NonNull;

import com.fantasygame.base.BaseView;
import com.fantasygame.data.model.response.ResultResponse.Datum;
import com.fantasygame.data.model.response.SportResponse;

import java.util.List;

/**
 * Created by HP on 24/07/2017.
 */

public interface ResultView extends BaseView {
    public void showResultGetListResult(@NonNull List<Datum> resultResponse);
    public void hideLayoutHeader();
    public void showResultGetListSport(@NonNull SportResponse response);
}
