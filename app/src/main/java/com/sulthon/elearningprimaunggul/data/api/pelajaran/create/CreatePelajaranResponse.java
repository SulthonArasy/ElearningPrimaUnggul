package com.sulthon.elearningprimaunggul.data.api.pelajaran.create;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class CreatePelajaranResponse{

	@SerializedName("success")
	private int success;

	@SerializedName("message")
	private String message;

	public void setSuccess(int success){
		this.success = success;
	}

	public int getSuccess(){
		return success;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@NonNull
    @Override
 	public String toString(){
		return 
			"CreatePelajaranResponse{" + 
			"success = '" + success + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}