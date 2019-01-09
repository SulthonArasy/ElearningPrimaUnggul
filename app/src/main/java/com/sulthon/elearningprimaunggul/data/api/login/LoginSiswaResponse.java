package com.sulthon.elearningprimaunggul.data.api.login;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class LoginSiswaResponse {

    @SerializedName("kls_siswa")
    private String klsSiswa;

    @SerializedName("success")
    private int success;

    @SerializedName("message")
    private String message;

    @SerializedName("nm_siswa")
    private String nmSiswa;

    @SerializedName("token")
    private String token;

    public String getKlsSiswa() {
        return klsSiswa;
    }

    public void setKlsSiswa(String klsSiswa) {
        this.klsSiswa = klsSiswa;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNmSiswa() {
        return nmSiswa;
    }

    public void setNmSiswa(String nmSiswa) {
        this.nmSiswa = nmSiswa;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @NonNull
    @Override
    public String toString() {
        return
                "LoginSiswaResponse{" +
                        "kls_siswa = '" + klsSiswa + '\'' +
                        ",success = '" + success + '\'' +
                        ",message = '" + message + '\'' +
                        ",nm_siswa = '" + nmSiswa + '\'' +
                        ",token = '" + token + '\'' +
                        "}";
    }
}