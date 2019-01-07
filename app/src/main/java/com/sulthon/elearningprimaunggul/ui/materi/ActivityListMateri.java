package com.sulthon.elearningprimaunggul.ui.materi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.sulthon.elearningprimaunggul.R;
import com.sulthon.elearningprimaunggul.ui.uploadmateri.UploadMateriActivity;

import java.util.ArrayList;


public class ActivityListMateri extends AppCompatActivity {

    private RecyclerView recyclerViewMateri;
    private MateriAdapter adapter;
    private ArrayList<Materi>materiArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_materi);

        /*TextView TxtQuis = findViewById(R.id.txt_quis);
        TxtQuis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity((new Intent(ActivityListMateri.this, InputQuiz.class)));
            }
        });*/
        Button btnBuatMateri = findViewById(R.id.btn_buat_materi);
        btnBuatMateri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityListMateri.this, UploadMateriActivity.class));
            }
        });

        addData();

        recyclerViewMateri = (RecyclerView) findViewById(R.id.recycler_materi);

        adapter = new MateriAdapter(materiArrayList, getApplicationContext());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ActivityListMateri.this);

        recyclerViewMateri.setLayoutManager(layoutManager);
        recyclerViewMateri.setAdapter(adapter);
    }

    void addData(){
        materiArrayList = new ArrayList<>();
        materiArrayList.add(new Materi("Web 1"));
        materiArrayList.add(new Materi("Web 2"));
        materiArrayList.add(new Materi("Web 3"));
    }
}
