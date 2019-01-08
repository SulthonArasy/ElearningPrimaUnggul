package com.sulthon.elearningprimaunggul.ui.uploadmateri;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.sulthon.elearningprimaunggul.CommonHelper;
import com.sulthon.elearningprimaunggul.R;
import com.sulthon.elearningprimaunggul.data.api.materi.create.CreateMateriResponse;
import com.sulthon.elearningprimaunggul.data.sharedpref.SharedPrefLogin;
import com.sulthon.elearningprimaunggul.service.APIRepository;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadMateriActivity extends AppCompatActivity implements View.OnClickListener, Callback<CreateMateriResponse> {
    public final int REQUEST_FILE = 200;
    private TextView txtFile;
    private String nig, idPel;
    private EditText edtMateri;
    private Uri selectedFileUri;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_materi);

        SharedPrefLogin session = new SharedPrefLogin(this);

        if (getIntent().getExtras() != null) {
            idPel = getIntent().getExtras().getString("idpelajaran");
            nig = session.getUserDetails().get(SharedPrefLogin.KEY_ID_USER);
            if (idPel == null) {
                Toast.makeText(this, "Tidak dapat mengambil id pelajaran", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        Button btnChooseFile = findViewById(R.id.btn_choose_file);
        Button btnUpload = findViewById(R.id.btn_input_file);
        edtMateri = findViewById(R.id.edt_materi);
        txtFile = findViewById(R.id.txt_file);

        btnChooseFile.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
    }

    private void createMateri(String idPel, String nig, String pelajaran, File file, String fileType) {
        if (CommonHelper.checkInternet(this)) {
            loading = new ProgressDialog(UploadMateriActivity.this);
            loading.setCancelable(false);
            loading.setMessage("Mengupload data..");
            loading.show();

            Gson gson = new Gson();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://abangcoding.com/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            APIRepository service = retrofit.create(APIRepository.class);

            RequestBody requestFile = RequestBody.create(MediaType.parse(fileType), file);
            MultipartBody.Part bodyFile =
                    MultipartBody.Part.createFormData("file", file.getName(), requestFile);
            RequestBody bodyIdPel = RequestBody.create(MultipartBody.FORM, idPel);
            RequestBody bodyNig = RequestBody.create(MultipartBody.FORM, nig);
            RequestBody bodyPelajaran = RequestBody.create(MultipartBody.FORM, pelajaran);

            Call<CreateMateriResponse> call = service.createMateri(bodyNig, bodyIdPel, bodyPelajaran, bodyFile);
            call.enqueue(this);
        } else {
            Toast.makeText(this, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(@NonNull Call<CreateMateriResponse> call, @NonNull Response<CreateMateriResponse> response) {
        loading.dismiss();
        if (response.body() != null) {
            if (response.body().getSuccess() == 1) {
                setResult(RESULT_OK);
                finish();
            }
        } else {
            Toast.makeText(this, "Server tidak memberikan respon", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(@NonNull Call<CreateMateriResponse> call, @NonNull Throwable t) {
        loading.dismiss();
        t.printStackTrace();
        Toast.makeText(this, "Error gan..", Toast.LENGTH_SHORT).show();
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
                try {
                    File selectedFile = new File(txtFile.getText().toString());
                    String fileType = getContentResolver().getType(selectedFileUri);
                    if (edtMateri.getText().toString().trim().equals("")) {
                        Toast.makeText(this, "Nama materi harus diisi", Toast.LENGTH_SHORT).show();
                    } else {
                        createMateri(idPel, nig, edtMateri.getText().toString(), selectedFile, fileType);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Tidak dapat mengakses file..", Toast.LENGTH_SHORT).show();
                }
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
                selectedFileUri = data.getData();
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
