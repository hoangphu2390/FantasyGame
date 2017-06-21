package com.fantasygame.ui.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.Toast;

import com.fantasygame.R;
import com.fantasygame.base.BaseActivity;
import com.fantasygame.data.model.response.LoginResponse;
import com.fantasygame.define.Navigator;
import com.fantasygame.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HP on 18/06/2017.
 */

public class LoginActivity extends BaseActivity implements LoginView {
    @Bind(R.id.edtUsername)
    EditText edtUsername;
    @Bind(R.id.edtPass)
    EditText edtPass;

    private LoginPresenter presenter;
    private String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        setupPresenter();
    }

    @Override
    public void showResultLogin(@NonNull LoginResponse response) {
        Utils.showToast(response.message);
        if (response.result) {
            Navigator.openMainActivity(LoginActivity.this);
        }
    }

    @Override
    public void hideLoadingUI() {
        hideProgressDialog();
    }

    @Override
    public void showLoadingUI() {
        loadingProgressDialog();
    }

    @Override
    public void showErrorLoadingUI(@NonNull Throwable throwable) {
        hideProgressDialog();
        Toast.makeText(this, getString(R.string.error_login), Toast.LENGTH_SHORT).show();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    private boolean checkConditionLogin() {
        boolean isError = false;
        username = edtUsername.getText().toString().trim();
        password = edtPass.getText().toString().trim();
        if (username.isEmpty()) {
            edtUsername.setError(getString(R.string.show_error_username));
            edtUsername.requestFocus();
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
        presenter.login(username, password);
    }

    @OnClick(R.id.tv_create_account)
    public void createAccount() {
        Navigator.openRegisterActivity(LoginActivity.this);
    }
}

