package com.sulthon.elearningprimaunggul.ui.pelajaran;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.sulthon.elearningprimaunggul.R;
import com.sulthon.elearningprimaunggul.ui.uploadmateri.UploadMateriActivity;

import java.util.ArrayList;

public class PelajaranActivity extends AppCompatActivity {
    private ArrayList<Pelajaran> pelajaranArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pelajaran);

        addData();

        RecyclerView recyclerView = findViewById(R.id.recycler_pelajaran);
        PelajaranAdapter adapter = new PelajaranAdapter(getApplicationContext(), pelajaranArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PelajaranActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        /*LinearLayout btnMateri = findViewById(R.id.btn_meteri);
        btnMateri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PelajaranActivity.this, ActivityListMateri.class));

            }
        });*/

        Button btnBuatMateri = findViewById(R.id.btn_buat_materi);
        btnBuatMateri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
    }

    private void addData() {
        pelajaranArrayList = new ArrayList<>();
        pelajaranArrayList.add(new Pelajaran("Web"));
        pelajaranArrayList.add(new Pelajaran("Matematika"));
        pelajaranArrayList.add(new Pelajaran("Logika Dasar"));
    }


}
