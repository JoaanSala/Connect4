package com.example.connect_4.Logica;


public class Cell{

    private char state;
    private static final char PLAYER1 = '1';
    private static final char CPU = '0';
    private static final char EMPTY = '#';


    public Cell (char state){
        if (state == EMPTY) { this.state = EMPTY;}
        else {this.state = 'G';}
    }

    public Cell(Player player) {
        this.state = player.getId();
    }

    public boolean isEqualTo(Cell other) {
        if(other == null){
            return false;
        }
        return this.state == other.state;
    }

    public boolean isPlayer1() {
        return this.state == PLAYER1;
    }

    public boolean isCPU() {
        return  this.state == CPU;
    }

    public boolean isEmpty() {
        return this.state == EMPTY;
    }
}
