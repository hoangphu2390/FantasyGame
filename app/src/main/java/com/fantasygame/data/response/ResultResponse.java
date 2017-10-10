package com.fantasygame.data.model.response;

import java.util.List;

/**
 * Created by HP on 14/07/2017.
 */

public class ResultResponse {

    public boolean result;
    public String message;
    public List<Datum> data;

    public class Datum {
        public String id;
        public String user_id;
        public String game_id;
        public String tie_breaker;
        public String sport;
        public String season;
        public String score;
        public String last_edited_date;
        public String created_by;
        public String created_at;
        public String updated_by;
        public String updated_at;
        public String username;
        public String gamename;
    }
}
