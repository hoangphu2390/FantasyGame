package com.fantasygame.data.model.response;

import java.util.List;

/**
 * Created by HP on 19/06/2017.
 */

public class RegisterResponse {
    public boolean result;
    public String message;
    public Data data;

    public class Data{
        public String api_token;
        public String display_name ;
        public String phone_number;
        public String avatar;
        public String address;
        public String email;
    }
}
