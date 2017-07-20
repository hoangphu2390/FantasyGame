package com.fantasygame.ui.login;

<<<<<<< HEAD
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
=======
import android.os.Bundle;
import android.support.annotation.NonNull;
>>>>>>> ad8485e904013f72180e461e82a80c8da759f7cd
import android.widget.EditText;
import android.widget.ProgressBar;

import com.fantasygame.R;
import com.fantasygame.base.BaseActivity;
import com.fantasygame.data.model.response.LoginResponse;
<<<<<<< HEAD
import com.fantasygame.define.FantatsyGame;
=======
>>>>>>> ad8485e904013f72180e461e82a80c8da759f7cd
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
<<<<<<< HEAD

=======
>>>>>>> ad8485e904013f72180e461e82a80c8da759f7cd
    @Bind(R.id.edtUsername)
    EditText edtUsername;
    @Bind(R.id.edtPass)
    EditText edtPass;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.btnSignIn)
    Button btnSignIn;

<<<<<<< HEAD
    LoginPresenter presenter;
    String username, password;
=======
    private LoginPresenter presenter;
    private String username, password;
>>>>>>> ad8485e904013f72180e461e82a80c8da759f7cd

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        setupPresenter();

        String checklogin = PreferenceUtils.getFromPrefs(this, PreferenceUtils.PREFS_LogInLogOutCheck, "logout");
        if (checklogin.equals("login")) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void showResultLogin(@NonNull LoginResponse response) {
        Utils.showToast(response.message);
<<<<<<< HEAD
        btnSignIn.setEnabled(true);
        if (response.result) {
            if (response.data != null && response.data.api_token != null) {
                String api_token = response.data.api_token;
                PreferenceUtils.saveToPrefs(getApplicationContext(), PreferenceUtils.PREFS_ApiToken, api_token);
                PreferenceUtils.saveToPrefs(getApplicationContext(), PreferenceUtils.PREFS_LogInLogOutCheck, "login");
            }
=======
        if (response.result) {
>>>>>>> ad8485e904013f72180e461e82a80c8da759f7cd
            Navigator.openMainActivity(LoginActivity.this);
        }
    }

    @Override
    public void hideLoadingUI() {
<<<<<<< HEAD
        progressBar.setVisibility(View.GONE);
=======
        hideProgressDialog();
>>>>>>> ad8485e904013f72180e461e82a80c8da759f7cd
    }

    @Override
    public void showLoadingUI() {
<<<<<<< HEAD
        progressBar.setVisibility(View.VISIBLE);
=======
        loadingProgressDialog();
>>>>>>> ad8485e904013f72180e461e82a80c8da759f7cd
    }

    @Override
    public void showErrorLoadingUI(@NonNull Throwable throwable) {
        hideProgressDialog();
<<<<<<< HEAD
        Utils.showToast(getString(R.string.error_login));
=======
        Toast.makeText(this, getString(R.string.error_login), Toast.LENGTH_SHORT).show();
>>>>>>> ad8485e904013f72180e461e82a80c8da759f7cd
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
<<<<<<< HEAD
        if (Utils.isCheckShowSoftKeyboard(this))
            Utils.hideSoftKeyboard(this);
        btnSignIn.setEnabled(false);
=======
>>>>>>> ad8485e904013f72180e461e82a80c8da759f7cd
        presenter.login(username, password);
    }

    @OnClick(R.id.tv_create_account)
    public void createAccount() {
        Navigator.openRegisterActivity(LoginActivity.this);
    }
}

