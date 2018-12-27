package com.example.firoz.newsviewsv2.activity.welcome_screen;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.firoz.newsviewsv2.R;
import com.example.firoz.newsviewsv2.activity.BaseActivity;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/*
 *** This is an one time welcome screen
 *** This activity will show one time after installation
 */


public class WelcomeActivity extends BaseActivity {

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
            goHomePage();
            finish();
        } else {
            initViews();
        }
    }

    private void initViews() {
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        addWelcomeScreen(new FirstPage());
    }

    @OnClick(R.id.nextTextView)
    public void onViewClicked() {
        pageCounter++;
        if (pageCounter == 2) {
            page2.setImageResource(R.drawable.circle2);
            addWelcomeScreen(new SecondPage());
        } else if (pageCounter == 3) {
            page3.setImageResource(R.drawable.circle2);
            addWelcomeScreen(new ThirdPage());
            nextTextView.setText("Done");
            nextTextView.setTextColor(Color.GREEN);
        } else if (pageCounter == 4) {
            saveBoolean(true);
            goHomePage();
            finish();
        }
    }


    private boolean isFirstTime() {
        return getBoolean();
    }
}
