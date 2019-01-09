package com.sulthon.elearningprimaunggul.data.api.soal.read;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SoalResponse {

    @SerializedName("soal")
    private List<SoalItem> soal;

    @SerializedName("success")
    private int success;

    @SerializedName("message")
    private String message;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String soal) {
        this.message = soal;
    }

    public List<SoalItem> getSoal() {
        return soal;
    }

    public void setSoal(List<SoalItem> soal) {
        this.soal = soal;
    }

    @NonNull
    @Override
    public String toString() {
        return
                "SoalResponse{" +
                        "soal = '" + soal + '\'' +
                        ",success = '" + success + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}