package com.emranul.movieinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.emranul.movieinfo.fragment.FindFragment;
import com.emranul.movieinfo.fragment.HomeFragment;
import com.emranul.movieinfo.fragment.RankFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        frameLayout = findViewById(R.id.frame);
        setSupportActionBar(toolbar);

        loadFragment(new HomeFragment(),"Home");
        BottomNavigationView nav = findViewById(R.id.bottom_nav);
        nav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.item_home:

                    loadFragment(new HomeFragment(),"Home");
                    return true;
                case R.id.item_rank:
                    loadFragment(new RankFragment(),"Rank");
                    return true;
                case R.id.item_find:
                    loadFragment(new FindFragment(),"Find");
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment, String title) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame,fragment)
                .addToBackStack(null)
                .commit();
        toolbar.setTitle(title);
    }
}