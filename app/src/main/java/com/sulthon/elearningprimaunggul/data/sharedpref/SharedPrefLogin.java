package com.sulthon.elearningprimaunggul.data.sharedpref;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.sulthon.elearningprimaunggul.ui.login.LoginActivity;

import java.util.HashMap;

public class SharedPrefLogin {
    public static final String KEY_ID_USER = "iduser";
    public static final String KEY_NAME = "name";
    public static final String KEY_TOKEN = "token";
    private static final String PREF_NAME = "PUPreference";
    private static final String IS_LOGIN = "isLoggedIn";

    private SharedPreferences pref;
    private Editor editor;
    private Context _context;

    public SharedPrefLogin(Context context) {
        this._context = context;
        int PRIVATE_MODE = 0;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void saveLogin(String idUser, String name, String token) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_ID_USER, idUser);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }


    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_ID_USER, pref.getString(KEY_ID_USER, null));
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));
        return user;
    }

    public void modifyData(String column, String value) {
        editor.putString(column, value);
        editor.commit();
    }

    public void logout() {
        editor.clear();
        editor.commit();
        Intent i = new Intent(_context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }

    /**
     * Mengecek apakah user sudah login
     *
     * @return default false jika belum login
     */
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}
