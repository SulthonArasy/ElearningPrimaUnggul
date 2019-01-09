package com.sulthon.elearningprimaunggul.ui.inputsoal;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sulthon.elearningprimaunggul.CommonHelper;
import com.sulthon.elearningprimaunggul.R;
import com.sulthon.elearningprimaunggul.data.api.soal.create.CreateSoalResponse;
import com.sulthon.elearningprimaunggul.data.sharedpref.SharedPrefLogin;
import com.sulthon.elearningprimaunggul.service.APIRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InputSoalActivity extends AppCompatActivity implements View.OnClickListener, Callback<CreateSoalResponse> {

    private String idQuiz;
    private EditText edtPertanyaan, edtA, edtB, edtC, edtD, edtE;
    private RadioGroup radioGroup;
    private ProgressDialog loading;
    private Gson gson = new Gson();
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://abangcoding.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    private APIRepository service = retrofit.create(APIRepository.class);
    private String nig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_soal);

        if (getIntent().getExtras() != null) {
            idQuiz = getIntent().getExtras().getString("idquiz");
            if (idQuiz == null) {
                Toast.makeText(this, "Tidak dapat mengambil id quiz", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Tidak dapat mengambil id quiz", Toast.LENGTH_SHORT).show();
            finish();
        }

        SharedPrefLogin session = new SharedPrefLogin(this);
        nig = session.getUserDetails().get(SharedPrefLogin.KEY_ID_USER);

        edtPertanyaan = findViewById(R.id.edt_pertanyaan);
        edtA = findViewById(R.id.edt_a);
        edtB = findViewById(R.id.edt_b);
        edtC = findViewById(R.id.edt_c);
        edtD = findViewById(R.id.edt_d);
        edtE = findViewById(R.id.edt_e);
        radioGroup = findViewById(R.id.radioGroup);
        Button btnSimpanQuis = findViewById(R.id.btn_simpan);
        btnSimpanQuis.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (edtPertanyaan.getText().toString().equals("")) {
            Toast.makeText(this, "Pertanyaan tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if (edtA.getText().toString().equals("")) {
            Toast.makeText(this, "Jawaban A tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if (edtB.getText().toString().equals("")) {
            Toast.makeText(this, "Jawaban B tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if (edtC.getText().toString().equals("")) {
            Toast.makeText(this, "Jawaban C tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if (edtD.getText().toString().equals("")) {
            Toast.makeText(this, "Jawaban D tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if (edtE.getText().toString().equals("")) {
            Toast.makeText(this, "Jawaban E tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Pilih salah satu jawaban yang benar..", Toast.LENGTH_SHORT).show();
        } else {
            String jawaban;
            switch (radioGroup.getCheckedRadioButtonId()) {
                case R.id.rb_a:
                    jawaban = "jwb_a";
                    break;
                case R.id.rb_b:
                    jawaban = "jwb_b";
                    break;
                case R.id.rb_c:
                    jawaban = "jwb_c";
                    break;
                case R.id.rb_d:
                    jawaban = "jwb_d";
                    break;
                case R.id.rb_e:
                    jawaban = "jwb_e";
                    break;
                default:
                    jawaban = "jwb_a";
                    break;
            }
            createSoal(edtPertanyaan.getText().toString(),
                    edtA.getText().toString(),
                    edtB.getText().toString(),
                    edtC.getText().toString(),
                    edtD.getText().toString(),
                    edtE.getText().toString(),
                    jawaban);
        }
    }

    private void createSoal(String pertanyaan, String jwbA, String jwbB, String jwbC, String jwbD, String jwbE,
                            String jawaban) {
        if (CommonHelper.checkInternet(this)) {
            loading = new ProgressDialog(this);
            loading.setCancelable(false);
            loading.setMessage("Tunggu sebentar..");
            loading.show();

            Call<CreateSoalResponse> call = service.createSoal(nig, idQuiz, pertanyaan, jwbA, jwbB, jwbC, jwbD, jwbE, jawaban);
            call.enqueue(this);
        } else {
            Toast.makeText(this, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onResponse(@NonNull Call<CreateSoalResponse> call, @NonNull Response<CreateSoalResponse> response) {
        loading.dismiss();
        if (response.body() != null) {
            if (response.body().getSuccess() == 1) {
                setResult(RESULT_OK);
                finish();
            }
        } else {
            Toast.makeText(this, "Server tidak memberikan respon", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(@NonNull Call<CreateSoalResponse> call, @NonNull Throwable t) {
        loading.dismiss();
        t.printStackTrace();
        Toast.makeText(this, "Error gan..", Toast.LENGTH_SHORT).show();
    }
}
