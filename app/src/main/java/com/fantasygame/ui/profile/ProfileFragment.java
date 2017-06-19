package com.fantasygame.ui.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.fantasygame.R;
import com.fantasygame.base.BaseFragment;
import com.fantasygame.ui.main.MainActivity;

/**
 * Created by HP on 18/06/2017.
 */

public class ProfileFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        MainActivity main = new MainActivity();
        main.settestMain(getActivity(), "Profile");
        main.showBack(getActivity());
    }
}
