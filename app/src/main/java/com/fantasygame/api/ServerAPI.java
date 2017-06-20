package com.fantasygame.api;


import com.fantasygame.data.model.response.LoginResponse;
import com.fantasygame.data.model.response.LogoutResponse;
import com.fantasygame.data.model.response.RegisterResponse;
import com.fantasygame.data.model.response.TeamResponse;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

public interface ServerAPI {

    public static String SERVER_ADDRESS = "http://grbee.kennjdemo.com/api/";

    String PATH_LOGIN = "user/sign-in";
    String PATH_REGISTER = "user/sign-up";
    String PATH_LOGOUT = "user/sign-out";
    String PATH_GET_LIST_TEAM = "team/featured";

    @FormUrlEncoded
    @POST(PATH_LOGIN)
    Observable<LoginResponse> login(@Field("username") String username,
                                    @Field("password") String pwd);

    @POST(PATH_LOGOUT)
    Observable<LogoutResponse> logout();

    @FormUrlEncoded
    @POST(PATH_REGISTER)
    Observable<RegisterResponse> register(@Field("username") String username,
                                          @Field("password") String pwd,
                                          @Field("display_name") String display_name,
                                          @Field("email") String email,
                                          @Field("phone_number") String phone_number,
                                          @Field("address") String address);

    @GET(PATH_GET_LIST_TEAM)
    Observable<TeamResponse> getListTeam(@Query("page") int page,
                                         @Query("limit") int limit);
}