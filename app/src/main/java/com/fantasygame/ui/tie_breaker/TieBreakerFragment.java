package com.fantasygame.ui.tie_breaker;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fantasygame.R;
import com.fantasygame.base.BaseFragment;
import com.fantasygame.data.model.response.SelectTeamResponse.Team;
import com.fantasygame.data.model.response.TieBreakerResponse;
import com.fantasygame.ui.confirm_info.ConfirmInfoFragment;
import com.fantasygame.ui.congrat.CongratFragment;
import com.fantasygame.utils.Utils;

import org.parceler.Parcels;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by HP on 17/07/2017.
 */

public class TieBreakerFragment extends BaseFragment implements TieBreakerView {

    @Bind(R.id.edtTieBreaker)
    EditText edtTieBreaker;
    @Bind(R.id.tv_question)
    TextView tv_question;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    TieBreakerPresenter presenter;
    String tie_breaker_id;
    Parcelable parcelable;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tie_breaker;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null && getArguments().containsKey("tie_breaker_id")) {
            tie_breaker_id = getArguments().getString("tie_breaker_id");
        }
        if (getArguments() != null && getArguments().containsKey("teams")) {
            parcelable = getArguments().getParcelable("teams");
        }

        presenter = new TieBreakerPresenter();
        presenter.bindView(this);
        if (tie_breaker_id != null && !tie_breaker_id.isEmpty())
            presenter.getTieBreaker(Integer.parseInt(tie_breaker_id));
    }

    @OnClick(R.id.btnContinue)
    public void clickContinue() {
        String content = edtTieBreaker.getText().toString();
        if (content.isEmpty()) {
            edtTieBreaker.setError("Please enter tie-breaker");
            return;
        }
        if (Utils.isCheckShowSoftKeyboard(self)) Utils.hideSoftKeyboard(self);

        Fragment fragment = new ConfirmInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("teams", parcelable);
        bundle.putString("tie_breaker", content);
        bundle.putString("game_id", getArguments().getString("game_id"));
        bundle.putString("price", getArguments().getString("price"));
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(null)
                .commit();
    }

    @Override
    public void hideLoadingUI() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingUI() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorLoadingUI(@NonNull Throwable throwable) {
        progressBar.setVisibility(View.GONE);
        Utils.showToast(throwable.getMessage());
    }

    @Override
    public void showResultGetTieBreaker(@NonNull TieBreakerResponse response) {
        if (!response.result) return;
        tv_question.setText(response.data.question);
    }
}
