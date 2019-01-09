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
import com.sulthon.elearningprimaunggul.CommonHelper;
import com.sulthon.elearningprimaunggul.R;
import com.sulthon.elearningprimaunggul.data.api.login.LoginGuruResponse;
import com.sulthon.elearningprimaunggul.data.api.login.LoginSiswaResponse;
import com.sulthon.elearningprimaunggul.data.sharedpref.SharedPrefLogin;
import com.sulthon.elearningprimaunggul.service.APIRepository;
import com.sulthon.elearningprimaunggul.ui.about.AboutActivity;
import com.sulthon.elearningprimaunggul.ui.pelajaran.ListPelajaranActivity;

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
    private Gson gson = new Gson();
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://abangcoding.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    private APIRepository service = retrofit.create(APIRepository.class);

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

        if (session.isLoggedIn()) {
            startActivity(new Intent(LoginActivity.this, ListPelajaranActivity.class));
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

    private void loginSiswa(String user, String password) {
        if (CommonHelper.checkInternet(this)) {
            loading = new ProgressDialog(this);
            loading.setCancelable(false);
            loading.setMessage("Tunggu Sebentar....");
            loading.show();
            Call<LoginSiswaResponse> result = service.loginSiswa(user, password);

            result.enqueue(new Callback<LoginSiswaResponse>() {
                @Override
                public void onResponse(@NonNull Call<LoginSiswaResponse> call, @NonNull Response<LoginSiswaResponse> response) {
                    loading.dismiss();
                    if (response.body() != null) {
                        if (response.body().getSuccess() == 1) {
                            LoginSiswaResponse data = response.body();
                            if (data != null) {
                                session.saveLogin(edtUser.getText().toString(), data.getNmSiswa(), data.getToken(), "siswa");

                                Toast.makeText(LoginActivity.this, "Berhasil login", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, ListPelajaranActivity.class));
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Data Login Siswa null", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Server tidak memberikan respon", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<LoginSiswaResponse> call, @NonNull Throwable t) {
                    loading.dismiss();
                    t.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Error gan..", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
        }
    }

    private void loginGuru(String user, String password) {
        if (CommonHelper.checkInternet(this)) {
            loading = new ProgressDialog(this);
            loading.setCancelable(false);
            loading.setMessage("Tunggu Sebentar....");
            loading.show();
            Call<LoginGuruResponse> result = service.loginGuru(user, password);

            result.enqueue(this);
        } else {
            Toast.makeText(this, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(@NonNull Call<LoginGuruResponse> call, @NonNull Response<LoginGuruResponse> response) {
        loading.dismiss();
        if (response.body() != null) {
            if (response.body().getSuccess() == 1) {
                LoginGuruResponse data = response.body();
                if (data != null) {
                    session.saveLogin(edtUser.getText().toString(), data.getNmGuru(), data.getToken(), "guru");

                    Toast.makeText(this, "Berhasil login", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, ListPelajaranActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Data Login Guru null", Toast.LENGTH_SHORT).show();
                }
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
                String user = edtUser.getText().toString().trim();
                String pass = edtPass.getText().toString().trim();

                if (user.isEmpty()) {
                    Toast.makeText(this, "NIG tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (pass.isEmpty()) {
                    Toast.makeText(this, "Kata sandi tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else {
                    if (isSiswa) {  //jika true login ke siswa, jika false login ke guru
                        loginSiswa(user, pass);
                    } else {
                        loginGuru(user, pass);
                    }
                }
                break;
        }
    }
}

