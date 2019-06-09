package com.example.connect_4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.connect_4.BDSQLite.AccesDBActivity;
import com.example.connect_4.Preferences.PreferencesActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Show_HelpActivity(View clickedButton) {
        Intent in = new Intent(this, HelpActivity.class);
        startActivity(in);
    }

    public void Go_Game(View clickedButton) {
        Intent in = new Intent(this, GameActivity.class);
        startActivity(in);
    }

    public void Go_DB(View clickedButton) {
        Intent in = new Intent(this, AccesDBActivity.class);
        startActivity(in);
    }

    public void Exit(View clickedButton) {
        finish();
        System.exit(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.config:
                startActivity(new Intent(this, PreferencesActivity.class));
                return true;
            case R.id.database:
                startActivity(new Intent(this, AccesDBActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
