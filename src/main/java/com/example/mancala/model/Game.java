package com.example.mancala.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Game {
    private static Game instance = null;
    private Board board;
    private Player[] players;
    private boolean gameIsADraw;
    private boolean gameOver;

    private Game() {
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void resetGame() {
        this.board = null;
        this.players = null;
        this.gameIsADraw = false;
        this.gameOver = false;
    }

}
