package com.sulthon.elearningprimaunggul.data.api.pelajaran.delete;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class DeletePelajaranResponse {

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
                "DeletePelajaranResponse{" +
                        "success = '" + success + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}