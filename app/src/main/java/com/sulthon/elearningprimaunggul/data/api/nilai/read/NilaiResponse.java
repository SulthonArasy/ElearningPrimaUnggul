package com.sulthon.elearningprimaunggul.data.api.nilai.read;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NilaiResponse {

    @SerializedName("success")
    private int success;

    @SerializedName("nilai")
    private List<NilaiItem> nilai;

    @SerializedName("message")
    private String message;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public List<NilaiItem> getNilai() {
        return nilai;
    }

    public void setNilai(List<NilaiItem> nilai) {
        this.nilai = nilai;
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
                "NilaiResponse{" +
                        "success = '" + success + '\'' +
                        ",nilai = '" + nilai + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}