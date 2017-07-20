package com.fantasygame.data.model.response;

/**
 * Created by HP on 17/07/2017.
 */

public class TieBreakerResponse {

    public class Data {
        public String id;
        public String question;
        public String created_by;
        public String created_at;
        public String updated_by;
        public String updated_at;
    }

    public boolean result;
    public String message;
    public Data data;
}
