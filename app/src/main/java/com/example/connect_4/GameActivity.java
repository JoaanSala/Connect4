package com.example.connect_4;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.connect_4.Fragments.LogFragment;
import com.example.connect_4.Logica.Position;

import java.util.Date;


public class GameActivity extends FragmentActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

}