package com.fantasygame.data.model.response;

import java.util.List;

/**
 * Created by HP on 26/09/2017.
 */

public class ProfileSportResponse {

    public class Datum {
        public String id;
        public String code;
        public String name;
        public String season;
        public String description;
        public String active;
        public String created_by;
        public String created_at;
        public String updated_by;
        public String updated_at;
    }

    public boolean result;
    public String message;
    public List<Datum> data;
}
