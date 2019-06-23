package com.example.connect_4.Logica;

import android.os.Parcel;
import android.os.Parcelable;

public class Board implements Parcelable {

    public final int size;
    private Cell[][] cells;
    private int yellow;
    private int red;

    public Board(int size) {
        this.size = size;
        this.cells = new Cell[size][size];
        this.yellow = 0;
        this.red = 0;
        initBoard();
    }

    protected Board(Parcel in) {
        size = in.readInt();
        yellow = in.readInt();
        red = in.readInt();
    }

    public static final Creator<Board> CREATOR = new Creator<Board>() {
        @Override
        public Board createFromParcel(Parcel in) {
            return new Board(in);
        }

        @Override
        public Board[] newArray(int size) {
            return new Board[size];
        }
    };

    public int getSize() {return size;}

    public Cell[][] getCells() {
        return cells;
    }

    private void initBoard() {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                this.cells[i][j] = new Cell('#');
            }
        this.yellow = 0;
        this.red = 0;
    }

    public int getEmptyTokens () {
        return size * size - red - yellow;
    }


    public boolean contains(Position p) {
        return p.getCol() >= 0 && p.getCol() <= size - 1
                && p.getRow() >= 0 && p.getRow() <= size - 1;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty(Position p) {
        return contains(p) && this.cells[p.getRow()][p.getCol()].isEmpty();
    }

    public boolean canPlay(int col) {
        boolean canplay =false;
        int i = 0;
        while( i <= this.size-1) {
            if (this.cells[i][col].isEmpty()){
                canplay = true;
                break;
            }
            i++;
        }
        return col < this.size && col >= 0 && canplay;
    }

    int maxtokens(Position position) {
        if (!cells[position.getRow()][position.getCol()].isEmpty()) {
            int max = 0;
            for (int i = 0; i < Direction.ALL.length; i++) {
                Position one = update(position, Direction.ALL[i]);
                Position two = update(position, Direction.ALL[i].invert());
                max = Math.max(max, Position.pathLength(one, two));
            }
            return max;
        }
        return 0;
    }

    private Position update(Position position, Direction direction) {
        while (isValid(position.move(direction)) && player(position).isEqualTo(player(position.move(direction)))){
            position = position.move(direction);
        }
        return position;
    }

    Position occupyCell (int column, Player player) {
        if (canPlay(column)){
            int row = firstEmptyRow(column);
            this.cells[row][column] = new Cell(player);
            return new Position(row,column);
        }
        return null;
    }

    int firstEmptyRow(int column) {
        for (int i = 0 ; i <= this.size && canPlay(column); i++) {
            if (this.cells[i][column].isEmpty()){
                return i;
            }
        }
        return -1;
    }

    private boolean isValid(Position move) {
        return move.getRow() < size && move.getRow() >= 0 && move.getCol()< size && move.getCol()>=0;
    }

    private Cell player(Position position) {
        return this.cells[position.getRow()][position.getCol()];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(size);
        dest.writeInt(yellow);
        dest.writeInt(red);
    }
}



