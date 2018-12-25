package com.example.firoz.newsviewsv2.activity.welcome_screen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firoz.newsviewsv2.R;
import com.example.firoz.newsviewsv2.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/*
 *** This is an one time welcome screen
 *** This activity will show one time after installation
 */


public class WelcomeActivity extends AppCompatActivity {

    private static final String IS_FIRST_TIME = "pref_key";
    private static final String PREF_KEY = "news_views";
    @BindView(R.id.page1)
    ImageView page1;
    @BindView(R.id.page2)
    ImageView page2;
    @BindView(R.id.page3)
    ImageView page3;
    @BindView(R.id.nextTextView)
    TextView nextTextView;

    private int pageCounter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isFirstTime()) {
            goNextActivity();
        } else {
            initViews();
        }
    }

    private void initViews() {
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        addScreen(new FirstPage());
    }

    @OnClick(R.id.nextTextView)
    public void onViewClicked() {
        pageCounter++;
        if (pageCounter == 2) {
            page2.setImageResource(R.drawable.circle2);
            addScreen(new SecondPage());
        } else if (pageCounter == 3) {
            page3.setImageResource(R.drawable.circle2);
            addScreen(new ThirdPage());
            nextTextView.setText("Done");
            nextTextView.setTextColor(Color.GREEN);
        } else if (pageCounter == 4) {
            SharedPreferences preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(IS_FIRST_TIME, true).commit();
            goNextActivity();
        }
    }


    private void addScreen(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.welcome_screen_place, fragment).commit();
    }

    private void goNextActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private boolean isFirstTime() {
        SharedPreferences preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        if (preferences.contains(IS_FIRST_TIME)) {
            return preferences.getBoolean(IS_FIRST_TIME, false);
        }
        return false;
    }
}
