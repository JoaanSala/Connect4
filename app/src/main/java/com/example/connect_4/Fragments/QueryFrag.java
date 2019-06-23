package com.example.connect_4.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.connect_4.BDSQLite.LogSQLiteHelper;
import com.example.connect_4.BDSQLite.SQLAdapter;
import com.example.connect_4.Log;
import com.example.connect_4.MainActivity;
import com.example.connect_4.R;

import java.util.ArrayList;
import java.util.List;

public class QueryFrag extends Fragment {


    LogSQLiteHelper LogsHelper;
    SQLiteDatabase db;
    Button buttonDelete;
    ListView listView;
    SQLAdapter adapter;
    QueryListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (QueryListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " Must be implement QueryListener");
        }
    }

    private List<Log> logs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_query, container, false);
        listView = (ListView) view.findViewById(R.id.listViewQuery);
        buttonDelete = (Button) view.findViewById(R.id.buttonDelete);
        logs = new ArrayList<>();

        LogsHelper = new LogSQLiteHelper(getContext(), "DBConnect-4", null, 1);
        db = LogsHelper.getWritableDatabase();

        adapter = new SQLAdapter(getActivity(), logs, R.layout.db_items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Bundle b = new Bundle();
                Cursor cursorReg = db.rawQuery("SELECT * FROM Partides WHERE _id = ?", new String[]{Integer.toString(position + 1)});

                if (cursorReg.moveToFirst()) {
                    Log log = new Log(cursorReg.getString(cursorReg.getColumnIndex("AliasUser")),
                            cursorReg.getString(cursorReg.getColumnIndex("DateHour")),
                            cursorReg.getString(cursorReg.getColumnIndex("SizeGridView")),
                            cursorReg.getString(cursorReg.getColumnIndex("TotalTime")),
                            cursorReg.getString(cursorReg.getColumnIndex("TimeLeft")),
                            cursorReg.getString(cursorReg.getColumnIndex("StateMatch")));
                    b.putParcelable("LogId", log);

                    listener.queryFragment(b,position+1);

                } else
                    Toast.makeText(getActivity(), "Error en agafar les dades", Toast.LENGTH_LONG).show();
                cursorReg.close();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAll();
                update();
            }
        });

        update();

        return view;
    }

    private List<Log> getAllLogs() {
        Cursor cursor = db.rawQuery("SELECT * FROM Partides", null);
        List<Log> list = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String alias = cursor.getString(cursor.getColumnIndex("AliasUser"));
                String date = cursor.getString(cursor.getColumnIndex("DateHour"));
                String result = cursor.getString(cursor.getColumnIndex("StateMatch"));

                list.add(new Log(alias, result, date));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }

    private void removeAll() {
        String name_of_the_table = "Partides";
        db.execSQL("delete from Partides");
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + name_of_the_table + "'");
    }

    private void update() {
        logs.clear();
        logs.addAll(getAllLogs());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        db.close();
        super.onDestroy();
    }

    public interface QueryListener{
        public void queryFragment(Bundle b, int i);
    }

    public void setQueryListener(QueryListener queryListener) {
        listener = queryListener;
    }
}
