package com.sulthon.elearningprimaunggul.ui.materi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sulthon.elearningprimaunggul.CommonHelper;
import com.sulthon.elearningprimaunggul.R;
import com.sulthon.elearningprimaunggul.data.api.materi.MateriResponse;
import com.sulthon.elearningprimaunggul.data.sharedpref.SharedPrefLogin;
import com.sulthon.elearningprimaunggul.service.APIRepository;
import com.sulthon.elearningprimaunggul.ui.uploadmateri.UploadMateriActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ActivityListMateri extends AppCompatActivity implements View.OnClickListener, Callback<MateriResponse>, SwipeRefreshLayout.OnRefreshListener {
    private static final int REQUEST_CREATE_MATERI = 200;
    private RecyclerView recyclerViewMateri;
    private SwipeRefreshLayout swipeRefresh;
    private String idPel;
    private String nig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_materi);

        SharedPrefLogin session = new SharedPrefLogin(this);

        Button btnBuatMateri = findViewById(R.id.btn_buat_materi);
        swipeRefresh = findViewById(R.id.swipe_refresh);
        recyclerViewMateri = findViewById(R.id.recycler_materi);
        recyclerViewMateri.setLayoutManager(new LinearLayoutManager(ActivityListMateri.this));

        if (getIntent().getExtras() != null) {
            idPel = getIntent().getExtras().getString("idpelajaran");
            nig = session.getUserDetails().get(SharedPrefLogin.KEY_ID_USER);
            if (idPel != null) {
                swipeRefresh.setRefreshing(true);
                getMateri(idPel, nig);
            } else {
                Toast.makeText(this, "Tidak dapat mengambil id pelajaran", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        swipeRefresh.setOnRefreshListener(this);
        btnBuatMateri.setOnClickListener(this);
    }

    private void getMateri(String idPel, String nig) {
        if (CommonHelper.checkInternet(this)) {
            Gson gson = new Gson();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://abangcoding.com/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            APIRepository service = retrofit.create(APIRepository.class);
            Call<MateriResponse> call = service.getAllMateri(nig, idPel);
            call.enqueue(this);
        } else {
            Toast.makeText(this, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(@NonNull Call<MateriResponse> call, @NonNull Response<MateriResponse> response) {
        swipeRefresh.setRefreshing(false);
        if (response.body() != null) {
            if (response.body().getSuccess() == 1) {
                setAdapterMateri(response.body());
            }
        } else {
            Toast.makeText(this, "Server tidak memberikan respon", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(@NonNull Call<MateriResponse> call, @NonNull Throwable t) {
        swipeRefresh.setRefreshing(false);
        t.printStackTrace();
        Toast.makeText(this, "Error gan..", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        getMateri(idPel, nig);
    }

    private void setAdapterMateri(MateriResponse response) {
        MateriAdapter adapter = new MateriAdapter(response.getMateri(), this);
        recyclerViewMateri.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(ActivityListMateri.this, UploadMateriActivity.class);
        i.putExtra("idpelajaran", idPel);
        startActivityForResult(i, REQUEST_CREATE_MATERI);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CREATE_MATERI) {
                onRefresh();
            }
        }
    }
}
