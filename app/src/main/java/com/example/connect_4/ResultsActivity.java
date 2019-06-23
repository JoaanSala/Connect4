package com.example.connect_4;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.connect_4.BDSQLite.AccesDBActivity;
import com.example.connect_4.BDSQLite.LogSQLiteHelper;
import com.example.connect_4.Preferences.PreferencesActivity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ResultsActivity extends AppCompatActivity implements View.OnClickListener, Serializable {

    private LogSQLiteHelper LogHelper;
    private SQLiteDatabase db;
    String Alias, Size, Status, Log, leftTime, totaltime;
    boolean time;
    Log log;
    String date;
    boolean timeControl;

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
        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Alias = mySharedPreferences.getString(getString(R.string.P_Alias), "P1");
        Size = mySharedPreferences.getString(getString(R.string.P_Grill),"7");
        Status = in.getStringExtra("state");
        date = in.getStringExtra("DateHour");
        totaltime = in.getStringExtra("timer");
        leftTime = in.getStringExtra("time_left");

        Log = Logbuilder(Alias, Size, Status, leftTime);

        EditText dayhour = findViewById(R.id.DAYHOUR);
        EditText log = findViewById(R.id.LOG);
        EditText e_mail = findViewById(R.id.EMAIL_ADRESS);

        log.setText(Log);
        dayhour.setText(new Date().toString());
        e_mail.requestFocus();

        LogHelper = new LogSQLiteHelper(this, "DBConnect-4", null, 1);
        db = LogHelper.getWritableDatabase();
        create();
    }

    private void create() {

        if (db != null) {
            //ContentValues newRegister = new ContentValues();
            db.execSQL("INSERT INTO Partides (AliasUser, DateHour, SizeGridView, TotalTime, TimeLeft, StateMatch)" +
                    "VALUES ('"+Alias+"', '"+date+"', '"+Size+"', '"+totaltime+"', '"+leftTime+"', '"+Status+"')");

        }else{
            Toast.makeText(this, "No s'ha pogut guardar a la BD", Toast.LENGTH_LONG).show();
        }
        db.close();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        EditText Mail_edit = findViewById(R.id.EMAIL_ADRESS);
        EditText DayHour_edit = findViewById(R.id.DAYHOUR);
        EditText Log_edit = findViewById(R.id.LOG);

        switch (v.getId()) {
            case R.id.EMAIL:
                if(Mail_edit.getText().toString().isEmpty()){
                    Toast.makeText(this, "Falta Correu Destinatari", Toast.LENGTH_LONG).show();
                }else {
                    Intent in = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + Mail_edit.getText().toString()));
                    in.putExtra(Intent.EXTRA_SUBJECT, "Log - " + DayHour_edit.getText().toString());
                    in.putExtra(Intent.EXTRA_TEXT, "Alias: " + Alias + "\n" + "Mida Graella: " + Size + "   Temps total: "+ totaltime+"seg.\n" +"\n" +Status+"\n"+"Han sobrat: "+leftTime+" segons");
                    startActivity(in);
                }
                break;
            case R.id.NEWGAME:
                Intent in1 = new Intent(ResultsActivity.this, GameActivity.class);
                startActivity(in1);
                finish();
                break;
            case R.id.EXIT:
                finishAffinity();
                break;
        }
    }

    private String Logbuilder(String alias, String size, String status, String lefttime) {

        return "Alias: " + alias + " | Mida Grid View: "+ size + "\nHan sobrat: " + lefttime + " segons |  "+ status;
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

    @Override
    protected void onDestroy(){
        super.onDestroy();
        db.close();
    }
}
