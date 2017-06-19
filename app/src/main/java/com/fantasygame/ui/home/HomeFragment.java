package com.fantasygame.ui.home;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fantasygame.R;
import com.fantasygame.base.BaseFragment;
import com.fantasygame.ui.detail.DetailFragment;
import com.fantasygame.ui.main.MainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HP on 18/06/2017.
 */

public class HomeFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity main = new MainActivity();
        main.settestMain(getActivity(), "Home");
        main.hideBack(getActivity());
    }

    @OnClick(R.id.btnClick)
    public void onClick(){
        Fragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("phu", "hello");
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(null)
                .commit();
    }
}
