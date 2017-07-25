package com.fantasygame.api;


import com.fantasygame.data.model.response.FeatureResponse;
import com.fantasygame.data.model.response.FinishTeamResponse;
import com.fantasygame.data.model.response.HowToPlayResponse;
import com.fantasygame.data.model.response.LoginResponse;
import com.fantasygame.data.model.response.LogoutResponse;
import com.fantasygame.data.model.response.MatchesResponse;
import com.fantasygame.data.model.response.RegisterResponse;
import com.fantasygame.data.model.response.SelectTeamResponse;
import com.fantasygame.data.model.response.SportResponse;
import com.fantasygame.data.model.response.TeamResponse;
import com.fantasygame.data.model.response.TieBreakerResponse;
import com.fantasygame.data.model.response.UserWinnerResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import retrofit.http.Field;
import retrofit.http.FieldMap;
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
    String PATH_GET_LIST_SPORT = "sport";
    String PATH_GET_LIST_ALL_WINNER = "game/weeky";
    String PATH_GET_HOW_TO_PLAY = "game/latest";
    String PATH_GET_MATCHES = "match/latest?limit=5&sport=nfl";
    String PATH_GET_FEATURE = "team/featured";
    String PATH_GET_TEAM_BY_CODE = "team";
    String PATH_GET_LIST_TEAM_SELECT = "game/teams";
    String PATH_GET_TIE_BREAKER = "tie-breaker/get";
    String PATH_GAME_FINISH = "game/finish";

    @FormUrlEncoded
    @POST(PATH_LOGIN)
    Observable<LoginResponse> login(@Field("username") String username,
                                    @Field("password") String pwd);

    @FormUrlEncoded
    @POST(PATH_LOGOUT)
    Observable<LogoutResponse> logout(@Field("api_token") String api_token);

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

    @GET(PATH_GET_LIST_SPORT)
    Observable<SportResponse> getListSport();

    @GET(PATH_GET_LIST_ALL_WINNER)
    Observable<UserWinnerResponse> getAllWinner();

    @GET(PATH_GET_HOW_TO_PLAY)
    Observable<HowToPlayResponse> getHowToPlay(@Query("page") int page,
                                               @Query("limit") int limit,
                                               @Query("sport") String code_sport);

    @GET(PATH_GET_LIST_ALL_WINNER)
        // winner item sport
    Observable<UserWinnerResponse> getAllWinnerOfItemSport(@Query("status") int status,
                                                           @Query("sport") String code_sport);

    @GET(PATH_GET_MATCHES)
    Observable<MatchesResponse> getListMatches(@Query("limit") int limit,
                                               @Query("sport") String code_sport);

    @GET(PATH_GET_FEATURE)
    Observable<FeatureResponse> getListFeature(@Query("page") int page,
                                               @Query("limit") int limit);

    @GET(PATH_GET_TEAM_BY_CODE)
    Observable<FeatureResponse> getListTeamByCode(@Query("page") int page,
                                                  @Query("limit") int limit,
                                                  @Query("sport") String code_sport);

    @GET(PATH_GET_LIST_TEAM_SELECT)
    Observable<SelectTeamResponse> getListTeamSelect(@Query("game_id") int game_id);

    @GET(PATH_GET_TIE_BREAKER)
    Observable<TieBreakerResponse> getTieBreaker(@Query("id") int id);


    @FormUrlEncoded
    @POST(PATH_GAME_FINISH)
    Observable<FinishTeamResponse> betGameTeams(@Field("game_id") String game_id,
                                                @Field("tie_breaker") String tie_breaker,
                                                @Field("selected_teams[3]") String selected_team_one,
                                                @Field("selected_teams[1]") String selected_team_two,
                                                @Field("selected_teams[2]") String selected_team_three,
                                                @Field("selected_teams[4]") String selected_team_four,
                                                @Field("selected_teams[0]") String selected_team_five,
                                                @Field("api_token") String api_token);

    @FormUrlEncoded
    @POST(PATH_GAME_FINISH)
    Observable<FinishTeamResponse> betGameTeams(
            @Field("game_id") String game_id,
            @Field("tie_breaker") String tie_breaker,
            @Field("api_token") String api_token,
            @FieldMap(encoded = true) TreeMap<String, String> selected_teams);

}