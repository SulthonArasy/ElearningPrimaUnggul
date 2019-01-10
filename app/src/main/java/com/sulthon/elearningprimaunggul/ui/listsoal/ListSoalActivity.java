package com.sulthon.elearningprimaunggul.ui.listsoal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sulthon.elearningprimaunggul.CommonHelper;
import com.sulthon.elearningprimaunggul.R;
import com.sulthon.elearningprimaunggul.data.api.soal.delete.DeleteSoalResponse;
import com.sulthon.elearningprimaunggul.data.api.soal.read.SoalItem;
import com.sulthon.elearningprimaunggul.data.api.soal.read.SoalResponse;
import com.sulthon.elearningprimaunggul.data.sharedpref.SharedPrefLogin;
import com.sulthon.elearningprimaunggul.service.APIRepository;
import com.sulthon.elearningprimaunggul.ui.inputsoal.InputSoalActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListSoalActivity extends AppCompatActivity implements View.OnClickListener, Callback<SoalResponse>, SwipeRefreshLayout.OnRefreshListener {

    private String idQuiz;
    private Gson gson = new Gson();
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://abangcoding.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    private APIRepository service = retrofit.create(APIRepository.class);
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recyclerSoal;
    private String nig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_soal);

        SharedPrefLogin session = new SharedPrefLogin(this);
        nig = session.getUserDetails().get(SharedPrefLogin.KEY_ID_USER);

        Button btnTambahSoal = findViewById(R.id.btn_tambah_soal);
        swipeRefresh = findViewById(R.id.swipe_refresh);
        recyclerSoal = findViewById(R.id.recycler_soal);
        recyclerSoal.setLayoutManager(new LinearLayoutManager(this));

        if (getIntent().getExtras() != null) {
            idQuiz = getIntent().getExtras().getString("idquiz");
            if (idQuiz != null) {
                getAllSoal(idQuiz);
            } else {
                Toast.makeText(this, "Tidak dapat mengambil id quiz", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Tidak dapat mengambil id quiz", Toast.LENGTH_SHORT).show();
            finish();
        }


        btnTambahSoal.setOnClickListener(this);
        swipeRefresh.setOnRefreshListener(this);
    }

    public void showDeleteSoal(final SoalItem soalItem) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Peringatan")
                .setMessage("Apakah anda yakin ingin menghapus soal ini ?")
                .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteSoal(soalItem);
                    }
                })
                .setNegativeButton("Tidak", null)
                .create();
        dialog.show();
    }

    public void deleteSoal(SoalItem soal) {
        if (CommonHelper.checkInternet(this)) {
            swipeRefresh.setRefreshing(true);
            Call<DeleteSoalResponse> call = service.deleteSoal(nig, soal.getId());
            call.enqueue(new Callback<DeleteSoalResponse>() {
                @Override
                public void onResponse(@NonNull Call<DeleteSoalResponse> call, @NonNull Response<DeleteSoalResponse> response) {
                    swipeRefresh.setRefreshing(false);
                    if (response.body() != null) {
                        if (response.body().getSuccess() == 1) {
                            Toast.makeText(ListSoalActivity.this, "Berhasil delete", Toast.LENGTH_SHORT).show();
                            getAllSoal(idQuiz);
                        } else {
                            Toast.makeText(ListSoalActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ListSoalActivity.this, "Server tidak memberikan respon", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<DeleteSoalResponse> call, @NonNull Throwable t) {
                    swipeRefresh.setRefreshing(false);
                    t.printStackTrace();
                    Toast.makeText(ListSoalActivity.this, "Update error..", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
        }
    }

    private void getAllSoal(String idQuiz) {
        if (CommonHelper.checkInternet(this)) {
            swipeRefresh.setRefreshing(true);

            Call<SoalResponse> call = service.getAllSoal(nig, idQuiz);
            call.enqueue(this);
        } else {
            Toast.makeText(this, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(this, InputSoalActivity.class);
        i.putExtra("idquiz", idQuiz);
        startActivity(i);
    }

    @Override
    public void onResponse(@NonNull Call<SoalResponse> call, @NonNull Response<SoalResponse> response) {
        swipeRefresh.setRefreshing(false);
        if (response.body() != null) {
            if (response.body().getSuccess() == 1) {
                if (response.body() != null) {
                    setAdapterMateri(response.body());
                }
            }
        } else {
            Toast.makeText(this, "Server tidak memberikan respon", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(@NonNull Call<SoalResponse> call, @NonNull Throwable t) {
        swipeRefresh.setRefreshing(false);
        t.printStackTrace();
        Toast.makeText(this, "Error gan..", Toast.LENGTH_SHORT).show();
    }

    private void setAdapterMateri(SoalResponse response) {
        SoalAdapter adapter = new SoalAdapter(response.getSoal(), this);
        recyclerSoal.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        getAllSoal(idQuiz);
    }
}
