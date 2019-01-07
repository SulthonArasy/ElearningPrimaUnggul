package com.sulthon.elearningprimaunggul.ui.pelajaran;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.sulthon.elearningprimaunggul.R;
import com.sulthon.elearningprimaunggul.data.api.pelajaran.PelajaranResponse;
import com.sulthon.elearningprimaunggul.data.sharedpref.SharedPrefLogin;
import com.sulthon.elearningprimaunggul.service.APIRepository;
import com.sulthon.elearningprimaunggul.ui.uploadmateri.UploadMateriActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PelajaranActivity extends AppCompatActivity implements View.OnClickListener, Callback<PelajaranResponse> {
    private SharedPrefLogin session;
    private RecyclerView recyclerView;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pelajaran);

        session = new SharedPrefLogin(this);

        Button btnBuatPelajaran = findViewById(R.id.btn_buat_pelajaran);
        recyclerView = findViewById(R.id.recycler_pelajaran);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getPelajaran();

        btnBuatPelajaran.setOnClickListener(this);
    }

    @Override
    public void onResponse(@NonNull Call<PelajaranResponse> call, @NonNull Response<PelajaranResponse> response) {
        loading.dismiss();
        if (response.body()!=null){
            if (response.body().getSuccess()==1){
                setAdapterRecycler(response.body());
            }
        } else {
            Toast.makeText(this, "Server tidak memberikan respon", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(@NonNull Call<PelajaranResponse> call, @NonNull Throwable t) {
        loading.dismiss();
        t.printStackTrace();
        Toast.makeText(this, "Error gan..", Toast.LENGTH_SHORT).show();
    }

    private void setAdapterRecycler(PelajaranResponse response) {
        PelajaranAdapter adapter = new PelajaranAdapter(getApplicationContext(), response.getPelajaran());
        recyclerView.setAdapter(adapter);
    }

    private void getPelajaran() {
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
        Call<PelajaranResponse> call = service.getAllPelajaran(session.getUserDetails().get(SharedPrefLogin.KEY_ID_USER));
        call.enqueue(this);
    }


    @Override
    public void onClick(View view) {
        new TedPermission(PelajaranActivity.this)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        startActivity(new Intent(PelajaranActivity.this, UploadMateriActivity.class));
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        Toast.makeText(PelajaranActivity.this, "Kami memerlukan izin tersebut..", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .setPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }
}
