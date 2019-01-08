package com.sulthon.elearningprimaunggul;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sulthon.elearningprimaunggul.data.api.materi.MateriItem;
import com.sulthon.elearningprimaunggul.ui.nilai.ActivityNilai;
import com.sulthon.elearningprimaunggul.ui.quiz.InputQuiz;
import com.sulthon.elearningprimaunggul.ui.soal.SoalActivity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ViewMateriActivity extends AppCompatActivity implements View.OnClickListener {

    private MateriItem materi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_materi);

        TextView txtNamaMateri = findViewById(R.id.txt_nama_materi);
        TextView txtDownload = findViewById(R.id.txt_download);
        Button btnKuis = findViewById(R.id.btn_quiz);
        TextView txtQuis = findViewById(R.id.txt_quis);
        Button btnLihatNilai = findViewById(R.id.lihat_nilai);


        if (getIntent().getExtras() != null) {
            materi = (MateriItem) getIntent().getExtras().getSerializable("data");
            if (materi == null) {
                Toast.makeText(this, "Tidak dapat mengambil data materi", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                txtNamaMateri.setText(materi.getNama());
            }
        }
        btnLihatNilai.setOnClickListener(this);
        btnKuis.setOnClickListener(this);
        txtQuis.setOnClickListener(this);
        txtDownload.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_quiz:
                startActivity(new Intent(ViewMateriActivity.this, InputQuiz.class));
                break;
            case R.id.txt_quis:
                startActivity((new Intent(ViewMateriActivity.this, SoalActivity.class)));
                break;
            case R.id.lihat_nilai:
                startActivity(new Intent(ViewMateriActivity.this, ActivityNilai.class));
                break;
            case R.id.txt_download:
                new DownloadFile().execute(materi.getUrlFile());
                break;
        }
    }

    /**
     * Async Task to download file from URL
     */
    @SuppressLint("StaticFieldLeak")
    private class DownloadFile extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;
        private String fileName;
        private String folder;
        private boolean isDownloaded;

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progressDialog = new ProgressDialog(ViewMateriActivity.this);
            this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                int lengthOfFile = connection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                String timestamp = new SimpleDateFormat("yyyyMMdd-HH:mm:ss", Locale.getDefault()).format(new Date());

                fileName = f_url[0].substring(f_url[0].lastIndexOf('/') + 1, f_url[0].length());
                fileName = timestamp + "_" + fileName;
                folder = Environment.getExternalStorageDirectory() + File.separator + "elearningpu/";

                File directory = new File(folder);

                if (!directory.exists()) {
                    boolean result = directory.mkdirs();
                    if (result) Log.d("pesan", "berhasil create folder elearningpu/");
                }

                OutputStream output = new FileOutputStream(folder + fileName);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / lengthOfFile));
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();
                Log.d("pesan", "folder -> " + folder + fileName);
                return "File " + fileName + " berhasil didownload";

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("pesan Error: ", e.getMessage());
            }

            return "Something went wrong";
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            progressDialog.setProgress(Integer.parseInt(progress[0]));
        }


        @Override
        protected void onPostExecute(String message) {
            this.progressDialog.dismiss();

            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }
}
