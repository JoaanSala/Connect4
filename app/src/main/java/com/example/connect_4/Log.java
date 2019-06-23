package com.example.connect_4;

import android.os.Parcel;
import android.os.Parcelable;

public class Log implements Parcelable{
    private String player;
    private String result;
    private String date_time;
    private String grid;
    private String time_left;
    private String total_time;


    public Log(){}

    public Log(String player, String date_time, String  result){
        this.player = player;
        this.date_time = date_time;
        this.result = result;
    }

    public Log(String aliasUser, String dateHour, String sizeGridView, String totalTime, String timeLeft, String stateMatch) {
        this.player = aliasUser;
        this.date_time = dateHour;
        this.grid = sizeGridView;
        this.total_time = totalTime;
        this.time_left = timeLeft;
        this.result = stateMatch;
    }

    protected Log(Parcel in) {
        player = in.readString();
        result = in.readString();
        date_time = in.readString();
        grid = in.readString();
        time_left = in.readString();
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

    public String getTime_remaining() {
        return time_left;
    }

    public void setTime_remaining(String time_left) {
        this.time_left = time_left;
    }

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

    public String getGrid() {
        return grid;
    }

    public void setGrid(String grid) {
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
        dest.writeString(grid);
        dest.writeString(time_left);
        dest.writeString(total_time);
    }
}
