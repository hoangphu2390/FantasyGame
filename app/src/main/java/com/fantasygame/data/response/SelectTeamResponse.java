package com.fantasygame.data.model.response;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by HP on 17/07/2017.
 */
@Parcel
public class SelectTeamResponse {

    @Parcel
    public static class Team {
        public String id;
        public String season;
        public String code;
        public String name;
        public String sport;
        public String city;
        public String logo;
        public String rank;
        public String score;
        public String created_by;
        public String created_at;
        public String updated_by;
        public String updated_at;
    }

    @Parcel
    public static class Datum {
        public List<Team> teams;
    }

    public boolean result;
    public String message;
    public List<Datum> data;
}
