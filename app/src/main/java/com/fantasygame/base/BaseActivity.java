package com.fantasygame.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.fantasygame.R;

/**
 * Created by HP on 20/06/2017.
 */

public abstract class BaseActivity extends Activity {
    protected ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
    }

    protected void loadingProgressDialog() {
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();
    }

    protected void hideProgressDialog() {
        progressDialog.dismiss();
    }
}
