package com.example.connect_4.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.connect_4.ButtonAdapter;
import com.example.connect_4.GameActivity;
import com.example.connect_4.Log;
import com.example.connect_4.Logica.Board;
import com.example.connect_4.Logica.Game;
import com.example.connect_4.Logica.Game_State;
import com.example.connect_4.Logica.Player;
import com.example.connect_4.Logica.Position;
import com.example.connect_4.R;
import com.example.connect_4.ResultsActivity;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class GameFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static String TIMER_TIME;

    private ButtonAdapter image_table;
    String namePlayer;
    int sizeTable;
    private Game game;
    private Board board;
    private Player player;
    private Player cpu;
    private int size;
    private boolean Timer;
    boolean firstTimer=true;
    private boolean time;
    private String alias;
    private Bundle data = new Bundle();
    private Log log;

    /*Elements of view*/
    String time_left = "-";
    GridView gridView;
    TextView tvSeconds;
    TextView tvNumberSeconds;
    CountDownTimer timer;
    int totalTime = 0;
    private View view;
    private Object ViewGroup;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_game, container, false);

        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());

        time = mySharedPreferences.getBoolean(getString(R.string.P_Time), false);
        size = Integer.parseInt(mySharedPreferences.getString(getString(R.string.P_Grill),"7"));

        if(size == 5){TIMER_TIME = "30";}
        else if(size == 6){TIMER_TIME = "45";}
        else if(size == 7){TIMER_TIME = "60";}

        data.putString("timer", TIMER_TIME);
        manageGameTable(savedInstanceState);

        return view;

    }


    private void manageGameTable(Bundle savedInstanceState){
        itemsLayout();

        if(savedInstanceState != null && (savedInstanceState.getParcelable("game") != null)){
            game = (Game) savedInstanceState.getParcelable("game");
            image_table = (ButtonAdapter) savedInstanceState.getParcelable("table");
        } else {
            game = new Game(size, 4);
            image_table = new ButtonAdapter(getContext());
            image_table.setGrid(size);
        }

        GridView gridView = view.findViewById(R.id.grid_view);

        gridView.setAdapter(image_table);
        gridView.setNumColumns(size);
        gridView.setOnItemClickListener(this);

        int color = (time) ?
                getResources().getColor(R.color.red) :
                getResources().getColor(R.color.blue);

        tvSeconds.setTextColor(color);
        tvNumberSeconds.setTextColor(color);
    }

    private void itemsLayout() {
        tvSeconds = view.findViewById(R.id.seconds);
        tvNumberSeconds = view.findViewById(R.id.numberSeconds);
    }


    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        int col = position % size;
        Position pos = game.drop(col);

        image_table.setToken(R.drawable.red_token, pos.getRow(), pos.getCol());
        image_table.notifyDataSetChanged();

        if(game.isFinish()) {

            if(game.getState() == Game_State.PLAYER1_WINS){
                data.putString("state","HAS GUANYAT");
                showToast(1);
            }
            if(game.getState() == Game_State.DRAW){
                data.putString("state","HAS EMPATAT");
                showToast(2);
            }

        }else {
            col = game.CPUTurn();
            pos = game.drop(col);

            //CPU
            image_table.setToken(R.drawable.yellow_token, pos.getRow(), pos.getCol());
            image_table.notifyDataSetChanged();

            if (game.isFinish()) {

                if (game.getState() == Game_State.CPU_WINS) {
                    data.putString("state", "HAS PERDUT");
                    showToast(3);
                }
                if (game.getState() == Game_State.DRAW){
                    data.putString("state", "HAS EMPATAT");
                    showToast(2);
                }
            }
        }
    }

    private void showToast(int i) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup)view.findViewById(R.id.toast_root));

        TextView toast_text = layout.findViewById(R.id.text_toast);
        ImageView toast_image = layout.findViewById(R.id.image_toast);

        Toast toast = new Toast(getActivity().getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);

        if (time) {
            timer.cancel();
            time_left = tvNumberSeconds.getText().toString();
            data.putString("time_left", time_left);
        }else{
            data.putString("time_left", time_left);
        }

        switch (i){
            case 1:
                toast_text.setText(R.string.toast_win);
                toast_image.setImageResource(R.drawable.win);
                break;
            case 2:
                toast_text.setText(R.string.toast_draw);
                toast_image.setImageResource(R.drawable.draw);
                break;
            case 3:
                toast_text.setText(R.string.toast_lose);
                toast_image.setImageResource(R.drawable.lose);
                break;
            case 4:
                toast_text.setText(R.string.toast_out_time);
                toast_image.setImageResource(R.drawable.out_time);
                break;
            default:
                break;
        }
        String dateHour = obtainDateHour();
        data.putString("DateHour", dateHour);
        toast.show();
        Intent Results_Intent = new Intent(this.getActivity(), ResultsActivity.class);
        Results_Intent.putExtras(data);
        startActivity(Results_Intent);
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putParcelable("game", game);
        state.putParcelable("table", image_table);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (time) {
            if (timer == null) {
                totalTime = Integer.parseInt(TIMER_TIME) * 1000;
                timer = new CountDownTimer(totalTime, 1000) {
                    public void onTick(long millisUntilFinished) {
                        tvNumberSeconds.setText(String.valueOf(millisUntilFinished / 1000));
                    }

                    public void onFinish() {
                        data.putString("state", "FI DEL TEMPS");
                        showToast(4);
                    }
                }.start();
            }
        }else{
            tvNumberSeconds.setText(TIMER_TIME);
        }
    }

    private static String obtainDateHour() {
        Date d = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", new Locale("es", "ES"));
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK);
        String data = dateFormat.format(d);
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss", new Locale("es", "ES"));
        String hora = hourFormat.format(d);

        return data + " " + hora;
    }

    @Override
    public void onStop() {
        super.onStop();
        if(time && timer!=null) {
            timer.cancel();
            timer = null;
        }
    }


}
