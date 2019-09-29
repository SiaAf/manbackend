package com.example.mancala.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Board {
    private static Board instance = null;
    private Pit[] pits;
    private int indexOfArrivingPit;
    public int numberOfPits;

    private Board() {
    }

    public static Board getInstance() {
        if (instance == null) {
            instance = new Board();
        }
        return instance;
    }

    public void resetBoard() {
        this.pits = null;
        this.indexOfArrivingPit = -1;
        numberOfPits = -1;
    }
}
