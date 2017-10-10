package com.fantasygame.ui.register;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.fantasygame.R;
import com.fantasygame.base.BaseActivity;
import com.fantasygame.data.model.response.RegisterResponse;
import com.fantasygame.define.Navigator;
import com.fantasygame.utils.PreferenceUtils;
import com.fantasygame.utils.UsPhoneNumberFormatter;
import com.fantasygame.utils.Utils;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HP on 19/06/2017.
 */

public class RegisterActivity extends BaseActivity implements RegisterView {

    @Bind(R.id.edtFirstName)
    EditText edtFirstName;
    @Bind(R.id.edtLastName)
    EditText edtLastName;
    @Bind(R.id.edtPass)
    EditText edtPass;
    @Bind(R.id.edtPhone)
    EditText edtPhone;
    @Bind(R.id.edtEmail)
    EditText edtEmail;
    @Bind(R.id.edtAddress)
    EditText edtAddress;
    @Bind(R.id.edtDisplayName)
    EditText edtDisplayName;
    @Bind(R.id.edtConfirmPass)
    EditText edtConfirmPass;
    @Bind(R.id.ckbTerm)
    CheckBox ckbTerm;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.btnSignUp)
    Button btnSignUp;
    @Bind(R.id.ic_pwd_info)
    ImageView ic_pwd_info;

    RegisterPresenter presenter;
    String password, confirm_pwd, email, phone, first_name, last_name, address, display_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        ckbTerm.setButtonDrawable(R.drawable.ckb_blank);
        setupPresenter();

        ckbTerm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ckbTerm.setButtonDrawable(isChecked ? R.drawable.ckb_tick : R.drawable.ckb_blank);
            }
        });

        UsPhoneNumberFormatter addLineNumberFormatter = new UsPhoneNumberFormatter(
                new WeakReference<EditText>(edtPhone));
        edtPhone.addTextChangedListener(addLineNumberFormatter);

        ic_pwd_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showToast("Your password must be 8-20 characters long, contain both " +
                        "letters and numbers, and must not contain spaces, special characters, or emoji.");
            }
        });
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
        hideLoadingUI();
        Utils.showToast(throwable.getMessage());
    }

    @Override
    public void showResultRegister(RegisterResponse response) {
        Utils.showToast(response.message);
        btnSignUp.setEnabled(true);
        if (response.result) {
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
                Navigator.openMainActivity(RegisterActivity.this);
            }
        }
    }

    private boolean checkConditionRegister() {
        boolean isError = false;
        password = edtPass.getText().toString().trim();
        confirm_pwd = edtConfirmPass.getText().toString().trim();
        phone = edtPhone.getText().toString().trim();
        email = edtEmail.getText().toString().trim();
        address = edtAddress.getText().toString().trim();
        display_name = edtDisplayName.getText().toString().trim();
        first_name = edtFirstName.getText().toString().trim();
        last_name = edtLastName.getText().toString().trim();

        if (first_name.isEmpty()) {
            edtFirstName.setError(getString(R.string.show_error_first_name));
            edtFirstName.requestFocus();
            isError = true;
        } else if (last_name.isEmpty()) {
            edtLastName.setError(getString(R.string.show_error_last_name));
            edtLastName.requestFocus();
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
        } else if (display_name.isEmpty()) {
            edtDisplayName.setError(getString(R.string.show_error_display_name));
            edtDisplayName.requestFocus();
            isError = true;
        } else if (password.isEmpty()) {
            edtPass.setError(getString(R.string.show_error_password));
            edtPass.requestFocus();
            isError = true;
        } else if (password.length() < 8) {
            edtPass.setError(getString(R.string.show_error_password));
            edtPass.requestFocus();
            isError = true;
        } else if (!edtPass.getText().toString().trim().matches("^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+$")) {
            edtPass.setError(getString(R.string.show_error_must_be_contain_both_letters_and_numbers));
            edtPass.requestFocus();
            isError = true;
        } else if (confirm_pwd.isEmpty()) {
            edtConfirmPass.setError(getString(R.string.show_error_password));
            edtConfirmPass.requestFocus();
            isError = true;
        } else if (!confirm_pwd.equals(password)) {
            edtConfirmPass.setError(getString(R.string.error_confirm_pwd_not_same_pwd));
            edtConfirmPass.requestFocus();
            isError = true;
        } else if (!ckbTerm.isChecked()) {
            Utils.showToast(getString(R.string.agree_the_term_and_policy));
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
        phone = phone.replace("(", "").replace(")", "").replace(" ", "-");
        if (Utils.isCheckShowSoftKeyboard(this))
            Utils.hideSoftKeyboard(this);
        btnSignUp.setEnabled(false);
        presenter.register(first_name, last_name, password, email, phone, address, display_name);
    }
}
