package com.fantasygame.api;


import com.fantasygame.data.model.response.FeatureResponse;
import com.fantasygame.data.model.response.FinishTeamResponse;
import com.fantasygame.data.model.response.ForgotPasswordResponse;
import com.fantasygame.data.model.response.HowToPlayResponse;
import com.fantasygame.data.model.response.LoginResponse;
import com.fantasygame.data.model.response.LogoutResponse;
import com.fantasygame.data.model.response.MatchesResponse;
import com.fantasygame.data.model.response.PaymentCardResponse;
import com.fantasygame.data.model.response.PaymentTicketResponse;
import com.fantasygame.data.model.response.ProfileGameResponse;
import com.fantasygame.data.model.response.ProfileSportResponse;
import com.fantasygame.data.model.response.RegisterResponse;
import com.fantasygame.data.model.response.ResultResponse;
import com.fantasygame.data.model.response.SelectTeamResponse;
import com.fantasygame.data.model.response.SportResponse;
import com.fantasygame.data.model.response.TeamResponse;
import com.fantasygame.data.model.response.TieBreakerResponse;
import com.fantasygame.data.model.response.UserWinnerResponse;

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
    String PATH_GET_MATCHES = "match/latest";
    String PATH_GET_RESULT = "result";
    String PATH_GET_FEATURE = "team/featured";
    String PATH_GET_TEAM_BY_CODE = "team";
    String PATH_GET_LIST_TEAM_SELECT = "game/teams";
    String PATH_GET_TIE_BREAKER = "tie-breaker/get";
    String PATH_GAME_FINISH = "game/finish";
    String PATH_FORGOT_PASSWORD = "user/forgot-password";
    String PATH_PAYMENT_ACCESS_CODE = "ticket/check";
    String PATH_PAYMENT_CREDIT_CARD = "payment/checkout";

    String PATH_PROFILE_GAMES = "profile/games";
    String PATH_PROFILE_SPORTS = "profile/sports";
    String PATH_PROFILE_CONTACT = "profile/contact";
    String PATH_PROFILE_ABOUT = "profile/about";

    @FormUrlEncoded
    @POST(PATH_LOGIN)
    Observable<LoginResponse> login(@Field("email") String email,
                                    @Field("password") String pwd);

    @FormUrlEncoded
    @POST(PATH_LOGOUT)
    Observable<LogoutResponse> logout(@Field("api_token") String api_token);

    @FormUrlEncoded
    @POST(PATH_REGISTER)
    Observable<RegisterResponse> register(@Field("first_name") String firstname,
                                          @Field("last_name") String lastname,
                                          @Field("password") String pwd,
                                          @Field("email") String email,
                                          @Field("phone_number") String phone_number,
                                          @Field("address") String address,
                                          @Field("display_name") String display_name);

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

    @GET(PATH_GET_MATCHES)
    Observable<MatchesResponse> getListMatches(@Query("limit") int limit,
                                               @Query("sport") String code_sport);

    @GET(PATH_GET_RESULT)
    Observable<ResultResponse> getListResults(@Query("sport") String code_sport);

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
    Observable<FinishTeamResponse> betGameTeams(
            @Field("game_id") String game_id,
            @Field("tie_breaker") String tie_breaker,
            @Field("api_token") String api_token,
            @Field("transaction_id") String transaction_id,
            @Field("card_num") String card_num,
            @Field("access_code") String access_code,
            @FieldMap(encoded = true) TreeMap<String, String> selected_teams);


    @FormUrlEncoded
    @POST(PATH_FORGOT_PASSWORD)
    Observable<ForgotPasswordResponse> forgotPassword(@Field("email") String email);

    @FormUrlEncoded
    @POST(PATH_PAYMENT_ACCESS_CODE)
    Observable<PaymentTicketResponse> paymentAccessCode(@Field("access_code") String access_code);

    @FormUrlEncoded
    @POST(PATH_PAYMENT_CREDIT_CARD)
    Observable<PaymentCardResponse> paymentCreditCard(@Field("game_id") String game_id,
                                                      @Field("card_num") String card_num,
                                                      @Field("exp_date_month") String exp_date_month,
                                                      @Field("exp_date_year") String exp_date_year,
                                                      @Field("card_code") String card_code);

    @GET(PATH_PROFILE_GAMES)
    Observable<ProfileGameResponse> getProfileGames(@Query("uid") String uid);

    @GET(PATH_PROFILE_SPORTS)
    Observable<ProfileSportResponse> getProfileSports(@Query("uid") String uid);
}