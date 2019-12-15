package com.pechatkin.carstate;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpPrefs();
        setUpNavigation();
    }

    private void setUpPrefs() {
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    private void setUpNavigation() {

        BottomNavigationView mBottomNavigationView = findViewById(R.id.bottom_navigation_view);
        AppBarConfiguration mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_purchases, R.id.navigation_history, R.id.navigation_settings)
                .build();
        NavController mNavController =
                Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this,
                mNavController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(mBottomNavigationView, mNavController);

    }
}
