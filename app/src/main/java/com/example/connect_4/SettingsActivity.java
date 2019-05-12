package com.example.connect_4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingsActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button go = findViewById(R.id.START);
        go.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.START:
                EditText alias = findViewById(R.id.ALIAS);
                RadioGroup gridview = findViewById(R.id.GRIDVIEW);
                CheckBox time = findViewById(R.id.TIME);

                int selectedId = gridview.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(selectedId);

                Bundle data = new Bundle();
                data.putString(getString(R.string.Alias), alias.getText().toString());
                data.putInt(getString(R.string.Mida), Integer.parseInt(radioButton.getText().toString()));
                data.putBoolean("Temps", time.isChecked());

                Intent in = new Intent(this, GameActivity.class);
                in.putExtras(data);
                startActivity(in);
                break;
        }
    }
}
