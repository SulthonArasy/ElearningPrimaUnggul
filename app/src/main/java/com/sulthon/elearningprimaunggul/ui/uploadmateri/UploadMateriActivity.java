package com.sulthon.elearningprimaunggul.ui.uploadmateri;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.sulthon.elearningprimaunggul.CommonHelper;
import com.sulthon.elearningprimaunggul.R;

import java.util.ArrayList;

public class UploadMateriActivity extends AppCompatActivity implements View.OnClickListener {
    public final int REQUEST_FILE = 200;
    private TextView txtFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_materi);

        Button btnChooseFile = findViewById(R.id.btn_choose_file);
        Button btnUpload = findViewById(R.id.btn_input_file);
        EditText edtPelajaran = findViewById(R.id.edt_pelajaran);
        EditText edtMateri = findViewById(R.id.edt_materi);
        EditText edtDeskripsi = findViewById(R.id.edt_deskripsi);
        txtFile = findViewById(R.id.txt_file);

        btnChooseFile.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_choose_file:
                new TedPermission(UploadMateriActivity.this)
                        .setPermissionListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted() {
                                selectFile();
                            }

                            @Override
                            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                                Toast.makeText(UploadMateriActivity.this, "Kami memerlukan izin tersebut..", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .setPermissions(Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                        .check();
                break;
            case R.id.btn_input_file:
                break;
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose File to Upload.."), REQUEST_FILE);
    }

    private void selectFile() {
        final CharSequence[] items = {"Pilih file", "Batal"};

        AlertDialog.Builder builder = new AlertDialog.Builder(UploadMateriActivity.this);
        builder.setTitle("Pilih file..");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Pilih file")) {
                    showFileChooser();
                } else {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_FILE) {
                if (data == null) {
                    return;
                }
                Uri selectedFileUri = data.getData();
                String selectedFilePath = CommonHelper.getPath(this, selectedFileUri);
                if (selectedFilePath != null && !selectedFilePath.equals("")) {
                    txtFile.setText(selectedFilePath);
                } else {
                    Toast.makeText(this, "Cannot upload file to server", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
