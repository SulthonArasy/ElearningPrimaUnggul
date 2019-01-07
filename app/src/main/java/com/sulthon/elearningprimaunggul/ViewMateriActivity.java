package com.sulthon.elearningprimaunggul;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sulthon.elearningprimaunggul.ui.quiz.ActivityQuiz;
import com.sulthon.elearningprimaunggul.ui.quiz.InputQuiz;
import com.sulthon.elearningprimaunggul.ui.soal.SoalActivity;

public class ViewMateriActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_materi);

        Button btnKuis = findViewById(R.id.btn_quiz);
        btnKuis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewMateriActivity.this, InputQuiz.class));
            }
        });

        TextView TxtQuis = findViewById(R.id.txt_quis);
        TxtQuis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity((new Intent(ViewMateriActivity.this, SoalActivity.class)));
            }
        });

    }
}
