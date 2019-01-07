package com.sulthon.elearningprimaunggul.data.body;

import com.google.gson.annotations.SerializedName;

public class LoginBody {
    @SerializedName("nig")
    private String nig;
    @SerializedName("pass")
    private String pass;

    public LoginBody(String nig, String pass) {
        this.nig = nig;
        this.pass = pass;
    }
}
