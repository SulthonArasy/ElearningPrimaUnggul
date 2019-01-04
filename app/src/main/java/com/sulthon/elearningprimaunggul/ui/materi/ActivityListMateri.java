package com.sulthon.elearningprimaunggul.ui.materi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sulthon.elearningprimaunggul.R;

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
