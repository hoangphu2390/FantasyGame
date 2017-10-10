package com.fantasygame.data.model.response;

import java.util.List;

/**
 * Created by HP on 13/07/2017.
 */

public class HowToPlayResponse {

    public boolean result;
    public String message;
    public List<Datum> data;

    public class Datum {
        public String id;
        public String sport;
        public String price;
        public String season;
        public String name;
        public String description;
        public String number_of_teams;
        public String image;
        public String hint;
        public String start_date;
        public String end_date;
        public String prize_id;
        public String status;
        public Winner winner;
        public String tie_breaker_id;
        public String winner_image;
        public String winner_description;
        public String congratulation;
        public String created_by;
        public String created_at;
        public String updated_by;
        public String updated_at;
    }

    public static class Winner{
        public String start_date;
        public String end_date;
        public Team team;
        public String price;
    }

    public static class Team {
        public int id;
        public String name;
        public String description;
        public String date;
        public String city;
        public String abbreviation;
        public int sport_id;
        public String source;
        public String point;
        public String logo;
        public String rank;
        public String created_by;
        public String created_at;
        public String updated_by;
        public String updated_at;
    }
}