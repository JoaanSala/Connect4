package com.example.connect_4.BDSQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LogSQLiteHelper extends SQLiteOpenHelper {

    //Sentencia SQL para crear la taula Matches
    String sqlCreate = "CREATE TABLE Partides" +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
            "AliasUser TEXT NOT NULL, " +
            "DateHour TEXT NOT NULL, " +
            "SizeGridView TEXT NOT NULL, " +
            "TotalTime TEXT NOT NULL," +
            "TimeLeft TEXT, " +
            "StateMatch TEXT NOT NULL)";


    public LogSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int prevVersion, int newVersion) {

        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Partides");

        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreate);
    }
}
