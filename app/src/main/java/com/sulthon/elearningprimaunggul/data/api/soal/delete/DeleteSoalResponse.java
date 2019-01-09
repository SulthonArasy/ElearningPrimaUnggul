package com.sulthon.elearningprimaunggul.data.api.soal.delete;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class DeleteSoalResponse {

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

    public void setMessage(String message) {
        this.message = message;
    }

    @NonNull
    @Override
    public String toString() {
        return
                "DeleteSoalResponse{" +
                        "success = '" + success + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}