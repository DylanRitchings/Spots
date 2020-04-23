package com.dylanritchings.Utils;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AuthPreferences {

    private static final String KEY_USER = "user";
    private static final String KEY_TOKEN = "token";

    private static SharedPreferences preferences;

    public AuthPreferences(Context context) {
        preferences = context
                .getSharedPreferences("auth", Context.MODE_PRIVATE);
    }

    public static void setUser(String user) {
        Editor editor = preferences.edit();
        editor.putString(KEY_USER, user);
        editor.apply();
    }

    public static void setToken(String password) {
        Editor editor = preferences.edit();
        editor.putString(KEY_TOKEN, password);
        editor.apply();
    }

    public String getUser() {
        return preferences.getString(KEY_USER, null);
    }

    public String getToken() {
        return preferences.getString(KEY_TOKEN, null);
    }
}