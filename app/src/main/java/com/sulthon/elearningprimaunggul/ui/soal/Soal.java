package com.sulthon.elearningprimaunggul.ui.soal;

public class Soal {
    private String pertanyaan;
    private String jawaban;

    public Soal(String pertanyaan,String jawaban) {
        this.pertanyaan = pertanyaan;
        this.jawaban = jawaban;

    }

    public String getPertanyaan() {
        return pertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }

    public String getJawaban() {
        return jawaban;
    }

    public void setJawaban(String jawaban) {
        this.jawaban = jawaban;
    }
}
