package com.example.connect_4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.Date;

import com.example.connect_4.Logica.Board;
import com.example.connect_4.Logica.Game;
import com.example.connect_4.Logica.Player;
import com.example.connect_4.Logica.Position;
import com.example.connect_4.Logica.Game_State;


public class GameActivity extends Activity implements AdapterView.OnItemClickListener {

    private static final String PARCEL_PLAYER = "player";
    private static final String PARCEL_CPU = "cpu";
    private static final String PARCEL_GAME = "game";
    private static final String PARCEL_BOARD = "board";
    private static final String TIMER = "timer";

    private ButtonAdapter image_table;
    private Game game;
    private Board board;
    private Player player;
    private Player cpu;
    private int size;
    private boolean timer;
    boolean firstTimer=true;
    private boolean time;
    private Bundle data = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        TextView text = findViewById(R.id.clock);

        Intent intent = getIntent();
        size = intent.getIntExtra("Mida Graella",-1);
        timer = intent.getBooleanExtra("Checkbox", false);
        data.putInt("Mida Graella",size);
        data.putString("Alias identificatiu",intent.getStringExtra("Alias identificatiu"));

        game = new Game(size,4);
        GridView gridView = findViewById(R.id.grid_view);
        image_table = new ButtonAdapter(this);
        image_table.setGrid(size);
        gridView.setAdapter(image_table);
        gridView.setNumColumns(size);
        gridView.setOnItemClickListener(this);

        if (savedInstanceState != null) {
            board = savedInstanceState.getParcelable(PARCEL_BOARD);
            player = savedInstanceState.getParcelable(PARCEL_PLAYER);
            cpu = savedInstanceState.getParcelable(PARCEL_CPU);
            game = savedInstanceState.getParcelable(PARCEL_GAME);
            firstTimer=savedInstanceState.getBoolean(TIMER);

        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        int col = position % size;
        Position pos = game.drop(col);
        TextView text = findViewById(R.id.clock);
        int color = (timer) ?
                getResources().getColor(R.color.red) :
                getResources().getColor(R.color.blue);
        text.setTextColor(color);

        image_table.setToken(R.drawable.red_token, pos.getRow(), pos.getCol());
        image_table.notifyDataSetChanged();

        if(timer) {game.manageTime();}
        long temps = new Date().getTime();
        text.setText(Math.abs(((temps - game.getStartTime()) / 1000) - game.getRestTime()) + " seconds");

        if(game.isFinish()) {
            Intent Results_Intent = new Intent(GameActivity.this, ResultsActivity.class);

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

            if(timer) {game.manageTime();}

            if (game.isFinish()) {
                Intent Results_Intent = new Intent(GameActivity.this, ResultsActivity.class);

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
            timer = Boolean.parseBoolean(null);
        }

    }
}