package com.sulthon.elearningprimaunggul.ui.pelajaran;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.sulthon.elearningprimaunggul.CommonHelper;
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

public class PelajaranActivity extends AppCompatActivity implements View.OnClickListener, Callback<PelajaranResponse>, SwipeRefreshLayout.OnRefreshListener {
    private SharedPrefLogin session;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pelajaran);

        session = new SharedPrefLogin(this);

        Button btnBuatPelajaran = findViewById(R.id.btn_buat_pelajaran);
        TextView txtLogOut = findViewById(R.id.txt_log_out);
        refreshLayout = findViewById(R.id.swipe_refresh);
        recyclerView = findViewById(R.id.recycler_pelajaran);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        refreshLayout.setRefreshing(true);
        onRefresh();

        btnBuatPelajaran.setOnClickListener(this);
        txtLogOut.setOnClickListener(this);
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        getPelajaran();
    }

    @Override
    public void onResponse(@NonNull Call<PelajaranResponse> call, @NonNull Response<PelajaranResponse> response) {
        refreshLayout.setRefreshing(false);
        if (response.body() != null) {
            if (response.body().getSuccess() == 1) {
                setAdapterRecycler(response.body());
            }
        } else {
            Toast.makeText(this, "Server tidak memberikan respon", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(@NonNull Call<PelajaranResponse> call, @NonNull Throwable t) {
        refreshLayout.setRefreshing(false);
        t.printStackTrace();
        Toast.makeText(this, "Error gan..", Toast.LENGTH_SHORT).show();
    }

    private void setAdapterRecycler(PelajaranResponse response) {
        PelajaranAdapter adapter = new PelajaranAdapter(getApplicationContext(), response.getPelajaran());
        recyclerView.setAdapter(adapter);
    }

    private void getPelajaran() {
        if (CommonHelper.checkInternet(this)) {
            Gson gson = new Gson();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://abangcoding.com/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            APIRepository service = retrofit.create(APIRepository.class);
            Call<PelajaranResponse> call = service.getAllPelajaran(session.getUserDetails().get(SharedPrefLogin.KEY_ID_USER));
            call.enqueue(this);
        } else {
            Toast.makeText(this, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_buat_pelajaran:
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
                break;
            case R.id.txt_log_out:
                session.logout();
                break;
        }
    }
}
