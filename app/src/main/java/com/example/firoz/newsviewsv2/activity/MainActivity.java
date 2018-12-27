package com.example.firoz.newsviewsv2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;

import com.example.firoz.newsviewsv2.R;
import com.example.firoz.newsviewsv2.activity.welcome_screen.WelcomeActivity;
import com.example.firoz.newsviewsv2.fragment.About;
import com.example.firoz.newsviewsv2.fragment.Home;
import com.example.firoz.newsviewsv2.fragment.NumberInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.fragment_place)
    FrameLayout fragmentPlace;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (!getBoolean()) {
            startActivity(new Intent(this, WelcomeActivity.class));
            finish();
        }
        super.onCreate(savedInstanceState);

        initViews();
        initDrawer();
    }


    private void initViews() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setTitle("NewsViews");
        addFragment(new Home());
    }

    // --- Initialize Navigation View Drawer
    private void initDrawer() {
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);

        // divider on navigation drawer
        NavigationMenuView navMenuView = (NavigationMenuView) navigationView.getChildAt(0);
        navMenuView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.search_id);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setInputType(InputType.TYPE_CLASS_DATETIME);
        searchView.setQueryHint("Search number");

        // --- Add listener on search view
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Go number info page
                number = query;
                addFragment(new NumberInfo());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // --- This method handle the navigation drawer item click
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                addFragment(new Home());
                break;
            case R.id.nav_about:
                addFragment(new About());
                break;
            case R.id.nav_exit:
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        exitWhenDoubleClick("Press back again to exit.");
    }
}
