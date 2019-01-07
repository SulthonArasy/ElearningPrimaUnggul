package com.sulthon.elearningprimaunggul.data.api;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class LoginGuruResponse {

	@SerializedName("success")
	private int success;

	@SerializedName("nm_guru")
	private String nmGuru;

	@SerializedName("message")
	private String message;

	@SerializedName("token")
	private String token;

	public void setSuccess(int success){
		this.success = success;
	}

	public int getSuccess(){
		return success;
	}

	public void setNmGuru(String nmGuru){
		this.nmGuru = nmGuru;
	}

	public String getNmGuru(){
		return nmGuru;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	@NonNull
    @Override
 	public String toString(){
		return 
			"LoginGuruResponse{" +
			"success = '" + success + '\'' + 
			",nm_guru = '" + nmGuru + '\'' + 
			",message = '" + message + '\'' + 
			",token = '" + token + '\'' + 
			"}";
		}
}