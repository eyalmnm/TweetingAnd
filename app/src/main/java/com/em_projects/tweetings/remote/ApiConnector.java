package com.em_projects.tweetings.remote;

import com.em_projects.tweetings.model.RegionsModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiConnector {

    // Note: region and sex fields should return only numbers
    // 1 - male
    // 2 - female
    // ids - business id
    // dateReg - 2009-10-20

    @GET("api/login.php")
    Call<String> loginRequest(@Query("token") String token,
                              @Query("email") String email,
                              @Query("password") String password);

    @GET("api/logout.php")
    Call<String> logoutRequest(@Query("token") String token,
                               @Query("userkey") String userkey);

    @GET("api/register.php")
    Call<String> registrationRequest(@Query("token") String token,
                                     @Query("email") String email,
                                     @Query("name") String name,
                                     @Query("phone") String phone,
                                     @Query("dateReg") String dateReg,
                                     @Query("address") String address,
                                     @Query("password") String password,
                                     @Query("password2") String password2,
                                     @Query("agree") int agree,
                                     @Query("agree2") int agree2);

    @GET("api/regions.php")
    Call<RegionsModel> regionsRequest(@Query("token") String token);

    @GET("api/restore.php")
    Call<String> passwordResetRequest(@Query("token") String token,
                                      @Query("email") String email);

    @GET("api/userEdit.php")
    Call<String> editUserDataRequest(@Query("token") String token,
                                     @Query("email") String email,
                                     @Query("name") String name,
                                     @Query("phone") String phone,
                                     @Query("dateReg") String dateReg,
                                     @Query("address") String address,
                                     @Query("password") String password,
                                     @Query("newpassword1") String newpassword1,
                                     @Query("newpassword2") String newpassword2,
                                     @Query("sex") int sex,
                                     @Query("userkey") String userkey);

    @GET("api/userInfo.php")
    Call<String> userInfoRequest(@Query("token") String token,
                                 @Query("userkey") String userkey);

    @GET("api/userLgoti.php")
    Call<String> usersBusinessRequest(@Query("token") String token,
                                      @Query("userkey") String userkey);

    @GET("api/userLgotiRemove.php")
    Call<String> removeBusinessRequest(@Query("token") String token,
                                       @Query("userkey") String userkey,
                                       @Query("ids") String ids);
}