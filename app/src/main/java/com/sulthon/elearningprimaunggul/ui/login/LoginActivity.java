package com.sulthon.elearningprimaunggul.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sulthon.elearningprimaunggul.R;
import com.sulthon.elearningprimaunggul.data.api.login.LoginGuruResponse;
import com.sulthon.elearningprimaunggul.data.sharedpref.SharedPrefLogin;
import com.sulthon.elearningprimaunggul.service.APIRepository;
import com.sulthon.elearningprimaunggul.ui.about.AboutActivity;
import com.sulthon.elearningprimaunggul.ui.pelajaran.PelajaranActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Callback<LoginGuruResponse> {

    /**
     * guru = false, siswa = true
     */
    private Boolean isSiswa;
    private EditText edtUser;
    private TextView txtGuru;
    private TextView txtSiswa;
    private ProgressDialog loading;
    private TextView txtUser;
    private EditText edtPass;
    private SharedPrefLogin session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SharedPrefLogin(this);

        edtUser = findViewById(R.id.edt_user);
        edtPass = findViewById(R.id.edt_password);
        Button btnLogin = findViewById(R.id.btn_login);
        Button btnAbout = findViewById(R.id.btn_about);
        txtGuru = findViewById(R.id.txt_guru);
        txtSiswa = findViewById(R.id.txt_siswa);
        txtUser = findViewById(R.id.txt_user);

        if (session.isLoggedIn()){
            startActivity(new Intent(LoginActivity.this, PelajaranActivity.class));
            finish();
            return;
        }

        // pertama kali activity terbuat akan langsung set sebagai guru
        isSiswa = false;
        txtGuru.setBackgroundColor(Color.GREEN);
        txtUser.setText(R.string.NIGy);
        edtUser.setHint(R.string.NIGy);
        //============================================================

        txtGuru.setOnClickListener(this);
        txtSiswa.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnAbout.setOnClickListener(this);
    }

    private void ambilData(String user, String password) {
        loading = new ProgressDialog(this);
        loading.setCancelable(false);
        loading.setMessage("Tunggu Sebentar....");
        loading.show();
        Gson gson = new Gson();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://abangcoding.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        APIRepository service = retrofit.create(APIRepository.class);
        Call<LoginGuruResponse> result = service.login(user, password);

        result.enqueue(this);
    }

    @Override
    public void onResponse(@NonNull Call<LoginGuruResponse> call, @NonNull Response<LoginGuruResponse> response) {
        loading.dismiss();
        if (response.body() != null) {
            if (response.body().getSuccess() == 1) {
                LoginGuruResponse data = response.body();
                session.saveLogin(edtUser.getText().toString(), data.getNmGuru(), data.getToken());

                Toast.makeText(this, "Berhasil login", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, PelajaranActivity.class));
                finish();
            } else {
                Toast.makeText(this, response.body().getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Server tidak memberikan respon", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(@NonNull Call<LoginGuruResponse> call, @NonNull Throwable t) {
        loading.dismiss();
        t.printStackTrace();
        Toast.makeText(this, "Error gan..", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.txt_siswa:
                isSiswa = true;
                txtSiswa.setBackgroundColor(Color.GREEN);
                txtSiswa.setTextColor(Color.WHITE);
                txtUser.setText(R.string.nisy);
                edtUser.setHint(R.string.nisy);
                txtGuru.setBackgroundColor(Color.GRAY);
                txtGuru.setTextColor(Color.BLACK);
                break;
            case R.id.txt_guru:
                isSiswa = false;
                txtGuru.setBackgroundColor(Color.GREEN);
                txtGuru.setTextColor(Color.WHITE);
                txtUser.setText(R.string.NIGy);
                edtUser.setHint(R.string.NIGy);
                txtSiswa.setBackgroundColor(Color.GRAY);
                txtSiswa.setTextColor(Color.BLACK);
                break;
            case R.id.btn_login:
                //jika isSiswa false, login guru
                if (!isSiswa) {
                    String user = edtUser.getText().toString().trim();
                    String pass = edtPass.getText().toString().trim();
                    if (user.isEmpty()) {
                        Toast.makeText(this, "NIG tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                    } else if (pass.isEmpty()) {
                        Toast.makeText(this, "Kata sandi tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                    } else {
                        ambilData(edtUser.getText().toString(), edtPass.getText().toString());
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Anda bukan seorang guru", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}

