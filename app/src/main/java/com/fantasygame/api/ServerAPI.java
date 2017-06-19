package com.fantasygame.api;


import com.fantasygame.data.model.response.UserLoginResponse;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

public interface ServerAPI {

    public static String SERVER_ADDRESS = "http://api.menwi.com/api/";
    String PATH_LOGIN = "auth/login";

    @FormUrlEncoded
    @POST(PATH_LOGIN)
    Observable<UserLoginResponse> login(@Field("email") String email,
                                        @Field("password") String pwd);
}