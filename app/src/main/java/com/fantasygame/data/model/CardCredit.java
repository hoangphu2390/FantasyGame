package com.fantasygame.data.model;

/**
 * Created by HP on 31/08/2017.
 */

public class CardCredit {
    public int id;
    public String type;
    public String stripe_number;
    public String stripe_exp_month;
    public String stripe_exp_year;
    public String stripe_card_code;
    public String email;
    public boolean isGetFromServer;
}
