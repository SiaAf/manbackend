package com.example.mancala.rest;

import com.example.mancala.dto.GameDto;
import com.example.mancala.dto.InitializationDto;
import com.example.mancala.model.Game;
import com.example.mancala.model.Player;
import com.example.mancala.service.GameService;
import com.example.mancala.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    PlayerService playerService;
    @Autowired
    GameService gameService;

    private Game game;
    private Player[] players = new Player[2];

    @RequestMapping(value = "/create",
                    method = RequestMethod.POST,
                    produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Game> createGame(@RequestBody InitializationDto initializationDto) {
        players = playerService.createPlayers(initializationDto.getName1(), initializationDto.getName2());
        game = gameService.createGame(initializationDto.getNoOfPits(), initializationDto.getNoOfStones(), players);
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    @RequestMapping(value = "/currentgame",
                    method = RequestMethod.GET,
                    produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Game> getGame() {
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    @RequestMapping(value = "/move",
                    method = RequestMethod.POST,
                    produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Game> move(@RequestBody GameDto gameDto) {
        game = playerService.move(gameDto.getGame(),gameDto.getPlayer(), gameDto.getIndex());
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

}
