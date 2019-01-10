package com.sulthon.elearningprimaunggul.service;

import com.sulthon.elearningprimaunggul.data.api.login.LoginGuruResponse;
import com.sulthon.elearningprimaunggul.data.api.login.LoginSiswaResponse;
import com.sulthon.elearningprimaunggul.data.api.materi.create.CreateMateriResponse;
import com.sulthon.elearningprimaunggul.data.api.materi.delete.DeleteMateriResponse;
import com.sulthon.elearningprimaunggul.data.api.materi.read.MateriResponse;
import com.sulthon.elearningprimaunggul.data.api.materi.update.UpdateMateriResponse;
import com.sulthon.elearningprimaunggul.data.api.nilai.CreateNilaiResponse;
import com.sulthon.elearningprimaunggul.data.api.pelajaran.create.CreatePelajaranResponse;
import com.sulthon.elearningprimaunggul.data.api.pelajaran.delete.DeletePelajaranResponse;
import com.sulthon.elearningprimaunggul.data.api.pelajaran.read.PelajaranResponse;
import com.sulthon.elearningprimaunggul.data.api.pelajaran.update.UpdatePelajaranResponse;
import com.sulthon.elearningprimaunggul.data.api.soal.create.CreateSoalResponse;
import com.sulthon.elearningprimaunggul.data.api.soal.delete.DeleteSoalResponse;
import com.sulthon.elearningprimaunggul.data.api.soal.read.SoalResponse;

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
    Call<LoginGuruResponse> loginGuru(
            @Field("nig") String nig,
            @Field("pass") String pass);

    @FormUrlEncoded
    @POST("api/login-siswa.php")
    Call<LoginSiswaResponse> loginSiswa(
            @Field("nis") String nis,
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

    @FormUrlEncoded
    @POST("api/materi/update.php")
    Call<UpdateMateriResponse> updateMateri(
            @Field("nig") String nig,
            @Field("nama") String namaMateri,
            @Field("id") String idMateri);

    @FormUrlEncoded
    @POST("api/materi/delete.php")
    Call<DeleteMateriResponse> deleteMateri(
            @Field("nig") String nig,
            @Field("id") String idMateri);

    @FormUrlEncoded
    @POST("api/soal/read.php")
    Call<SoalResponse> getAllSoal(
            @Field("nig") String nig,
            @Field("id_quiz") String idQuiz);

    @FormUrlEncoded
    @POST("api/soal/create.php")
    Call<CreateSoalResponse> createSoal(
            @Field("nig") String nig,
            @Field("id_quiz") String idQuiz,
            @Field("pertanyaan") String pertanyaan,
            @Field("a") String jawabA,
            @Field("b") String jawabB,
            @Field("c") String jawabC,
            @Field("d") String jawabD,
            @Field("e") String jawabE,
            @Field("jawaban") String jawaban);

    @FormUrlEncoded
    @POST("api/soal/delete.php")
    Call<DeleteSoalResponse> deleteSoal(
            @Field("nig") String nig,
            @Field("id_soal") String idSoal);

    @FormUrlEncoded
    @POST("api/nilai/create.php")
    Call<CreateNilaiResponse> createNilai(
            @Field("nilai") String nilai,
            @Field("id_quiz") String idQuiz,
            @Field("nis") String nis,
            @Field("keterangan") String ket);

}
