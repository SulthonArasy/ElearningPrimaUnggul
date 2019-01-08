package com.sulthon.elearningprimaunggul.service;

import com.sulthon.elearningprimaunggul.data.api.login.LoginGuruResponse;
import com.sulthon.elearningprimaunggul.data.api.materi.MateriResponse;
import com.sulthon.elearningprimaunggul.data.api.materi.create.CreateMateriResponse;
import com.sulthon.elearningprimaunggul.data.api.pelajaran.create.CreatePelajaranResponse;
import com.sulthon.elearningprimaunggul.data.api.pelajaran.delete.DeletePelajaranResponse;
import com.sulthon.elearningprimaunggul.data.api.pelajaran.read.PelajaranResponse;
import com.sulthon.elearningprimaunggul.data.api.pelajaran.update.UpdatePelajaranResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIRepository {
    @FormUrlEncoded
    @POST("api/login-guru.php")
    Call<LoginGuruResponse> login(
            @Field("nig") String nig,
            @Field("pass") String pass);

    @FormUrlEncoded
    @POST("api/pelajaran/read.php")
    Call<PelajaranResponse> getAllPelajaran(
            @Field("nig") String nig);

    @FormUrlEncoded
    @POST("api/pelajaran/create.php")
    Call<CreatePelajaranResponse> createPelajaran(
            @Field("nig") String nig,
            @Field("nama") String namaPelajaran);

    @FormUrlEncoded
    @POST("api/pelajaran/update.php")
    Call<UpdatePelajaranResponse> updatePelajaran(
            @Field("nig") String nig,
            @Field("nama") String namaPelajaran,
            @Field("id") String idpel);

    @FormUrlEncoded
    @POST("api/pelajaran/delete.php")
    Call<DeletePelajaranResponse> deletePelajaran(
            @Field("nig") String nig,
            @Field("id") String idpel);

    @FormUrlEncoded
    @POST("api/materi/read.php")
    Call<MateriResponse> getAllMateri(
            @Field("nig") String nig,
            @Field("idpel") String idpel);

    @Multipart
    @POST("api/materi/create.php")
    Call<CreateMateriResponse> createMateri(
            @Part("nig") RequestBody nig,
            @Part("id_pelajaran") RequestBody idpel,
            @Part("materi") RequestBody namaMateri,
            @Part MultipartBody.Part file);

}
