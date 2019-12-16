package com.pechatkin.carstate.presentation.ui.settings;

import android.os.Bundle;

import androidx.navigation.Navigation;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.pechatkin.carstate.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);


        SwitchPreference tutorialPref = findPreference(getString(R.string.pref_tutorial));
        if (tutorialPref != null) {
            tutorialPref.setOnPreferenceClickListener(preference -> {
                if(tutorialPref.isChecked() && getActivity() != null) {
                    Navigation.findNavController(getActivity(), R.id.nav_host_fragment)
                            .navigate(R.id.action_navigation_settings_to_firstOpenTutorialFragment);
                    tutorialPref.setChecked(false);
                }

                return true;
            });
        }
    }
}
