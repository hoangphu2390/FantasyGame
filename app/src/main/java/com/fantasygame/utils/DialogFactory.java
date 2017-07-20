package com.fantasygame.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fantasygame.R;
import com.fantasygame.data.model.response.HowToPlayResponse;
import com.fantasygame.listener.ContinueSelectTeamListener;

/**
 * Created by HP on 13/07/2017.
 */

public class DialogFactory {

    public static Dialog createHowToPlayDialog(final Context context, HowToPlayResponse.Datum game, ContinueSelectTeamListener listener) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_how_to_play);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        ImageView btnClose = (ImageView) dialog.findViewById(R.id.btn_close);
        TextView tvHint = (TextView) dialog.findViewById(R.id.tv_hint);
        Button btnContinue = (Button) dialog.findViewById(R.id.btnContinue);
        tvHint.setText(Html.fromHtml(game.hint));

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                listener.onContinueSelectTeam();
            }
        });
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return dialog;
    }
}
