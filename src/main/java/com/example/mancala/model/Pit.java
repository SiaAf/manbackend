package com.example.mancala.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Pit {
    private int id;
    private int numOfStone;
    private int playerId;

    public Pit() {
    }

    public int getOppositeIndex() {
        return Board.getInstance().getNumberOfPits() - id;
    }
}
