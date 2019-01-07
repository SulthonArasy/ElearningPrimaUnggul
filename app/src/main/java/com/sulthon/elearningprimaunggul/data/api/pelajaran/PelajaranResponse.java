package com.sulthon.elearningprimaunggul.data.api.pelajaran;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PelajaranResponse{

	@SerializedName("success")
	private int success;

	@SerializedName("message")
	private String message;

	@SerializedName("pelajaran")
	private List<PelajaranItem> pelajaran;

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

	public void setPelajaran(List<PelajaranItem> pelajaran){
		this.pelajaran = pelajaran;
	}

	public List<PelajaranItem> getPelajaran(){
		return pelajaran;
	}

	@Override
 	public String toString(){
		return 
			"PelajaranResponse{" + 
			"success = '" + success + '\'' + 
			",message = '" + message + '\'' + 
			",pelajaran = '" + pelajaran + '\'' + 
			"}";
		}
}