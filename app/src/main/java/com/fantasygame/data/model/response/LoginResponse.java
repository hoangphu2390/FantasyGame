package com.fantasygame.data.model.response;

/**
 * Author: Son Vo
 * Date: 1/4/2016.
 */
public class LoginResponse {

    public boolean result;
    public String message;
    public Data data;

    public class Data {
        public String api_token;
    }
}
