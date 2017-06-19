package com.fantasygame.api;


import com.fantasygame.data.model.response.LoginResponse;
import com.fantasygame.data.model.response.RegisterResponse;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

public interface ServerAPI {

    public static String SERVER_ADDRESS = "http://grbee.kennjdemo.com/api/";

    String PATH_LOGIN = "user/sign-in";
    String PATH_REGISTER = "user/sign-up";

    @FormUrlEncoded
    @POST(PATH_LOGIN)
    Observable<LoginResponse> login(@Field("username") String username,
                                    @Field("password") String pwd);

    @FormUrlEncoded
    @POST(PATH_REGISTER)
    Observable<RegisterResponse> register(@Field("username") String username,
                                          @Field("password") String pwd,
                                          @Field("display_name") String display_name,
                                          @Field("email") String email,
                                          @Field("phone_number") String phone_number,
                                          @Field("address") String address);
}