package com.fantasygame.ui.payment;

import com.fantasygame.api.ServerAPI;
import com.fantasygame.base.Presenter;
import com.fantasygame.data.model.CardCredit;
import com.fantasygame.define.Dependencies;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by HP on 19/09/2017.
 */

public class PaymentPresenter extends Presenter<PaymentView> {

    ServerAPI serverAPI;

    public PaymentPresenter() {
        serverAPI = Dependencies.getServerAPI();
    }

    public void paymentAccessCode(String accessCode) {
        final PaymentView view = view();
        if (view != null) {
            view.showLoadingUI();
        }
        subscriptions.add(serverAPI.paymentAccessCode(accessCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (view != null) {
                        try {
                            view.hideLoadingUI();
                            view.showResultPaymentTicket(response);
                        } catch (Exception ex) {
                        }
                    }

                }, throwable -> {
                    if (view != null) {
                        view.hideLoadingUI();
                        view.showErrorLoadingUI(throwable);
                    }
                }));
    }

    public void paymentCreditCard(String game_id, CardCredit cardCredit) {
        final PaymentView view = view();
        if (view != null) {
            view.showLoadingUI();
        }
        subscriptions.add(serverAPI.paymentCreditCard(game_id, cardCredit.stripe_number, cardCredit.stripe_exp_month,
                cardCredit.stripe_exp_year, cardCredit.stripe_card_code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (view != null) {
                        try {
                            view.hideLoadingUI();
                            view.showResultPaymentCard(response);
                        } catch (Exception ex) {
                        }
                    }

                }, throwable -> {
                    if (view != null) {
                        view.hideLoadingUI();
                        view.showErrorLoadingUI(throwable);
                    }
                }));
    }
}
