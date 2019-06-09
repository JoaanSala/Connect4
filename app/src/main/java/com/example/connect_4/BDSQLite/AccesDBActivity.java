package com.example.connect_4.BDSQLite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.connect_4.Fragments.QueryFrag;
import com.example.connect_4.Fragments.RegFrag;
import com.example.connect_4.R;

public class AccesDBActivity extends FragmentActivity implements QueryFrag.QueryListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acces_db);

        QueryFrag queryFrag = (QueryFrag) getSupportFragmentManager().findFragmentById(R.id.queryFragment);
        queryFrag.setQueryListener(this);
    }

    @Override
    public void queryFragment(Bundle b, int position) {
        RegFrag regFrag = (RegFrag) getSupportFragmentManager().findFragmentById(R.id.regFragment);
        if (regFrag != null && regFrag.isInLayout()) {
            regFrag.updateRegFragment(regFrag.getView(), b, position);
        } else {
            Intent in = new Intent(this, DetailRegActivity.class);
            b.putInt(RegFrag.KEY_ID, position);
            in.putExtras(b);
            startActivity(in);
            finish();
        }
    }
}