package com.fantasygame.ui.register;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.Toast;

import com.fantasygame.R;
import com.fantasygame.data.model.response.RegisterResponse;
import com.fantasygame.define.Navigator;
import com.fantasygame.utils.UsPhoneNumberFormatter;
import com.fantasygame.utils.Utils;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HP on 19/06/2017.
 */

public class RegisterActivity extends Activity implements RegisterView {

    @Bind(R.id.edtUsername)
    EditText edtUsername;
    @Bind(R.id.edtPass)
    EditText edtPass;
    @Bind(R.id.edtDisplayName)
    EditText edtDisplayName;
    @Bind(R.id.edtPhone)
    EditText edtPhone;
    @Bind(R.id.edtEmail)
    EditText edtEmail;
    @Bind(R.id.edtAddress)
    EditText edtAddress;

    private ProgressDialog progressDialog;
    private RegisterPresenter presenter;
    private String username, password, email, phone, address, display_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        UsPhoneNumberFormatter addLineNumberFormatter = new UsPhoneNumberFormatter(
                new WeakReference<EditText>(edtPhone));
        edtPhone.addTextChangedListener(addLineNumberFormatter);

        progressDialog = new ProgressDialog(this);
        setupPresenter();
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
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showResultRegister(RegisterResponse response) {
        Utils.showToast(response.message);
        if (response.result) Navigator.openMainActivity(RegisterActivity.this);
    }

    private boolean checkConditionRegister() {
        boolean isError = false;
        username = edtUsername.getText().toString().trim();
        password = edtPass.getText().toString().trim();
        display_name = edtDisplayName.getText().toString().trim();
        phone = edtPhone.getText().toString().trim();
        address = edtAddress.getText().toString().trim();
        email = edtEmail.getText().toString().trim();

        if (username.isEmpty()) {
            edtUsername.setError(getString(R.string.show_error_username));
            edtUsername.requestFocus();
            isError = true;
        } else if (password.isEmpty()) {
            edtPass.setError(getString(R.string.show_error_password));
            edtPass.requestFocus();
            isError = true;
        } else if (display_name.isEmpty()) {
            edtDisplayName.setError(getString(R.string.show_error_display_name));
            edtDisplayName.requestFocus();
            isError = true;
        } else if (email.isEmpty()) {
            edtEmail.setError(getString(R.string.show_error_email));
            edtEmail.requestFocus();
            isError = true;
        } else if (!Utils.isValidEmail(email)) {
            edtEmail.setError(getString(R.string.show_error_email_invalid));
            edtEmail.requestFocus();
            isError = true;
        } else if (phone.isEmpty()) {
            edtPhone.setError(getString(R.string.show_error_phone));
            edtPhone.requestFocus();
            isError = true;
        } else if (address.isEmpty()) {
            edtAddress.setError(getString(R.string.show_error_address));
            edtAddress.requestFocus();
            isError = true;
        }
        return isError;
    }

    private void setupPresenter() {
        presenter = new RegisterPresenter();
        presenter.bindView(this);
    }

    @OnClick(R.id.btnSignUp)
    public void clickSignUp() {
        if (checkConditionRegister()) return;
        presenter.register(username, password, display_name, email, phone, address);
    }
}
