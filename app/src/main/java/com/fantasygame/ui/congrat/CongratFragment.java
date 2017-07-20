package com.fantasygame.ui.congrat;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.fantasygame.R;
import com.fantasygame.base.BaseFragment;
import com.fantasygame.ui.matches.MatchesFragment;
import com.fantasygame.ui.sport.SportFragment;

import butterknife.OnClick;

/**
 * Created by HP on 17/07/2017.
 */

public class CongratFragment extends BaseFragment {

    String tie_breaker_id;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_congrat;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            if (getArguments().containsKey("tie_breaker_id"))
                tie_breaker_id = getArguments().getString("tie_breaker_id");
        }
    }

    @OnClick(R.id.btnHome)
    public void clickHome() {
        Fragment fragment = new SportFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(null)
                .commit();
    }
}
