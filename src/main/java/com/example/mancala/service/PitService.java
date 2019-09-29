package com.example.mancala.service;

import com.example.mancala.dto.PitDto;
import com.example.mancala.model.Game;
import com.example.mancala.model.Pit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PitService {

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
