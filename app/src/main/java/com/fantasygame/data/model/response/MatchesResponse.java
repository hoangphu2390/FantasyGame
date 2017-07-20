package com.fantasygame.data.model.response;

import java.util.List;

/**
 * Created by HP on 13/07/2017.
 */

public class MatchesResponse {

    public boolean result;
    public String message;
    public List<Datum> data;

    public class Datum {
        public String id;
        public String season;
        public String sport;
        public String datetime;
        public String location;
        public String away_team;
        public String home_team;
        public String is_unplayed;
        public String is_in_progress;
        public String is_completed;
        public double away_score;
        public double away_shots;
        public double home_score;
        public double home_shots;
        public String created_by;
        public String created_at;
        public String updated_by;
        public String updated_at;
        public String home_team_name;
        public String home_team_logo;
        public String away_team_name;
        public String away_team_logo;
    }
}
