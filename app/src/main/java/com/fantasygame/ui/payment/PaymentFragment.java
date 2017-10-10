package com.fantasygame.ui.payment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.devmarvel.creditcardentry.library.CardValidCallback;
import com.devmarvel.creditcardentry.library.CreditCard;
import com.devmarvel.creditcardentry.library.CreditCardForm;
import com.fantasygame.BuildConfig;
import com.fantasygame.R;
import com.fantasygame.base.BaseFragment;
import com.fantasygame.data.model.CardCredit;
import com.fantasygame.data.model.response.PaymentCardResponse;
import com.fantasygame.data.model.response.PaymentTicketResponse;
import com.fantasygame.ui.select_team.SelectTeamFragment;
import com.fantasygame.utils.Utils;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by HP on 31/08/2017.
 */

public class PaymentFragment extends BaseFragment implements PaymentView {

    @Bind(R.id.credit_card_form)
    CreditCardForm creditCardForm;
    @Bind(R.id.rdb_access_code)
    RadioButton rdb_access_code;
    @Bind(R.id.rdb_buy_ticket)
    RadioButton rdb_buy_ticket;
    @Bind(R.id.edtAccessCode)
    EditText edtAccessCode;
    @Bind(R.id.btnPayment)
    Button btnPayment;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    private CardCredit cardCredit;
    private boolean isCheck;
    private PaymentPresenter presenter;
    private String game_id;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_payment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        self.settestMain(self, "PAYMENT");
        self.showBack(self);
        if (getArguments() != null && getArguments().containsKey("game_id")) {
            game_id = getArguments().getString("game_id");
        }
        creditCardForm.setOnCardValidCallback(cardValidCallback);
        setUiRadioGroup();

        presenter = new PaymentPresenter();
        presenter.bindView(this);
    }

    @OnClick(R.id.btnPayment)
    public void clickPayment() {
        if (rdb_access_code.isChecked()) {
            presenter.paymentAccessCode(edtAccessCode.getText().toString());
            return;
        }

        if (!isCheck) {
            Toast.makeText(self, "Card information invalid or not completed", Toast.LENGTH_SHORT).show();
            return;
        } else {
            btnPayment.setEnabled(false);
        }
        if (Utils.isCheckShowSoftKeyboard(self))
            Utils.hideSoftKeyboard(self);

        progressBar.setVisibility(View.VISIBLE);

        final String publishableApiKey = BuildConfig.DEBUG ?
                "pk_test_6pRNASCoBOKtIshFeQd4XMUh" :
                getString(R.string.com_stripe_publishable_key);

        Card card = new Card(creditCardForm.getCreditCard().getCardNumber(),
                creditCardForm.getCreditCard().getExpMonth(),
                creditCardForm.getCreditCard().getExpYear(),
                creditCardForm.getCreditCard().getSecurityCode());

        Stripe stripe = new Stripe(getActivity());
        stripe.createToken(card, publishableApiKey, new TokenCallback() {
            public void onSuccess(Token token) {
                presenter.paymentCreditCard(game_id, cardCredit);
            }

            public void onError(Exception error) {
                Toast.makeText(self, "Card information invalid or not completed", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                isCheck = false;
                btnPayment.setEnabled(true);
            }
        });
    }

    CardValidCallback cardValidCallback = new CardValidCallback() {
        @Override
        public void cardValid(CreditCard card) {
            isCheck = true;
            cardCredit = new CardCredit();
            cardCredit.stripe_number = card.getCardNumber();
            cardCredit.stripe_card_code = card.getSecurityCode();
            cardCredit.stripe_exp_month = String.valueOf(card.getExpMonth());
            cardCredit.stripe_exp_year = String.valueOf(card.getExpYear());
        }
    };


    public void setUiRadioGroup() {
        rdb_access_code.setButtonDrawable(R.drawable.ckb_tick);
        rdb_access_code.setChecked(true);
        rdb_buy_ticket.setButtonDrawable(R.drawable.ckb_blank);
        edtAccessCode.setEnabled(true);
        edtAccessCode.requestFocus();
        creditCardForm.setVisibility(View.GONE);

        rdb_access_code.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                rdb_access_code.setButtonDrawable(isChecked ? R.drawable.ckb_tick : R.drawable.ckb_blank);
                rdb_buy_ticket.setButtonDrawable(isChecked ? R.drawable.ckb_blank : R.drawable.ckb_tick);
                if (isChecked) {
                    edtAccessCode.setEnabled(true);
                    edtAccessCode.requestFocus();
                    creditCardForm.setVisibility(View.GONE);
                }
            }
        });

        rdb_buy_ticket.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                rdb_buy_ticket.setButtonDrawable(isChecked ? R.drawable.ckb_tick : R.drawable.ckb_blank);
                rdb_access_code.setButtonDrawable(isChecked ? R.drawable.ckb_blank : R.drawable.ckb_tick);
                if (isChecked) {
                    creditCardForm.setVisibility(View.VISIBLE);
                    creditCardForm.requestFocus();
                    edtAccessCode.setEnabled(false);
                }
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
        progressBar.setVisibility(View.GONE);
        Utils.showToast(throwable.getMessage());
    }

    @Override
    public void showResultPaymentTicket(PaymentTicketResponse response) {
        openSelectTeamFragment("", response.message, response.result);
    }

    @Override
    public void showResultPaymentCard(PaymentCardResponse response) {
        openSelectTeamFragment(response.data.transaction_id, response.message, response.result);
    }

    private void openSelectTeamFragment(String transaction_id, String message, boolean result) {
        Utils.showToast(message);
        if (!result) return;
        isCheck = false;
        btnPayment.setEnabled(true);
        Fragment fragment = new SelectTeamFragment();
        Bundle bundle = new Bundle();
        bundle.putString("game_id", game_id);
        bundle.putString("game_name", getArguments().getString("game_name"));
        bundle.putString("price", getArguments().getString("price"));
        bundle.putString("tie_breaker_id", getArguments().getString("tie_breaker_id"));
        bundle.putString("congratulation", getArguments().getString("congratulation"));
        bundle.putString("transaction_id", transaction_id);
        if (cardCredit != null && !cardCredit.stripe_card_code.isEmpty())
            bundle.putString("card_num", cardCredit.stripe_number);
        else if (!edtAccessCode.getText().toString().isEmpty())
            bundle.putString("access_code", edtAccessCode.getText().toString());
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(null)
                .commit();
    }
}
