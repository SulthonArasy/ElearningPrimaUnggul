package com.sulthon.elearningprimaunggul.ui.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sulthon.elearningprimaunggul.R;
import com.sulthon.elearningprimaunggul.ui.nilai.ActivityNilai;

public class InputQuiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_quiz);

        Button btnSimpanQuis= findViewById(R.id.input_file);
        btnSimpanQuis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InputQuiz.this, ActivityNilai.class));
            }
        });
    }
}
