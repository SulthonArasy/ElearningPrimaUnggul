package com.sulthon.elearningprimaunggul.data.api.soal.readsiswa;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SoalSiswaResponse {

    @SerializedName("soal")
    private List<SoalItem> soal;

    @SerializedName("success")
    private int success;

    @SerializedName("message")
    private String message;

    public List<SoalItem> getSoal() {
        return soal;
    }

    public void setSoal(List<SoalItem> soal) {
        this.soal = soal;
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

    @NonNull
    @Override
    public String toString() {
        return
                "SoalSiswaResponse{" +
                        "soal = '" + soal + '\'' +
                        ",success = '" + success + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}