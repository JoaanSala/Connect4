package com.example.connect_4;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class ResultsActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Button email = findViewById(R.id.EMAIL);
        Button newGame = findViewById(R.id.NEWGAME);
        Button exit = findViewById(R.id.EXIT);
        email.setOnClickListener(this);
        newGame.setOnClickListener(this);
        exit.setOnClickListener(this);

        Intent in = getIntent();
        EditText dayhour = findViewById(R.id.DAYHOUR);
        EditText log = findViewById(R.id.LOG);
        EditText e_mail = findViewById(R.id.EMAIL_ADRESS);

        log.setText(in.getStringExtra("Alias identificatiu")+" Mida Graella: ");
        log.append(String.valueOf(in.getIntExtra("Mida Graella",-1))+" "+in.getStringExtra("state"));
        dayhour.setText(new Date().toString());
        e_mail.requestFocus();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        EditText Mail_edit = findViewById(R.id.EMAIL_ADRESS);
        EditText DayHour_edit = findViewById(R.id.DAYHOUR);
        EditText Log_edit = findViewById(R.id.LOG);

        switch (v.getId()) {
            case R.id.EMAIL:
                Toast.makeText(this, "Correu Destinatari", Toast.LENGTH_LONG).show();
                Intent in = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + Mail_edit.getText().toString()));
                in.putExtra(Intent.EXTRA_SUBJECT, "Log - " + DayHour_edit.getText().toString());
                in.putExtra(Intent.EXTRA_TEXT, Log_edit.getText().toString());
                startActivity(in);
                break;
            case R.id.NEWGAME:
                Intent in1 = new Intent(ResultsActivity.this, SettingsActivity.class);
                startActivity(in1);
                finish();
                break;
            case R.id.EXIT:
                finishAffinity();
                break;
        }
    }
}
