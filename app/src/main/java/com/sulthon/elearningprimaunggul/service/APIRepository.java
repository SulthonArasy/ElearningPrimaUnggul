package com.sulthon.elearningprimaunggul.service;

import com.sulthon.elearningprimaunggul.data.body.LoginBody;
import com.sulthon.elearningprimaunggul.data.api.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIRepository {
    @POST("api/login-test.php")
    Call<LoginResponse> login(@Body LoginBody body);

}
