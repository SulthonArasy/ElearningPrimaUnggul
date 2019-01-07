package com.sulthon.elearningprimaunggul.data.api.pelajaran.read;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class PelajaranItem{

	@SerializedName("nama")
	private String nama;

	@SerializedName("id")
	private String id;

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	@NonNull
	@Override
 	public String toString(){
		return 
			"PelajaranItem{" + 
			"nama = '" + nama + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}