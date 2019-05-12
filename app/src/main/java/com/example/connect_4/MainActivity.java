package com.example.connect_4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void Show_HelpActivity (View clickedButton) {
        Intent in = new Intent(this, HelpActivity.class);
        startActivity(in);
    }
    public void Go_Settings(View clickedButton){
        Intent in = new Intent(this, SettingsActivity.class);
        startActivity(in);
    }
}
