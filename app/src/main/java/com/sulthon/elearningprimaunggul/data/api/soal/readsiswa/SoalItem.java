package com.sulthon.elearningprimaunggul.data.api.soal.readsiswa;

import com.google.gson.annotations.SerializedName;

public class SoalItem {

    @SerializedName("a")
    private String A;

    @SerializedName("b")
    private String B;

    @SerializedName("c")
    private String C;

    @SerializedName("d")
    private String D;

    @SerializedName("e")
    private String E;

    @SerializedName("id")
    private String id;

    @SerializedName("pertanyaan")
    private String pertanyaan;

    @SerializedName("jawaban_pilihan")
    private String jwbanPilihan;

    public String getJwbanPilihan() {
        return jwbanPilihan;
    }

    public void setJwbanPilihan(String jwbanPilihan) {
        this.jwbanPilihan = jwbanPilihan;
    }

    public String getA() {
        return A;
    }

    public void setA(String A) {
        this.A = A;
    }

    public String getB() {
        return B;
    }

    public void setB(String B) {
        this.B = B;
    }

    public String getC() {
        return C;
    }

    public void setC(String C) {
        this.C = C;
    }

    public String getD() {
        return D;
    }

    public void setD(String D) {
        this.D = D;
    }

    public String getE() {
        return E;
    }

    public void setE(String E) {
        this.E = E;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPertanyaan() {
        return pertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }

    @Override
    public String toString() {
        return
                "SoalItem{" +
                        "a = '" + A + '\'' +
                        ",b = '" + B + '\'' +
                        ",c = '" + C + '\'' +
                        ",d = '" + D + '\'' +
                        ",e = '" + E + '\'' +
                        ",id = '" + id + '\'' +
                        ",pertanyaan = '" + pertanyaan + '\'' +
                        "}";
    }
}