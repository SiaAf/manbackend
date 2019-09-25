package com.example.mancala.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Game {

    private Board board;
    private Player[] players;
    private boolean gameIsADraw;
    private boolean gameOver;

    private Game() {}


}
