package com.example.mancala.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerDto {
    private String name;
    private boolean isWinner;
    private boolean isActive;
    private int score;
}
