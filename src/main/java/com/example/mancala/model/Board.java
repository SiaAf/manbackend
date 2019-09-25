package com.example.mancala.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Board {

    private static Board instance;

    private Pit[] pits;
    private int indexOfArrivingPit;
    public static int numberOfPits;

    public Board() {}

    public void createBoard(int numberOfPits, int numberOfStones) {

    }
    public Board(Pit[] pits, int numberOfPits) {
        this.pits = pits;
        this.numberOfPits = numberOfPits;
    }
}
