package com.sulthon.elearningprimaunggul.ui.listsoal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sulthon.elearningprimaunggul.R;
import com.sulthon.elearningprimaunggul.ui.inputsoal.InputSoalActivity;

public class ListSoalActivity extends AppCompatActivity implements View.OnClickListener {

    private String idQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pertanyaan);

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

        Button btnTambahSoal = findViewById(R.id.btn_tambah_soal);
        btnTambahSoal.setOnClickListener(this);
    }

    private void getAllSoal(String idQuiz) {

    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(this, InputSoalActivity.class);
        i.putExtra("idquiz", idQuiz);
        startActivity(i);
    }
}
