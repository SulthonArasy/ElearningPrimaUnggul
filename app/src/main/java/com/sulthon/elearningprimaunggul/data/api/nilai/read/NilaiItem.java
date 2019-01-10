package com.sulthon.elearningprimaunggul.data.api.nilai.read;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class NilaiItem {

    @SerializedName("keterangan")
    private String keterangan;

    @SerializedName("nama_siswa")
    private String namaSiswa;

    @SerializedName("nilai")
    private String nilai;

    @SerializedName("nis")
    private String nis;

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getNamaSiswa() {
        return namaSiswa;
    }

    public void setNamaSiswa(String namaSiswa) {
        this.namaSiswa = namaSiswa;
    }

    public String getNilai() {
        return nilai;
    }

    public void setNilai(String nilai) {
        this.nilai = nilai;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    @NonNull
    @Override
    public String toString() {
        return
                "NilaiItem{" +
                        "keterangan = '" + keterangan + '\'' +
                        ",nama_siswa = '" + namaSiswa + '\'' +
                        ",nilai = '" + nilai + '\'' +
                        ",nis = '" + nis + '\'' +
                        "}";
    }
}