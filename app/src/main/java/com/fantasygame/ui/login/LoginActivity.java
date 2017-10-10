package com.fantasygame.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fantasygame.R;
import com.fantasygame.base.BaseActivity;
import com.fantasygame.data.model.response.LoginResponse;
import com.fantasygame.define.Navigator;
import com.fantasygame.ui.main.MainActivity;
import com.fantasygame.utils.PreferenceUtils;
import com.fantasygame.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HP on 18/06/2017.
 */

public class LoginActivity extends BaseActivity implements LoginView {

    @Bind(R.id.edtEmail)
    EditText edtEmail;
    @Bind(R.id.edtPass)
    EditText edtPass;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.btnSignIn)
    Button btnSignIn;
    @Bind(R.id.tv_create_account)
    TextView tv_create_account;

    LoginPresenter presenter;
    String email, password, contentIntent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        setupPresenter();

        if (getIntent() != null && getIntent().getStringExtra("authorized") != null) {
            contentIntent = getIntent().getStringExtra("authorized");
            tv_create_account.setVisibility(View.GONE);
            return;
        }

        String checklogin = PreferenceUtils.getFromPrefs(this, PreferenceUtils.PREFS_LogInLogOutCheck, "logout");
        if (checklogin.equals("login")) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void showResultLogin(@NonNull LoginResponse response) {
        Utils.showToast(response.message);
        btnSignIn.setEnabled(true);
        String api_token, avatar, phone, email, address, name;
        if (response.result) {
            if (response.data != null) {
                if (response.data.api_token != null) {
                    api_token = response.data.api_token;
                    PreferenceUtils.saveToPrefs(getApplicationContext(), PreferenceUtils.PREFS_ApiToken, api_token);
                }
                if (response.data.avatar != null) {
                    avatar = response.data.avatar;
                    PreferenceUtils.saveToPrefs(getApplicationContext(), PreferenceUtils.PREFS_AVATAR, avatar);
                }
                if (response.data.phone_number != null) {
                    phone = response.data.phone_number;
                    PreferenceUtils.saveToPrefs(getApplicationContext(), PreferenceUtils.PREFS_PHONE, phone);
                }
                if (response.data.email != null) {
                    email = response.data.email;
                    PreferenceUtils.saveToPrefs(getApplicationContext(), PreferenceUtils.PREFS_EMAIL, email);
                }
                if (response.data.address != null) {
                    address = response.data.address;
                    PreferenceUtils.saveToPrefs(getApplicationContext(), PreferenceUtils.PREFS_ADDRESS, address);
                }
                if (response.data.display_name != null) {
                    name = response.data.display_name;
                    PreferenceUtils.saveToPrefs(getApplicationContext(), PreferenceUtils.PREFS_FULLNAME, name);
                }
            }
            PreferenceUtils.saveToPrefs(getApplicationContext(), PreferenceUtils.PREFS_LogInLogOutCheck, "login");
            if (contentIntent.isEmpty())
                Navigator.openMainActivity(LoginActivity.this);
            else
                finish();
        }
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
        hideProgressDialog();
        Utils.showToast(getString(R.string.error_login));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    private boolean checkConditionLogin() {
        boolean isError = false;
        email = edtEmail.getText().toString().trim();
        password = edtPass.getText().toString().trim();
        if (email.isEmpty()) {
            edtEmail.setError(getString(R.string.show_error_email));
            edtEmail.requestFocus();
            isError = true;
        } else if (password.isEmpty()) {
            edtPass.setError(getString(R.string.show_error_password));
            edtPass.requestFocus();
            isError = true;
        }
        return isError;
    }

    private void setupPresenter() {
        presenter = new LoginPresenter();
        presenter.bindView(this);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    @OnClick(R.id.btnSignIn)
    public void clickSignIn() {
        if (checkConditionLogin()) return;
        if (Utils.isCheckShowSoftKeyboard(this))
            Utils.hideSoftKeyboard(this);
        btnSignIn.setEnabled(false);
        presenter.login(email, password);
    }

    @OnClick(R.id.tv_create_account)
    public void createAccount() {
        Navigator.openRegisterActivity(LoginActivity.this);
    }

    @OnClick(R.id.tv_forgot_pwd)
    public void forgotPassword() {
        Navigator.openForgotPasswordActivity(LoginActivity.this);
    }
}

