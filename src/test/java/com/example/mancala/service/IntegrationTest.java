package com.example.mancala.service;

import com.example.mancala.model.Board;
import com.example.mancala.model.Game;
import com.example.mancala.model.Pit;
import com.example.mancala.model.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private GameService gameService;

    private Player[] players = new Player[2];
    private Game game;

    @Test
    public void integrationTest() {
        int counter = 0;
        players = playerService.createPlayers("Siavash","Jack");

        game = gameService.createGame(14,6,players);
        while (!game.isGameOver()) {
            counter++;
            System.out.println("///////////////////////////////// COUNTER IS " + counter + " \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
            if (players[0].isActive()) {
                createRandomeNumber(6, 1);
            } else if (players[1].isActive()) {
                createRandomeNumber(13, 8);
            }

        }

        System.out.println("******************************************GAME IS OVER **********************************************");
        System.out.println("******************************************GAME IS OVER **********************************************");
        System.out.println("******************************************GAME IS OVER **********************************************");
        System.out.println("******************************************GAME IS OVER **********************************************");
        System.out.println(game.toString());

    }

    private void createRandomeNumber(int max, int min) {
        Random rn = new Random();
        int random = rn.nextInt((max - min) + 1) + min;
        if (min == 1) {
            letsPlay(game, players[0], random);
        } else {
            letsPlay(game, players[1], random);
        }

    }

    private void letsPlay(Game game, Player player, int random) {
        if (game.getBoard().getPits()[random].getNumOfStone() != 0) {
            System.out.println("+++++++++++++++++++++++++++ PLAYER " + player + " choosed " + random);
            playerService.move(game, player, random);
            System.out.println(game.toString());
        } else {
            if (random <= 6 && random >= 1) {
                createRandomeNumber(6, 1);
            } else {
                createRandomeNumber(13, 8);
            }
        }

    }

}
