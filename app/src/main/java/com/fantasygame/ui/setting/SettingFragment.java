package com.fantasygame.ui.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.fantasygame.R;
import com.fantasygame.base.BaseFragment;
import com.fantasygame.ui.main.MainActivity;

/**
 * Created by HP on 18/06/2017.
 */

public class SettingFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        MainActivity main = new MainActivity();
        main.settestMain(getActivity(), "SETTING");
        main.showBack(getActivity());
    }
}
