package com.example.connect_4;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.Serializable;


public class ButtonAdapter extends BaseAdapter implements Parcelable {

    private Context mContext;
    private Integer[] Tokens;
    private int num;

    public ButtonAdapter(Context c) {
        mContext = c;
    }

    protected ButtonAdapter(Parcel in) {
        num = in.readInt();
    }

    public static final Creator<ButtonAdapter> CREATOR = new Creator<ButtonAdapter>() {
        @Override
        public ButtonAdapter createFromParcel(Parcel in) {
            return new ButtonAdapter(in);
        }

        @Override
        public ButtonAdapter[] newArray(int size) {
            return new ButtonAdapter[size];
        }
    };

    @Override
    public int getCount() {
        return Tokens.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(135, 135));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(3, 3, 3, 3);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(Tokens[position]);
        return imageView;
    }

    public void setGrid(int i) {
        this.num = i;
        Tokens = new Integer[i * i];
        for (int x = 0; x < i * i; x++) {
            Tokens[x] = R.drawable.empty_token;
        }
    }

    public void setToken(int token, int row, int col) {
        int position = num * (-(row - (num - 1))) + col;
        Tokens[position] = token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(num);
    }
}
