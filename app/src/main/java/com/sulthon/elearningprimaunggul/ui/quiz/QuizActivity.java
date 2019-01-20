package com.sulthon.elearningprimaunggul.ui.quiz;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sulthon.elearningprimaunggul.CommonHelper;
import com.sulthon.elearningprimaunggul.R;
import com.sulthon.elearningprimaunggul.data.api.nilai.CreateNilaiResponse;
import com.sulthon.elearningprimaunggul.data.api.soal.read.SoalItem;
import com.sulthon.elearningprimaunggul.data.api.soal.read.SoalResponse;
import com.sulthon.elearningprimaunggul.data.sharedpref.SharedPrefLogin;
import com.sulthon.elearningprimaunggul.service.APIRepository;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class QuizActivity extends AppCompatActivity implements Callback<SoalResponse>, View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private Gson gson = new Gson();
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://abangcoding.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    private APIRepository service = retrofit.create(APIRepository.class);
    private ProgressDialog loading;
    private List<SoalItem> soalItems;
    private Button btnSebelum;
    private Button btnLanjut;
    private Button btnSelesai;
    private TextView txtNo;
    private TextView txtPertanyaan;
    private TextView txtNilai;
    private RadioGroup radioGroup;
    private RadioButton rbA;
    private RadioButton rbB;
    private RadioButton rbC;
    private RadioButton rbD;
    private RadioButton rbE;
    private LinearLayout linearSoal;
    private int posisiPertanyaan;
    private String nis;
    private String idQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        SharedPrefLogin session = new SharedPrefLogin(this);
        nis = session.getUserDetails().get(SharedPrefLogin.KEY_ID_USER);

        txtNo = findViewById(R.id.txt_nomor);
        txtPertanyaan = findViewById(R.id.txt_pertanyaan);
        txtNilai = findViewById(R.id.txt_nilai);
        radioGroup = findViewById(R.id.radioGroup);
        rbA = findViewById(R.id.rb_a);
        rbB = findViewById(R.id.rb_b);
        rbC = findViewById(R.id.rb_c);
        rbD = findViewById(R.id.rb_d);
        rbE = findViewById(R.id.rb_e);
        btnSebelum = findViewById(R.id.btn_sebelum);
        btnLanjut = findViewById(R.id.btn_lanjut);
        btnSelesai = findViewById(R.id.btn_selesai);
        linearSoal = findViewById(R.id.linear_soal);

        if (getIntent().getExtras() != null) {
            idQuiz = getIntent().getExtras().getString("idquiz");
            if (idQuiz != null) {
                getAllSoalSiswa(idQuiz);
            } else {
                Toast.makeText(this, "Tidak dapat mengambil id quiz", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Tidak dapat mengambil id quiz", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnSebelum.setOnClickListener(this);
        btnLanjut.setOnClickListener(this);
        btnSelesai.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
    }

    private void getAllSoalSiswa(String idQuiz) {
        if (CommonHelper.checkInternet(this)) {
            loading = new ProgressDialog(this);
            loading.setCancelable(false);
            loading.setMessage("Tunggu sebentar..");
            loading.show();

            Call<SoalResponse> call = service.getAllSoal(nis, idQuiz);
            call.enqueue(this);
        } else {
            Toast.makeText(this, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
        }

    }

    private void createNilai() {
        double jumlahSoal = soalItems.size();
        int jawabanBenar = 0;


        for (SoalItem soalItem : soalItems) {
            if (soalItem.getJwbanPilihan().equals(soalItem.getJawaban())) jawabanBenar++;
        }
        String keterangan = jawabanBenar + " / " + jumlahSoal;
        final double nilai = jawabanBenar / jumlahSoal * 100;
        final int tmpJawabanBenar = jawabanBenar;



        if (CommonHelper.checkInternet(this)) {
            loading = new ProgressDialog(this);
            loading.setCancelable(false);
            loading.setMessage("Tunggu sebentar..");
            loading.show();

            Call<CreateNilaiResponse> call = service.createNilai(String.valueOf(nilai), idQuiz, nis, keterangan);
            call.enqueue(new Callback<CreateNilaiResponse>() {
                @Override
                public void onResponse(@NonNull Call<CreateNilaiResponse> call, @NonNull Response<CreateNilaiResponse> response) {
                    loading.dismiss();
                    if (response.body() != null) {
                        if (response.body().getSuccess() == 1) {
                            nilaiBerhasilDiInput(nilai, tmpJawabanBenar);
                        } else {
                            Toast.makeText(QuizActivity.this, "Respon -> " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(QuizActivity.this, "Server tidak memberikan respon", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<CreateNilaiResponse> call, @NonNull Throwable t) {
                    loading.dismiss();
                    t.printStackTrace();
                    Toast.makeText(QuizActivity.this, "Error create nilai -> " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
        }
    }

    private void nilaiBerhasilDiInput(double nilai, int tmpJawabanBenar) {
        String formatNilai = new DecimalFormat("##.##").format(nilai);

        linearSoal.setVisibility(View.GONE);
        txtNilai.setVisibility(View.VISIBLE);
        String strNilai = "Nilai anda : " + formatNilai + "\n" +
                "Jawaban benar " + tmpJawabanBenar + " dari " + soalItems.size() + ".";
        txtNilai.setText(strNilai);
        //double i2=i/60000;
        //tv.setText(new DecimalFormat("##.##").format(i2));
    }

    private void nilaiBerhasilDiInput(String nilai, String keterangan) {
        linearSoal.setVisibility(View.GONE);
        txtNilai.setVisibility(View.VISIBLE);
        String strNilai = "Nilai anda : " + nilai + "\n" +
                "Jawaban benar " + keterangan + ".";
        txtNilai.setText(strNilai);
    }

    @Override
    public void onResponse(@NonNull Call<SoalResponse> call, @NonNull Response<SoalResponse> response) {
        loading.dismiss();
        if (response.body() != null) {
            if (response.body().getSuccess() == 1) {
                if (response.body().getNilai() != null) {
                    nilaiBerhasilDiInput(response.body().getNilai(), response.body().getKeterangan());
                } else {
                    if (response.body().getSoal().size() == 0) {
                        Toast.makeText(this, "Tidak ada soal untuk hari ini..", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        showAllSoal(response.body().getSoal());
                    }
                }
            } else {
                Toast.makeText(this, "Respon -> " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Server tidak memberikan respon", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onFailure(@NonNull Call<SoalResponse> call, @NonNull Throwable t) {
        loading.dismiss();
        t.printStackTrace();
        Toast.makeText(this, "Error Ambil soal -> " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        finish();
    }

    private void showAllSoal(List<SoalItem> soalSoal) {
        soalItems = soalSoal;
        posisiPertanyaan = 0;
        switchPertanyaan(posisiPertanyaan);
    }

    @SuppressLint("SetTextI18n")
    private void switchPertanyaan(int position) {
        SoalItem soal = soalItems.get(position);

        txtNo.setText((position + 1) + ". ");
        txtPertanyaan.setText(soal.getPertanyaan());
        rbA.setText(soal.getA());
        rbB.setText(soal.getB());
        rbC.setText(soal.getC());
        rbD.setText(soal.getD());
        rbE.setText(soal.getE());
        if (soal.getJwbanPilihan() != null) {
            switch (soal.getJwbanPilihan()) {
                case "jwb_a":
                    radioGroup.check(R.id.rb_a);
                    break;
                case "jwb_b":
                    radioGroup.check(R.id.rb_b);
                    break;
                case "jwb_c":
                    radioGroup.check(R.id.rb_c);
                    break;
                case "jwb_d":
                    radioGroup.check(R.id.rb_d);
                    break;
                case "jwb_e":
                    radioGroup.check(R.id.rb_e);
                    break;
            }
        } else {
            radioGroup.check(-1);
        }
        if (posisiPertanyaan == 0) btnSebelum.setVisibility(View.INVISIBLE);
        else btnSebelum.setVisibility(View.VISIBLE);

        if (posisiPertanyaan == (soalItems.size() - 1)) btnLanjut.setVisibility(View.INVISIBLE);
        else btnLanjut.setVisibility(View.VISIBLE);

        checkAllPertanyaan();
    }

    private void checkAllPertanyaan() {
        int pertanyaanTerjawab = 0;
        for (SoalItem soalItem : soalItems) {
            if (soalItem.getJwbanPilihan() != null) pertanyaanTerjawab++;
        }
        if (pertanyaanTerjawab == soalItems.size()) {
            btnSelesai.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        radioGroup.check(-1);
        switch (view.getId()) {
            case R.id.btn_sebelum:
                posisiPertanyaan--;
                switchPertanyaan(posisiPertanyaan);
                break;
            case R.id.btn_lanjut:
                posisiPertanyaan++;
                switchPertanyaan(posisiPertanyaan);
                break;
            case R.id.btn_selesai:
                createNilai();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rb_a:
                soalItems.get(posisiPertanyaan).setJwbanPilihan("jwb_a");
                break;
            case R.id.rb_b:
                soalItems.get(posisiPertanyaan).setJwbanPilihan("jwb_b");
                break;
            case R.id.rb_c:
                soalItems.get(posisiPertanyaan).setJwbanPilihan("jwb_c");
                break;
            case R.id.rb_d:
                soalItems.get(posisiPertanyaan).setJwbanPilihan("jwb_d");
                break;
            case R.id.rb_e:
                soalItems.get(posisiPertanyaan).setJwbanPilihan("jwb_e");
                break;
        }
        checkAllPertanyaan();
    }
}
