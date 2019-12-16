package com.pechatkin.carstate;

import android.content.SharedPreferences;
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

        setUpNavigation();
        if (savedInstanceState == null) {
            setUpPrefs();
            initTutorial();
        }
    }

    private void initTutorial() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getBoolean(getString(R.string.pref_tutorial), true)) {
            showTutorial();
        }
    }

    private void setUpPrefs() {
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    private void showTutorial() {
        Navigation.findNavController(this, R.id.nav_host_fragment)
                .navigate(R.id.action_navigation_purchases_to_firstOpenTutorialFragment);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs
                .edit()
                .putBoolean(getString(R.string.pref_tutorial), false)
                .apply();
    }

    private void setUpNavigation() {

        BottomNavigationView mBottomNavigationView = findViewById(R.id.bottom_navigation_view);
        AppBarConfiguration mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_purchases, R.id.navigation_history, R.id.navigation_settings,
                R.id.history_summary_fragment, R.id.planned_summary_fragment,
                R.id.firstOpenTutorialFragment)
                .build();
        NavController mNavController =
                Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this,
                mNavController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(mBottomNavigationView, mNavController);

    }
}
