package com.example.mancala.service;

import com.example.mancala.dto.PitDto;
import com.example.mancala.model.Game;
import com.example.mancala.model.Pit;
import com.example.mancala.model.Player;
import org.springframework.stereotype.Service;

@Service
public class PitService {



    /**
     * @param index          index of each pit.
     * @param numberOfPits   total number of pits on the board
     * @param numberOfStones number of stones in each pit excluding big pits.
     * @param player         the players should be attached to the pit
     * @return a pit
     */
    public Pit createPit(int index, int numberOfPits, int numberOfStones, Player player) {
        if (index == 0 || index == numberOfPits / 2) {
            return new Pit(index, 0, player.getId());
        } else {
            return new Pit(index, numberOfStones, player.getId());
        }
    }

    /**
     * the method creates pitsDto.
     *
     * @return PitsDto[]
     */
    public PitDto[] pitsToPitsDto() {
        PitDto[] pitDtos = new PitDto[Game.getInstance().getBoard().getNumberOfPits()];
        Pit[] pits = Game.getInstance().getBoard().getPits();
        int counter = 0;
        for (Pit pit : pits) {
            pitDtos[counter] = pitToPPitDto(pit);
            counter++;
        }
        return pitDtos;
    }

    /**
     * the method creates pitDto.
     *
     * @param pit
     * @return PitDto
     */
    public PitDto pitToPPitDto(Pit pit) {
        PitDto pitDto = new PitDto();
        pitDto.setId(pit.getId());
        pitDto.setNoOfStones(pit.getNumOfStone());
        pitDto.setPlayerId(pit.getPlayerId());
        return pitDto;
    }

}
