package com.fantasygame.data.model.response;

import java.util.List;

/**
 * Created by HP on 20/06/2017.
 */

public class TeamResponse {
    public boolean result;
    public String message;
    public List<Team> data;

    public class Team {
        public int id;
        public String name;
        public String city;
        public String abbreviation;
        public int sport_id;
        public String source;
        public String logo;
        public String rank;
        public String created_by;
        public String created_at;
        public String updated_by;
        public String updated_at;
    }
}
