package com.sulthon.elearningprimaunggul.service;

import com.sulthon.elearningprimaunggul.data.body.LoginBody;
import com.sulthon.elearningprimaunggul.data.api.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIRepository {
    @FormUrlEncoded
    @POST("api/login-guru.php")
    Call<LoginResponse> login(@Field("nig") String nig, @Field("pass") String pass);

}
