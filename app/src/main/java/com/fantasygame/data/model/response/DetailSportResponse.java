package com.fantasygame.data.model.response;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by HP on 22/06/2017.
 */

public class DetailSportResponse {

    public String message;
    public boolean result;
    public Data data;

    @Parcel
    public static class Data{
        public int id;
        public String name;
        public String code;
        public String created_by;
        public String created_at;
        public String updated_by;
        public String updated_at;
        public List<Winner> winners;
    }

    @Parcel
    public static class Winner{
        public String start_date;
        public String end_date;
        public Team team;
        public String price;
    }

    @Parcel
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
