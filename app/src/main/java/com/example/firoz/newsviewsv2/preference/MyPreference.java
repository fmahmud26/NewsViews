package com.example.firoz.newsviewsv2.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.firoz.newsviewsv2.app.AppConstant;

public class MyPreference {

    private static SharedPreferences preferences;

    public static void saveBoolean(Context context, String key, boolean value) {
        preferences = context.getSharedPreferences(AppConstant.KEY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key) {
        preferences = context.getSharedPreferences(AppConstant.KEY_PREF, Context.MODE_PRIVATE);
        if (preferences.contains(key))
            return preferences.getBoolean(key, false);
        else
            return false;
    }
}
