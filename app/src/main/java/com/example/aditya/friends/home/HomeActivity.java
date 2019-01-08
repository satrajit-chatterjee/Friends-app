package com.example.aditya.friends.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import com.example.aditya.friends.startup.StartupActivity;

import com.example.aditya.friends.R;
import com.example.aditya.friends.appointment.AppointmentActivity;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout mHomeDrawerLayout;
    private NavigationView mHomeNavigationView;

    private ViewPager mHomeViewPager;
    private TabLayout mHomeTabLayout;
    private HomeViewPagerAdapter mAdapter;
    private Toolbar mToolbar;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mToolbar = (Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeAsUpIndicator(R.drawable.ic_dehaze_white_24dp);


        mHomeDrawerLayout = (DrawerLayout) findViewById(R.id.home_drawerLayout);
        mHomeNavigationView = (NavigationView) findViewById(R.id.home_navigationView);

        mHomeViewPager = (ViewPager) findViewById(R.id.home_viewPager);
        mHomeTabLayout = (TabLayout) findViewById(R.id.home_tabLayout);

        mAdapter = new HomeViewPagerAdapter(getSupportFragmentManager());
        mHomeViewPager.setAdapter(mAdapter);

        mHomeNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                // close drawer when item is tapped
                mHomeDrawerLayout.closeDrawers();
                menuItem.setChecked(false);

                switch (menuItem.getItemId()){
                    case R.id.menu_home_navigation_drawer_home : {
                        mHomeDrawerLayout.closeDrawers();
                        break;
                    }
                    case R.id.menu_home_navigation_drawer_appointment : {
                        Intent intent = new Intent(HomeActivity.this, AppointmentActivity.class);
                        startActivity(intent);
                        break;
                    }
                }

                // Add code here to update the UI based on the item selected
                // For example, swap UI fragments here
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                mHomeDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
            case R.id.menu_home_activity_logout : {

                SharedPreferences mPreferences;
                SharedPreferences.Editor mEditor;
                mPreferences = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
                mEditor = mPreferences.edit();
                mEditor.putString("password", "");
                mEditor.apply();
                Intent intent = new Intent(HomeActivity.this, StartupActivity.class);
                startActivity(intent);
                finish();

            }

        }
        return super.onOptionsItemSelected(item);
    }
}
