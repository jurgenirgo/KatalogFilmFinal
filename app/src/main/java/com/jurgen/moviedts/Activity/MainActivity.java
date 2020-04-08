package com.jurgen.moviedts.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jurgen.moviedts.Fragments.FavoriteFragment;
import com.jurgen.moviedts.Fragments.MovieFragment;
import com.jurgen.moviedts.Fragments.TVShowFragment;
import com.jurgen.moviedts.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.container_layout)
    FrameLayout container_layout;
    @BindView(R.id.bottomNavigation)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.toolbarText)
    TextView toolbarText;

    Fragment fragment = new MovieFragment();

    int mKategoriPencarian = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() == null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            toolbarText.setText(getResources().getString(R.string.app_name));
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_movie:
                        fragment = new MovieFragment();

                        mKategoriPencarian = 0;
                        break;
                    case R.id.navigation_tv_show:
                        fragment = new TVShowFragment();

                        mKategoriPencarian = 1;
                        break;
                    case R.id.navigation_favorite:
                        fragment = new FavoriteFragment();

                        mKategoriPencarian = 2;
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container_layout, fragment).commit();
                return true;
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_layout, fragment).commit();
        } else {
            fragment = getSupportFragmentManager().getFragment(savedInstanceState, "Fragment");
            getSupportFragmentManager().beginTransaction().replace(R.id.container_layout, fragment).commit();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        getSupportFragmentManager().putFragment(outState, "Fragment", fragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_change_settings:
                startActivity(new Intent(MainActivity.this, Settings.class));
                break;
            case R.id.action_exit:
                finish();
                break;
            case R.id.action_search:
                SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
                searchView.setOnQueryTextListener(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        startActivity(new Intent(MainActivity.this, SearchActivity.class)
                .putExtra("KEYWORDPENCARIAN", query)
                .putExtra("KATEGORIPENCARIAN", mKategoriPencarian));
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
