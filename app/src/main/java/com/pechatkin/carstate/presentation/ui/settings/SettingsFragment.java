package com.pechatkin.carstate.presentation.ui.settings;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.pechatkin.carstate.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
    }
}
