package com.example.firoz.newsviewsv2.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firoz.newsviewsv2.R;

public class BaseActivity extends AppCompatActivity {

    private long startTime, endTime;
    private static final String KEY_BOOLEAN = "boolean_value";
    private static final String PREF_KEY = "news_views";
    public static String number = "";

    // --- Code for show a snackbar
    public void showSnackBar(String message) {
        View view = findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);

        View view2 = snackbar.getView();
        TextView tv = view2.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.GREEN);

        snackbar.setAction("OK", null);

        snackbar.show();
    }


    // --- Code for network connectivity check
    public boolean isInternetConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        } else
            return false;
    }

    // --- Code for exit from the app when user double clicked on the back button
    public void exitWhenDoubleClick() {
        endTime = startTime;
        startTime = System.currentTimeMillis();
        if (startTime - endTime < 500) {
            finish();
        } else {
            showToast("Press back again to exit.");
        }
    }

    // --- Code for replace fragment
    public void addFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_place, fragment).commit();
    }

    // --- Code for showing a toast
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    // --- Code for store a boolean value into shared preference
    public void saveBoolean(Boolean value) {
        SharedPreferences preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_BOOLEAN, value).commit();
    }

    // --- Code for retrieve a boolean value into shared preference
    public boolean getBoolean() {
        SharedPreferences preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        if (preferences.contains(KEY_BOOLEAN)) {
            return preferences.getBoolean(KEY_BOOLEAN, false);
        }
        return false;
    }


    public void addWelcomeScreen(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.welcome_screen_place, fragment).commit();
    }

    public void goHomePage() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
