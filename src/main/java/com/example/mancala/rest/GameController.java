package com.example.mancala.rest;

import com.example.mancala.dto.GameDto;
import com.example.mancala.dto.InitializationDto;
import com.example.mancala.model.Game;
import com.example.mancala.model.Pit;
import com.example.mancala.model.Player;
import com.example.mancala.service.GameService;
import com.example.mancala.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/game")
public class GameController {
    private final Logger log = LoggerFactory.getLogger(GameController.class);
    @Autowired
    PlayerService playerService;
    @Autowired
    GameService gameService;

    private Player[] players = new Player[2];

    @RequestMapping(value = "/create",
                    method = RequestMethod.POST,
                    produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity createGame(@RequestBody InitializationDto initializationDto) {
        if (initializationDto.getNoOfPits() != 14) {
            log.info("number of pits has wrongly choosen : " + initializationDto.getNoOfPits());
            return new ResponseEntity<>("The game should have exactly 14 pits", HttpStatus.BAD_REQUEST);
        }
        if (initializationDto.getNoOfStones() != 6) {
            log.info("number of stones has wrongly choosen : " + initializationDto.getNoOfStones());
            return new ResponseEntity<>("Each pit should have exactly 6 stones", HttpStatus.BAD_REQUEST);
        }
        players = playerService.createPlayers(initializationDto.getName1(), initializationDto.getName2());
        gameService.createGame(initializationDto.getNoOfPits(), initializationDto.getNoOfStones(), players);
        return new ResponseEntity<>(gameService.gameToGameDto(), HttpStatus.OK);
    }

    @RequestMapping(value = "/currentgame",
                    method = RequestMethod.GET,
                    produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<GameDto> getGame() {
        return new ResponseEntity<>(gameService.gameToGameDto(), HttpStatus.OK);
    }

    @RequestMapping(value = "/move",
                    method = RequestMethod.POST,
                    produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity move(@RequestBody GameDto gameDto) {
        log.info("playerId: "  + gameDto.getPlayerIdWhoPlayed()+ " wants to act from " + gameDto.getStartIndex());
        Game game = Game.getInstance();
        Player playerWhoPlayed = null;
        Pit SelectedPit = game.getBoard().getPits()[gameDto.getStartIndex()];
        Player[] players = game.getPlayers();
        for (Player player : players) {
            if (player.getId() == gameDto.getPlayerIdWhoPlayed()) {
                playerWhoPlayed = player;
            }
        }
        assert playerWhoPlayed != null;
        if (!playerWhoPlayed.isActive() || SelectedPit.getPlayerId() != playerWhoPlayed.getId()) {
            new ResponseEntity<>("You are not allowed to do this act ", HttpStatus.OK);
        }
        playerService.move(gameDto.getPlayerIdWhoPlayed(), gameDto.getStartIndex());
        return new ResponseEntity<>(gameService.gameToGameDto(), HttpStatus.OK);
    }

}
