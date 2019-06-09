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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class GameFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static final String KEY_TIME = "time";
    public static final String KEY_LOG = "LOG";
    public static final String KEY_NAME = "name";
    public static final String KEY_SIZE = "size";


    private static final String PARCEL_PLAYER = "player";
    private static final String PARCEL_CPU = "cpu";
    private static final String PARCEL_GAME = "game";
    private static final String PARCEL_BOARD = "board";
    private static final String PARCEL_LOG = "log";
    private static final String TIMER = "timer";

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
    GridView gridView;
    TextView tvSeconds;
    TextView tvNumberSeconds;
    CountDownTimer timer;
    private View view;

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

        manageGameTable(savedInstanceState);

        return view;

    }

    private void manageGameTable(Bundle savedInstanceState){

        if(savedInstanceState != null && (savedInstanceState.getSerializable("game") != null)){
            game = (Game) savedInstanceState.getSerializable("game");
            image_table = (ButtonAdapter) savedInstanceState.getSerializable("table");
        } else {
            game = new Game(size, 4);
            image_table = new ButtonAdapter(getContext());
            image_table.setGrid(size);
        }

        GridView gridView = view.findViewById(R.id.grid_view);
        //timeControl();

        gridView.setAdapter(image_table);
        gridView.setNumColumns(size);
        gridView.setOnItemClickListener(this);
    }


    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        int col = position % size;
        Position pos = game.drop(col);
        int color = (Timer) ?
                getResources().getColor(R.color.red) :
                getResources().getColor(R.color.blue);

        image_table.setToken(R.drawable.red_token, pos.getRow(), pos.getCol());
        image_table.notifyDataSetChanged();

        if(Timer) {game.manageTime();}
        long temps = new Date().getTime();
        //text.setText(Math.abs(((temps - game.getStartTime()) / 1000) - game.getRestTime()) + " seconds");

        if(game.isFinish()) {
            Intent Results_Intent = new Intent(this.getActivity(), ResultsActivity.class);

            if(game.getState() == Game_State.PLAYER1_WINS) data.putString("state","HAS GUANYAT");
            if(game.getState() == Game_State.DRAW) data.putString("state","HAS EMPATAT");

            Results_Intent.putExtras(data);
            startActivity(Results_Intent);
        }else {
            col = game.CPUTurn();
            pos = game.drop(col);

            //CPU
            image_table.setToken(R.drawable.yellow_token, pos.getRow(), pos.getCol());
            image_table.notifyDataSetChanged();

            if(Timer) {game.manageTime();}

            if (game.isFinish()) {
                Intent Results_Intent = new Intent(this.getActivity(), ResultsActivity.class);

                if (game.getState() == Game_State.CPU_WINS) data.putString("state", "HAS PERDUT");
                if (game.getState() == Game_State.DRAW) data.putString("state", "HAS EMPATAT");

                Results_Intent.putExtras(data);
                startActivity(Results_Intent);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(PARCEL_BOARD, board);
        outState.putParcelable(PARCEL_PLAYER, player);
        outState.putParcelable(PARCEL_CPU, cpu);
        outState.putParcelable(PARCEL_GAME, game);
        outState.putBoolean(TIMER,firstTimer);
        if (time) {
            Timer = Boolean.parseBoolean(null);
        }

    }
}
