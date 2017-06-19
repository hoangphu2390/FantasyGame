package com.fantasygame.ui.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.fantasygame.R;
import com.fantasygame.data.model.User;
import com.fantasygame.databinding.ActivityLoginBinding;
import com.fantasygame.define.Navigator;
import com.fantasygame.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HP on 18/06/2017.
 */

public class LoginActivity extends Activity implements LoginView {
    @Bind(R.id.edtEmail)
    EditText edtEmail;
    @Bind(R.id.edtPass)
    EditText edtPass;

    private ProgressDialog progressDialog;
    private LoginPresenter presenter;
    private String email, password;
    private User user = new User();
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil
                .setContentView(this, R.layout.activity_login);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        setupPresenter();
    }

    @Override
    public void showLoginSuccessful(@NonNull User user, @NonNull String message) {
        user.setName("Phu");
        user.setDescription(null);
        binding.setUser(user);
        Utils.showToast(message);
    }

    @Override
    public void hideLoadingUI() {
        progressDialog.dismiss();
    }

    @Override
    public void showLoadingUI() {
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();
    }

    @Override
    public void showErrorLoadingUI(@NonNull Throwable throwable) {
        progressDialog.dismiss();
        Toast.makeText(this, getString(R.string.error_login), Toast.LENGTH_SHORT).show();
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
        } else if (!Utils.isValidEmail(email)) {
            edtEmail.setError(getString(R.string.show_error_email_invalid));
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
        showLoginSuccessful(user, "success");
    }
}

