package com.example.firoz.newsviewsv2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.firoz.newsviewsv2.R;
import com.example.firoz.newsviewsv2.model.Article;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends BaseActivity {

    @BindView(R.id.titleTextView)
    TextView titleTextView;
    @BindView(R.id.authorTextView)
    TextView authorTextView;
    @BindView(R.id.timeTextView)
    TextView timeTextView;
    @BindView(R.id.newsPhoto)
    ImageView newsPhoto;
    @BindView(R.id.detailsTextView)
    TextView detailsTextView;

    private Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
        initObjects();
        setData();
    }


    private void initViews() {
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
    }

    private void initObjects() {
        article = (Article) getIntent().getSerializableExtra("article");
    }

    private void setData() {
        titleTextView.setText(article.getTitle());
        if (article.getAuthor()!=null)
        authorTextView.setText(article.getAuthor().toString());
        timeTextView.setText(article.getPublishedAt());
        detailsTextView.setText(article.getDescription());

        Glide.with(this).load(article.getUrlToImage())
                .apply(new RequestOptions().placeholder(R.drawable.newspaper).error(R.drawable.newspaper))
                .into(newsPhoto);
    }

    public void viewMore(View view) {
        // It show an alert dialog, if user open it into external browser or not
        showAlert(article.getUrl());
    }


}
