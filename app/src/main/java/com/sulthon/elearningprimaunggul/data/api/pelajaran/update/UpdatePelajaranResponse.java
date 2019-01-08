package com.sulthon.elearningprimaunggul.data.api.pelajaran.update;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class UpdatePelajaranResponse {

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
                "UpdatePelajaranResponse{" +
                        "success = '" + success + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}