package com.example.connect_4;

import android.os.Parcel;
import android.os.Parcelable;

public class Log implements Parcelable {
    private String player;
    private String result;
    private String date_time;
    private int grid;
    private String time_remaining;

    public Log(){}

    public Log(String player, String result, String  date_time){
        this.player = player;
        this.result = result;
        this.date_time = date_time;
    }

    public Log(String player, String result, String date_time, int grid, String time_remaining){
        this.player = player;
        this.result = result;
        this.date_time = date_time;
        this.grid = grid;
        this.time_remaining = time_remaining;
    }

    public String getTime_remaining() {
        return time_remaining;
    }

    public void setTime_remaining(String time_remaining) {
        this.time_remaining = time_remaining;
    }

    protected Log(Parcel in) {
        player = in.readString();
        result = in.readString();
        date_time = in.readString();
        grid = in.readInt();
        time_remaining = in.readString();
    }

    public static final Creator<Log> CREATOR = new Creator<Log>() {
        @Override
        public Log createFromParcel(Parcel in) {
            return new Log(in);
        }

        @Override
        public Log[] newArray(int size) {
            return new Log[size];
        }
    };

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getGrid() {
        return grid;
    }

    public void setGrid(int grid) {
        this.grid = grid;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(player);
        dest.writeString(result);
        dest.writeString(date_time);
        dest.writeInt(grid);
        dest.writeString(time_remaining);
    }
}
