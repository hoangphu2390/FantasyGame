package com.fantasygame.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fantasygame.R;
import com.fantasygame.ui.main.MainActivity;

import butterknife.ButterKnife;

/**
 * Created by HP on 17/02/2017.
 */

public abstract class BaseFragment extends Fragment {

    protected abstract int getLayoutId();
    private ProgressDialog progressDialog;
    protected static MainActivity self;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        self = (MainActivity) activity;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
        return view;
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
