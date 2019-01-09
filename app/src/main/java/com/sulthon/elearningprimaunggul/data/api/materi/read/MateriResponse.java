package com.sulthon.elearningprimaunggul.data.api.materi.read;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MateriResponse{

	@SerializedName("materi")
	private List<MateriItem> materi;

	@SerializedName("success")
	private int success;

	@SerializedName("message")
	private String message;

	public void setMateri(List<MateriItem> materi){
		this.materi = materi;
	}

	public List<MateriItem> getMateri(){
		return materi;
	}

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

	@Override
 	public String toString(){
		return 
			"MateriResponse{" + 
			"materi = '" + materi + '\'' + 
			",success = '" + success + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}