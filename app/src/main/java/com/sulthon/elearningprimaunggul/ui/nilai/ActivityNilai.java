package com.sulthon.elearningprimaunggul.ui.nilai;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sulthon.elearningprimaunggul.CommonHelper;
import com.sulthon.elearningprimaunggul.R;
import com.sulthon.elearningprimaunggul.data.api.nilai.read.NilaiResponse;
import com.sulthon.elearningprimaunggul.data.sharedpref.SharedPrefLogin;
import com.sulthon.elearningprimaunggul.service.APIRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityNilai extends AppCompatActivity implements Callback<NilaiResponse>, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private String idQuiz;
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
        setContentView(R.layout.activity_nilai);

        SharedPrefLogin session = new SharedPrefLogin(this);
        nig = session.getUserDetails().get(SharedPrefLogin.KEY_ID_USER);

        swipeRefresh = findViewById(R.id.swipe_refresh);
        recyclerView = findViewById(R.id.recycle_nilai);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ActivityNilai.this);
        recyclerView.setLayoutManager(layoutManager);

        if (getIntent().getExtras() != null) {
            idQuiz = getIntent().getExtras().getString("idquiz");
            if (idQuiz != null) {
                getAllNilai();
            } else {
                Toast.makeText(this, "Tidak dapat mengambil id quiz", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Tidak dapat mengambil id quiz", Toast.LENGTH_SHORT).show();
            finish();
        }

        swipeRefresh.setOnRefreshListener(this);
    }

    private void getAllNilai() {
        if (CommonHelper.checkInternet(this)) {
            swipeRefresh.setRefreshing(true);

            Call<NilaiResponse> call = service.getAllNilai(nig, idQuiz);
            call.enqueue(this);
        } else {
            Toast.makeText(this, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(@NonNull Call<NilaiResponse> call, @NonNull Response<NilaiResponse> response) {
        swipeRefresh.setRefreshing(false);
        if (response.body() != null) {
            if (response.body().getSuccess() == 1) {
                if (response.body() != null) {
                    setAdapterNilai(response.body());
                }
            }
        } else {
            Toast.makeText(this, "Server tidak memberikan respon", Toast.LENGTH_SHORT).show();
        }
    }

    private void setAdapterNilai(NilaiResponse response) {
        ListAdapterNilai adapter = new ListAdapterNilai(response.getNilai());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onFailure(@NonNull Call<NilaiResponse> call, @NonNull Throwable t) {
        swipeRefresh.setRefreshing(false);
        t.printStackTrace();
        Toast.makeText(this, "Error gan..", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        getAllNilai();
    }
}
    
