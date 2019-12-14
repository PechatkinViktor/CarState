package com.pechatkin.carstate;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
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

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        NavController navController =
                Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

    }
}
