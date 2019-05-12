package com.example.connect_4.Logica;

public class Position {

    private final int row;
    private final int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public Position move(Direction direction) {
        int newRow = this.row + direction.getChangeInRow();
        int newColumn = this.col + direction.getChangeInColumn();

        return new Position(newRow, newColumn);
    }

    boolean isEqualTo(Position other) {
        if(other == null){
            return false;
        }
        return this.row == other.row && this.col == other.col;
    }

    static int pathLength(Position pos1, Position pos2) {
        if (pos1.col==pos2.col) return Math.abs(pos1.row-pos2.row)+1;
        else return Math.abs(pos1.col-pos2.col)+1;
    }

    @Override
    public String toString() {
        return "Position{" +
                "row=" + row +
                ", column=" + col +
                '}';
    }
}
