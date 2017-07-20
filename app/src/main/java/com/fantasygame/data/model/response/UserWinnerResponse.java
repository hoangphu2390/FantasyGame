package com.fantasygame.data.model.response;

import java.util.List;

/**
 * Created by HP on 23/06/2017.
 */

public class UserWinnerResponse {

    public boolean result;
    public String message;
    public List<Datum> data;

    public static class Datum {
        public String image;
        public int background;
        public String player;
        public String description;
        public String start_date;
        public String end_date;
    }
}
