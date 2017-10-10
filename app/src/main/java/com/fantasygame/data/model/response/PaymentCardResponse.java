package com.fantasygame.data.model.response;

/**
 * Created by HP on 19/09/2017.
 */

public class PaymentCardResponse {
    public Data data;
    public String message;
    public boolean result;

    public static class Data {
        public boolean approved;
        public String transaction_id;
    }
}
