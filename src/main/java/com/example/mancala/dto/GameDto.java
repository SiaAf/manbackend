package com.example.mancala.dto;

import com.example.mancala.model.Game;
import com.example.mancala.model.Player;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GameDto {
    private Game game;
    private Player player;
    private Integer index;

}
