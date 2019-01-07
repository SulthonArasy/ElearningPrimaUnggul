package com.sulthon.elearningprimaunggul.service;

import com.sulthon.elearningprimaunggul.data.api.login.LoginGuruResponse;
import com.sulthon.elearningprimaunggul.data.api.pelajaran.PelajaranResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIRepository {
    @FormUrlEncoded
    @POST("api/login-guru.php")
    Call<LoginGuruResponse> login(@Field("nig") String nig, @Field("pass") String pass);

    @FormUrlEncoded
    @POST("api/pelajaran/read.php")
    Call<PelajaranResponse> getAllPelajaran(@Field("nig") String nig);

}
