package com.kalkinemedia;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MyApi {
    @FormUrlEncoded
    @POST("api-adv.php")
    Call<ResponseBody> sendotp(
            @Field("username") String username,
            @Field("password") String password,
            @Field("to") String to,
            @Field("from") String from,
            @Field("message") String message,
            @Field("ref") String ref,
            @Field("maxsplit") String maxsplit
    );

    @FormUrlEncoded
    @POST("register.php")
    Call<ResponseBody> registeruser(
            @Field("nameid") String name,
            @Field("emailid") String email,
            @Field("mobileid") String mobile

    );


}
