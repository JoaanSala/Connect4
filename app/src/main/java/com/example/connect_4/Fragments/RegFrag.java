package com.example.connect_4.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.connect_4.Log;
import com.example.connect_4.R;

public class RegFrag extends Fragment {

    public static final String KEY_ID = "id_reg";
    private static final String PARCEL_FRAG_ID = "log_frag_id";
    private static final String PARCEL_FRAG_ALIAS = "log_frag_alias";
    private static final String PARCEL_FRAG_DATE_TIME = "log_frag_date_time";
    private static final String PARCEL_FRAG_BOARD = "log_frag_board";
    private static final String PARCEL_FRAG_TC = "log_frag_tc";
    private static final String PARCEL_FRAG_TOTAL_TIME = "log_frag_total_time";
    private static final String PARCEL_FRAG_RESULT = "log_frag_result";


    TextView textView0;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView7;
    TextView textView8;


    public RegFrag() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        createObjects(getView());

        if (savedInstanceState != null) {
            textView0.setText(savedInstanceState.getString(PARCEL_FRAG_ID));
            textView1.setText(savedInstanceState.getString(PARCEL_FRAG_ALIAS));
            textView2.setText(savedInstanceState.getString(PARCEL_FRAG_DATE_TIME));
            textView3.setText(savedInstanceState.getString(PARCEL_FRAG_BOARD));
            textView4.setText(savedInstanceState.getString(PARCEL_FRAG_TC));
            textView7.setText(savedInstanceState.getString(PARCEL_FRAG_TOTAL_TIME));
            textView8.setText(savedInstanceState.getString(PARCEL_FRAG_RESULT));
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reg, container, false);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null)
            updateRegFragment(view, bundle, bundle.getInt(KEY_ID));

        return view;
    }

    public void updateRegFragment(View view, Bundle bundle, int pos) {
        Log log = bundle.getParcelable("LogId");

        createObjects(view);

        textView0.setText(Integer.toString(pos));
        textView1.setText(log.getPlayer().toString());
        textView2.setText(log.getDate_time().toString());
        textView3.setText(Integer.toString(log.getGrid()));
        if (log.getTime_remaining() == null) {
            textView4.setText(getString(R.string.negative));
            textView7.setText("-");
        } else {
            textView4.setText(getString(R.string.affirmative));
            textView7.setText(log.getTime_remaining());
        }
        textView8.setText(log.getResult());
    }

    private void createObjects(View view) {
        textView0 = (TextView) view.findViewById(R.id.regID);
        textView1 = (TextView) view.findViewById(R.id.aliasP1);
        textView2 = (TextView) view.findViewById(R.id.dateTimeReg);
        textView3 = (TextView) view.findViewById(R.id.boardSize);
        textView4 = (TextView) view.findViewById(R.id.TimeControlValue);
        textView7 = (TextView) view.findViewById(R.id.TotalTimeValue);
        textView8 = (TextView) view.findViewById(R.id.resultValue);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(PARCEL_FRAG_ID, textView0.getText().toString());
        outState.putString(PARCEL_FRAG_ALIAS, textView1.getText().toString());
        outState.putString(PARCEL_FRAG_DATE_TIME, textView2.getText().toString());
        outState.putString(PARCEL_FRAG_BOARD, textView3.getText().toString());
        outState.putString(PARCEL_FRAG_TC, textView4.getText().toString());
        outState.putString(PARCEL_FRAG_TOTAL_TIME, textView7.getText().toString());
        outState.putString(PARCEL_FRAG_RESULT, textView8.getText().toString());
    }

}
