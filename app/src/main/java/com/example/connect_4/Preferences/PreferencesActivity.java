package com.example.connect_4.Preferences;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.connect_4.R;

public class PreferencesActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        getFragmentManager().beginTransaction().replace(android.R.id.content,new PreferencesFragment()).commit();

    }

}
