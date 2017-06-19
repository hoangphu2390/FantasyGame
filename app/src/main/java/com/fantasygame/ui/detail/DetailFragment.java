package com.fantasygame.ui.detail;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fantasygame.R;
import com.fantasygame.base.BaseFragment;
import com.fantasygame.ui.main.MainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by HP on 18/06/2017.
 */

public class DetailFragment extends BaseFragment {

    @Bind(R.id.tv_content)
    TextView tv_content;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detail;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null && getArguments().containsKey("phu")) {
            String content = getArguments().getString("phu");
            tv_content.setText(content);
        }

        MainActivity main = new MainActivity();
        main.settestMain(getActivity(), "Detail");
        main.showBack(getActivity());
    }
}
