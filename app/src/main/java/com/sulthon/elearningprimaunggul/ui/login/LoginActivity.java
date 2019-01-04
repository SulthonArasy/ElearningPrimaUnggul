package com.sulthon.elearningprimaunggul.ui.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sulthon.elearningprimaunggul.ui.about.AboutActivity;
import com.sulthon.elearningprimaunggul.R;
import com.sulthon.elearningprimaunggul.ui.pelajaran.PelajaranActivity;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private EditText NisSiswa, passwordText;
    private Button loginButton;
    private TextView txtGuru;
    private TextView txtSiswa;
    private String contohNis = "12345";
    private String contohPassword = "zxcvb";
    private Boolean guruSiswa = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        NisSiswa = (EditText) findViewById(R.id.edt_nis);
        passwordText = (EditText) findViewById(R.id.edt_password);
        Button btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (guruSiswa) {
                    startActivity(new Intent(LoginActivity.this, PelajaranActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Anda Bukan Siswa", Toast.LENGTH_SHORT).show();
                }
//                login();
                /*ambilData();*/
            }
        });

        final TextView txtNis = findViewById(R.id.txt_nis);
        final EditText edtNis = findViewById(R.id.edt_nis);

        txtGuru = findViewById(R.id.txt_guru);
        txtGuru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guruSiswa = false;
                txtGuru.setBackgroundColor(Color.GREEN);
                txtGuru.setTextColor(Color.WHITE);
                txtNis.setText(R.string.NIGy);
                edtNis.setHint("NIG");
                txtSiswa.setBackgroundColor(Color.GRAY);
                txtSiswa.setTextColor(Color.BLACK);

            }
        });

        txtGuru.setBackgroundColor(Color.GREEN);

        txtSiswa = findViewById(R.id.txt_siswa);
        txtSiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guruSiswa = true;
                txtSiswa.setBackgroundColor(Color.GREEN);
                txtSiswa.setTextColor(Color.WHITE);
                txtNis.setText(R.string.nisy);
                edtNis.setHint(R.string.nisy);
                txtGuru.setBackgroundColor(Color.GRAY);
                txtGuru.setTextColor(Color.BLACK);
            }
        });

        txtNis.setText(R.string.NIGy);
        edtNis.setHint(R.string.NIGy);

        Button btnAbout = findViewById(R.id.btn_about);
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, AboutActivity.class));
            }
        });
    }

    /*public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.MyMaterialTheme_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String Nis = NisSiswa.getText().toString();
        String password = passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String Nis = NisSiswa.getText().toString();
        String password = passwordText.getText().toString();

        if (Nis.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(Nis).matches()) {
            NisSiswa.setError("enter a valid email address");
            valid = false;
        } else {
            NisSiswa.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }

}
    /*public static SharedPreferences getSharedPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
    public class Preferen {
        static final String KEY_USER_TEREGISTER =
                "user",KEY_PASS_TEREGISTER ="pass";
        static final String KEY_USERNAME_SEDANG_LOGIN =
                "Username_logged_in";
        static final String KEY_STATUS_SEDANG_LOGIN =
                "Status_logged_in";
    }

    private static SharedPreferences getSharedPreference(Context context,String username) {
        public static void setRegisteredUser(Context context, String
        username){
            SharedPreferences.Editor editor =
                    getSharedPreference(context).edit();
            editor.putString(KEY_USER_TEREGISTER, username);
            editor.apply();
        }
    }*/


    /*private void ambilData() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Tunggu Sebentar....");
        dialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://180.243.162.99/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIRepository service = retrofit.create(APIRepository.class);
        LoginBody body = new LoginBody("1","1");
        Call<LoginResponse> result = service.login(body);

        result.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                dialog.dismiss();
                Toast.makeText(LoginActivity.this, response.message(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                dialog.dismiss();
                Toast.makeText(LoginActivity.this, "Error gan..", Toast.LENGTH_SHORT).show();

            }
        });*/

