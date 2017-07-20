package com.fantasygame.ui.matches;

import android.support.annotation.NonNull;

import com.fantasygame.base.BaseView;
import com.fantasygame.data.model.response.MatchesResponse;
import com.fantasygame.data.model.response.SportResponse;

/**
 * Created by HP on 13/07/2017.
 */

public interface MatchesView extends BaseView {
    public void showResultGetMatches(@NonNull MatchesResponse response);
    public void showResultGetListSport(@NonNull SportResponse response);
}
