package com.example.connect_4.Logica;

public class Direction {

    public static final Direction DOWN = new Direction(1, 0);
    public static final Direction RIGHT = new Direction(0, 1);
    public static final Direction DIAGONAL = new Direction(1, 1);
    public static final Direction REVERSE_DIAGONAL = new Direction(1, -1);

    public static final Direction[] ALL = new Direction[] {RIGHT, DOWN, DIAGONAL, REVERSE_DIAGONAL};

    private final int changeRow;
    private final int changeColumn;

    private Direction(int changeRow, int changeColumn) {
        this.changeRow = changeRow;
        this.changeColumn = changeColumn;
    }

    public int getChangeInRow() {
        return changeRow;
    }

    public int getChangeInColumn() {
        return changeColumn;
    }

    public Direction invert() {
        int changeRowInverted = this.changeRow;
        int changeColumnInverted = this.changeColumn;
        changeRowInverted *= -1;
        changeColumnInverted *= -1;
        Direction inverted = new Direction(changeRowInverted, changeColumnInverted);
        return inverted;
    }
}
