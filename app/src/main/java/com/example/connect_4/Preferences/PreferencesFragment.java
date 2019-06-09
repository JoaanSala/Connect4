package com.example.connect_4.Preferences;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.connect_4.R;

public class PreferencesFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
