package com.example.mancala.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDto {
    private PitDto[] pitDtos;
    private int indexOfArrivingPit;
}
