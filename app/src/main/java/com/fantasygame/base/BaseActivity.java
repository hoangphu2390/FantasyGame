package com.fantasygame.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.fantasygame.R;

/**
 * Created by HP on 20/06/2017.
 */

<<<<<<< HEAD
public abstract class BaseActivity extends Activity {
    protected ProgressDialog progressDialog;
=======
public class BaseActivity extends Activity {
    private ProgressDialog progressDialog;
>>>>>>> ad8485e904013f72180e461e82a80c8da759f7cd

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
