package com.sulthon.elearningprimaunggul.ui.nilai;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sulthon.elearningprimaunggul.R;

import java.util.ArrayList;

public class ActivityNilai extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListAdapterNilai adapter;
    private ArrayList<Nilai> nilaiArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_row_nilai);

        addData();

        recyclerView = (RecyclerView) findViewById(R.id.recycle_nilai);

        adapter = new ListAdapterNilai(nilaiArrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ActivityNilai.this);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
    }

    void addData(){
        nilaiArrayList = new ArrayList<>();
        nilaiArrayList.add(new Nilai("15150", "Maulana", "95"));
        nilaiArrayList.add(new Nilai("15151", "Yonk", "75"));
        nilaiArrayList.add(new Nilai("15152", "Nugraha", "80"));
        nilaiArrayList.add(new Nilai("15153", "Siswana", "90"));
    }

}
    
