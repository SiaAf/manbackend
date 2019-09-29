package com.example.mancala.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GameDto {
    private BoardDto board;
    private PlayerDto[] players;
    private boolean gameIsADraw;
    private boolean gameOver;
    private int startIndex;
    private int playerIdWhoPlayed;

}
