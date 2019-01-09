package com.sulthon.elearningprimaunggul.ui.pelajaran;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.sulthon.elearningprimaunggul.CommonHelper;
import com.sulthon.elearningprimaunggul.R;
import com.sulthon.elearningprimaunggul.data.api.pelajaran.create.CreatePelajaranResponse;
import com.sulthon.elearningprimaunggul.data.api.pelajaran.delete.DeletePelajaranResponse;
import com.sulthon.elearningprimaunggul.data.api.pelajaran.read.PelajaranItem;
import com.sulthon.elearningprimaunggul.data.api.pelajaran.read.PelajaranResponse;
import com.sulthon.elearningprimaunggul.data.api.pelajaran.update.UpdatePelajaranResponse;
import com.sulthon.elearningprimaunggul.data.sharedpref.SharedPrefLogin;
import com.sulthon.elearningprimaunggul.service.APIRepository;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListPelajaranActivity extends AppCompatActivity implements View.OnClickListener,
        Callback<PelajaranResponse>, SwipeRefreshLayout.OnRefreshListener {
    private SharedPrefLogin session;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
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
        setContentView(R.layout.activity_list_pelajaran);

        session = new SharedPrefLogin(this);
        nig = session.getUserDetails().get(SharedPrefLogin.KEY_ID_USER);
        String namaUser = session.getUserDetails().get(SharedPrefLogin.KEY_NAME);

        Button btnBuatPelajaran = findViewById(R.id.btn_buat_pelajaran);
        TextView txtLogOut = findViewById(R.id.txt_log_out);
        TextView txtNama = findViewById(R.id.txt_nama_user);
        ImageView imgUser = findViewById(R.id.img_user);
        refreshLayout = findViewById(R.id.swipe_refresh);
        recyclerView = findViewById(R.id.recycler_pelajaran);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        txtNama.setText(namaUser);
        if (!session.isGuru()) {
            imgUser.setImageResource(R.drawable.pu);
            btnBuatPelajaran.setVisibility(View.GONE);
        }

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
        Toast.makeText(this, "Read error..", Toast.LENGTH_SHORT).show();
    }

    private void setAdapterRecycler(PelajaranResponse response) {
        PelajaranAdapter adapter = new PelajaranAdapter(this, response.getPelajaran(), session.isGuru());
        recyclerView.setAdapter(adapter);
    }

    private void getPelajaran() {
        if (CommonHelper.checkInternet(this)) {
            refreshLayout.setRefreshing(true);
            Call<PelajaranResponse> call = service.getAllPelajaran(nig);
            call.enqueue(this);
        } else {
            Toast.makeText(this, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
        }
    }

    private void createPelajaran(String namaPelajaran, String nig) {
        if (CommonHelper.checkInternet(this)) {
            refreshLayout.setRefreshing(true);
            Call<CreatePelajaranResponse> call = service.createPelajaran(nig, namaPelajaran);
            call.enqueue(new Callback<CreatePelajaranResponse>() {
                @Override
                public void onResponse(@NonNull Call<CreatePelajaranResponse> call, @NonNull Response<CreatePelajaranResponse> response) {
                    refreshLayout.setRefreshing(false);
                    if (response.body() != null) {
                        if (response.body().getSuccess() == 1) {
                            getPelajaran();
                        }
                    } else {
                        Toast.makeText(ListPelajaranActivity.this, "Server tidak memberikan respon", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<CreatePelajaranResponse> call, @NonNull Throwable t) {
                    refreshLayout.setRefreshing(false);
                    t.printStackTrace();
                    Toast.makeText(ListPelajaranActivity.this, "Create error..", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
        }
    }

    public void updatePelajaran(PelajaranItem pelajaran) {
        if (CommonHelper.checkInternet(this)) {
            refreshLayout.setRefreshing(true);
            Call<UpdatePelajaranResponse> call = service.updatePelajaran(nig, pelajaran.getNama(), pelajaran.getId());
            call.enqueue(new Callback<UpdatePelajaranResponse>() {
                @Override
                public void onResponse(@NonNull Call<UpdatePelajaranResponse> call, @NonNull Response<UpdatePelajaranResponse> response) {
                    refreshLayout.setRefreshing(false);
                    if (response.body() != null) {
                        if (response.body().getSuccess() == 1) {
                            getPelajaran();
                        }
                    } else {
                        Toast.makeText(ListPelajaranActivity.this, "Server tidak memberikan respon", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<UpdatePelajaranResponse> call, @NonNull Throwable t) {
                    refreshLayout.setRefreshing(false);
                    t.printStackTrace();
                    Toast.makeText(ListPelajaranActivity.this, "Update error..", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
        }
    }

    public void deletePelajaran(PelajaranItem pelajaran) {
        if (CommonHelper.checkInternet(this)) {
            refreshLayout.setRefreshing(true);
            Call<DeletePelajaranResponse> call = service.deletePelajaran(nig, pelajaran.getId());
            call.enqueue(new Callback<DeletePelajaranResponse>() {
                @Override
                public void onResponse(@NonNull Call<DeletePelajaranResponse> call, @NonNull Response<DeletePelajaranResponse> response) {
                    refreshLayout.setRefreshing(false);
                    if (response.body() != null) {
                        if (response.body().getSuccess() == 1) {
                            getPelajaran();
                        }
                    } else {
                        Toast.makeText(ListPelajaranActivity.this, "Server tidak memberikan respon", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<DeletePelajaranResponse> call, @NonNull Throwable t) {
                    refreshLayout.setRefreshing(false);
                    t.printStackTrace();
                    Toast.makeText(ListPelajaranActivity.this, "Update error..", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
        }
    }

    private void showAddPelajaran(final Context c) {
        final EditText taskEditText = new EditText(c);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Tambah Pelajaran")
                .setMessage("Beri nama untuk pelajaran baru?")
                .setView(taskEditText)
                .setPositiveButton("Tambah", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String pelajaran = taskEditText.getText().toString().trim();
                        if (pelajaran.isEmpty()) {
                            Toast.makeText(ListPelajaranActivity.this, "Nama pelajaran harus diisi", Toast.LENGTH_SHORT).show();
                            showAddPelajaran(c);
                        } else {
                            createPelajaran(pelajaran, nig);
                        }
                    }
                })
                .setNegativeButton("Batal", null)
                .create();
        dialog.show();
    }

    public void showUpdatePelajaran(final PelajaranItem pelajaranItem) {
        final EditText taskEditText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Ubah Pelajaran")
                .setMessage("Beri nama baru?")
                .setView(taskEditText)
                .setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String pelajaran = taskEditText.getText().toString().trim();
                        if (pelajaran.isEmpty()) {
                            Toast.makeText(ListPelajaranActivity.this, "Nama pelajaran harus diisi", Toast.LENGTH_SHORT).show();
                            showUpdatePelajaran(pelajaranItem);
                        } else {
                            pelajaranItem.setNama(pelajaran);
                            updatePelajaran(pelajaranItem);
                        }
                    }
                })
                .setNegativeButton("Batal", null)
                .create();
        dialog.show();
    }

    public void showDeletePelajaran(final PelajaranItem pelajaranItem) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Peringatan")
                .setMessage("Apakah anda yakin ingin menghapus pelajaran " + pelajaranItem.getNama() + "?")
                .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deletePelajaran(pelajaranItem);
                    }
                })
                .setNegativeButton("Tidak", null)
                .create();
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_buat_pelajaran:
                new TedPermission(ListPelajaranActivity.this)
                        .setPermissionListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted() {
                                showAddPelajaran(ListPelajaranActivity.this);
                            }

                            @Override
                            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                                Toast.makeText(ListPelajaranActivity.this, "Kami memerlukan izin tersebut..", Toast.LENGTH_SHORT).show();
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
                finishAffinity();
                break;
        }
    }
}
