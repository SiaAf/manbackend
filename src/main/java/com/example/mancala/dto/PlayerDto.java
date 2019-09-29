package com.example.mancala.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerDto {
    private int id;
    private String name;
    private boolean isWinner;
    private boolean isActive;
    private int score;
}
