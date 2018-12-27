package com.example.firoz.newsviewsv2.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
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
        snackbar.setActionTextColor(Color.MAGENTA);

        View view2 = snackbar.getView();
        TextView tv = view2.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.GREEN);

        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // --- Just close
            }
        });

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
    public void exitWhenDoubleClick(String message) {
        endTime = startTime;
        startTime = System.currentTimeMillis();
        if (startTime - endTime < 500) {
            finish();
        } else {
            showToast(message);
        }
    }

    // --- Code for replace fragment
    // --- This method add fragment on the home screen
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

    // These for three page welcome screen
    public void addWelcomeScreen(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.welcome_screen_place, fragment).commit();
    }

    // Go home activity
    public void goHomePage() {
        startActivity(new Intent(this, MainActivity.class));
    }


    // --- Show alert dialog, if user open the news link into an external browser or not
    public void showAlert(final String url) {
        new AlertDialog.Builder(this).
                setMessage("Do you open it external browser?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // If yes, then open the link into external web browser
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // If No, then open into webview
                        startActivity(new Intent(BaseActivity.this, MoreDetailsActivity.class).putExtra("url", url));
                    }
                })
                .setNeutralButton("Cancel", null).show();

    }


    // --- When home button is called, this method gets called
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
