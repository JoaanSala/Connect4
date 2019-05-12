package com.example.connect_4.Logica;

import android.os.Parcel;
import android.os.Parcelable;

public class Player implements Parcelable {

    private static final char PLAYER1 = '1';
    private static final char CPU = '0';

    private final char id;

    private Player(char id) {
        this.id = id;
    }

    protected Player(Parcel in) {
        id = (char) in.readInt();
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    public static Player player1() {
        Player Player1 = new Player(PLAYER1);
        return Player1;
    }

    public static Player CPU() {
        Player cpu = new Player(CPU);
        return cpu;
    }

    public boolean isPlayer1() {
        return this.id == PLAYER1;
    }

    public boolean isCPU() {
        return  this.id == CPU;
    }

    public char getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Player "
                + id
                ;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt((int) id);
    }
}
