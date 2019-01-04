package com.sulthon.elearningprimaunggul.ui.nilai;

/**
 * Created by Dimas Maulana on 5/26/17.
 * Email : araymaulana66@gmail.com
 */

public class Nilai {

    private String nis;
    private String nama;
    private String nilai;

    public Nilai(String nis, String nama, String nilai) {
        this.nis = nis;
        this.nama = nama;
        this.nilai = nilai;

    }

    public String getNis() {
        return nis;
    }

    public void getNis(String nis) {
        this.nis = nis;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNilai() {
        return nilai;
    }

    public void getNilai(String nila) {
        this.nilai = nilai;
    }
}