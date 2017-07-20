package com.fantasygame.data.model.response;

import java.util.List;

/**
 * Created by HP on 14/07/2017.
 */

public class FeatureResponse {

    public boolean result;
    public String message;
    public List<Datum> data;

    public class Datum {
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
        public String image;
    }
}
