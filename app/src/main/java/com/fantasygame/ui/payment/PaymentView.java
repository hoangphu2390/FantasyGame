package com.fantasygame.ui.payment;

import com.fantasygame.base.BaseView;
import com.fantasygame.data.model.response.PaymentCardResponse;
import com.fantasygame.data.model.response.PaymentTicketResponse;

/**
 * Created by HP on 19/09/2017.
 */

public interface PaymentView extends BaseView {
    public void showResultPaymentTicket(PaymentTicketResponse response);

    public void showResultPaymentCard(PaymentCardResponse response);
}
