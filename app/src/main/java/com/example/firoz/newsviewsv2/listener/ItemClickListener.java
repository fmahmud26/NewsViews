package com.example.firoz.newsviewsv2.listener;

import android.view.MenuItem;
import android.view.View;

public interface ItemClickListener {
    void itemClick(int position);

    void popUpMenuClick(MenuItem item, int position);
}
