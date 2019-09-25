package com.example.mancala.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PitDto {
    private int id;
    private PlayerDto playerDto;
    private int noOfStones;
}
