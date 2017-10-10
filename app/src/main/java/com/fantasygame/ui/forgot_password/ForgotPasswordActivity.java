package com.fantasygame.ui.forgot_password;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.fantasygame.R;
import com.fantasygame.data.model.response.ForgotPasswordResponse;
import com.fantasygame.define.Navigator;
import com.fantasygame.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HP on 22/08/2017.
 */

public class ForgotPasswordActivity extends Activity implements ForgotPasswordView {

    @Bind(R.id.edtEmail)
    EditText edtEmail;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    ForgotPasswordPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        presenter = new ForgotPasswordPresenter();
        presenter.bindView(this);
    }

    @OnClick(R.id.btnReset)
    public void clickReset() {
        if (Utils.isCheckShowSoftKeyboard(this))
            Utils.hideSoftKeyboard(this);
        String email = edtEmail.getText().toString().trim();
        if (!Utils.isValidEmail(email)) {
            edtEmail.setError(getString(R.string.show_error_email_invalid));
            edtEmail.requestFocus();
            return;
        }
        presenter.forgotPassword(email);
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
    public void showResultForgotPassword(ForgotPasswordResponse response) {
        if (response.result) {
            Utils.showToast("Reset password successful.Please check your email.");
            Navigator.openLoginActivity(this);
        }
    }
}
