package com.sulthon.elearningprimaunggul.service;

import com.sulthon.elearningprimaunggul.data.api.login.LoginGuruResponse;
import com.sulthon.elearningprimaunggul.data.api.materi.MateriResponse;
import com.sulthon.elearningprimaunggul.data.api.pelajaran.create.CreatePelajaranResponse;
import com.sulthon.elearningprimaunggul.data.api.pelajaran.read.PelajaranResponse;

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

    @FormUrlEncoded
    @POST("api/pelajaran/create.php")
    Call<CreatePelajaranResponse> createPelajaran(@Field("nig") String nig, @Field("nama") String namaPelajaran);

    @FormUrlEncoded
    @POST("api/materi/read.php")
    Call<MateriResponse> getAllMateri(@Field("nig") String nig, @Field("idpel") String idpel);

}
