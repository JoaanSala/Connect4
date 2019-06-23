package com.example.connect_4.Logica;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Game implements Parcelable {

    private final Board board;
    private int FourInARow;
    private boolean Winner;
    private Player turn;
    private long initTime;
    private long leftTime;
    private Game_State Gstate;


    public Game(int size, int FourInARow) {
        this.board = new Board(size);

        this.Winner = false;
        this.FourInARow = FourInARow;

        this.turn = Player.player1();
        this.Gstate = Game_State.PLAYER1_PLAYS;

        Date date = new Date();
    }

    public Game(Parcel in) {
        board = in.readParcelable(Board.class.getClassLoader());
        FourInARow = in.readInt();
        Winner = in.readByte() != 0;
        turn = in.readParcelable(Player.class.getClassLoader());
        initTime = in.readLong();
        leftTime = in.readLong();
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    public Game(Board board) {
        this.board = board;
    }

    public Integer CPUTurn () {

        int size = this.board.getSize();
        Integer[] pos = new Integer[size];

        if (board.getEmptyTokens()!=0){
            for (int j = 0; j < size; j++){
                boolean i = this.board.canPlay(j);
                if (i == false){pos[j] = null;}
                else {pos[j] = j;}
            }
            int random_pos= (int)(Math.random() * size);
            while (pos[random_pos] == null){
                random_pos = (int)(Math.random() * size);
            }
            return pos[random_pos];
        } else {
            return null;
        }
    }

    void SwitchTurn() {
        if (this.Gstate == Game_State.PLAYER1_PLAYS){
            this.Gstate = Game_State.CPU_PLAYS;
            this.turn = Player.CPU();
        } else {
            this.Gstate = Game_State.PLAYER1_PLAYS;
            this.turn = Player.player1();
        }
    }

    public void manageTime() {
        Date actualDate = new Date();
        long actualTime = actualDate.getTime();
        long timePassed = actualTime - this.initTime;
        this.leftTime = this.leftTime - timePassed;

        if (this.leftTime <= 0) {
            if (this.turn.isPlayer1()) {
                this.Gstate = Game_State.CPU_WINS;
            } else if (this.turn.isCPU()) {
                this.Gstate = Game_State.PLAYER1_WINS;
            }
            this.Winner = true;
        }
    }

    public boolean isFinish() {
        return Gstate != Game_State.PLAYER1_PLAYS && Gstate != Game_State.CPU_PLAYS;
    }

    public Position drop(int col){
        boolean playable_Row = this.board.canPlay(col);

        if (playable_Row) {
            Position occupedPos = this.board.occupyCell(col, this.turn);

            if (this.board.maxtokens(occupedPos) >= this.FourInARow) {
                if (this.turn.isPlayer1()) {
                    this.Gstate = Game_State.PLAYER1_WINS;
                } else if (this.turn.isCPU()) {
                    this.Gstate = Game_State.CPU_WINS;
                }
                this.Winner = true;
            } else if (this.board.getEmptyTokens()==0) {
                this.Gstate = Game_State.DRAW;
            } else {
                SwitchTurn();
            }
            return occupedPos;
        } else {
            return null;
        }
    }

    public long getGameTime() {
        return leftTime;
    }

    public long getRestTime() {
        return leftTime;
    }

    public long getStartTime() {return initTime;}

    public Board getBoard() {
        return board;
    }

    public int getConnectToWin() {
        return FourInARow;
    }

    public Game_State getState() {
        return Gstate;
    }

    public boolean itHasAWinner() {
        return Winner;
    }

    public Player getTurn() {
        return turn;
    }

    public void setStatus(Game_State Gstate) {
        this.Gstate = Gstate;
    }

    public void setHasWinner(boolean Winner) {
        this.Winner = Winner;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(board, flags);
        dest.writeInt(FourInARow);
        dest.writeByte((byte) (Winner ? 1 : 0));
        dest.writeParcelable(turn, flags);
        dest.writeLong(initTime);
        dest.writeLong(leftTime);
    }
}
