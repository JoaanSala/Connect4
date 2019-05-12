package com.example.connect_4;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


public class ButtonAdapter extends BaseAdapter {

    private Context mContext;
    private Integer[] Tokens;
    private int num;

    public ButtonAdapter(Context c) {
        mContext = c;
    }


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
            imageView.setPadding(5, 5, 5, 5);
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
}
