package com.example.mancala.service;

import com.example.mancala.model.Game;
import com.example.mancala.model.Player;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    private final Logger log = LoggerFactory.getLogger(IntegrationTest.class);

    @Autowired
    private PlayerService playerService;
    @Autowired
    private GameService gameService;

    private Player[] players = new Player[2];

    @Test
    public void integrationTest() {
        int counter = 0;
        players = playerService.createPlayers("Siavash","Jack");

        gameService.createGame(14,6,players);
        while (!Game.getInstance().isGameOver()) {
            counter++;
            log.info("///////////////////////////////// COUNTER IS " + counter + " \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
            if (players[0].isActive()) {
                createRandomeNumber(6, 1);
            } else if (players[1].isActive()) {
                createRandomeNumber(13, 8);
            }
        }

        log.info("******************************************GAME IS OVER **********************************************");
        log.info("******************************************GAME IS OVER **********************************************");
        log.info("******************************************GAME IS OVER **********************************************");
        log.info("******************************************GAME IS OVER **********************************************");
        log.info(Game.getInstance().toString());

    }

    private void createRandomeNumber(int max, int min) {
        Random rn = new Random();
        int random = rn.nextInt((max - min) + 1) + min;
        if (min == 1) {
            letsPlay( players[0], random);
        } else {
            letsPlay( players[1], random);
        }
    }

    private void letsPlay(Player player, int random) {
        if (Game.getInstance().getBoard().getPits()[random].getNumOfStone() != 0) {
            System.out.println("+++++++++++++++++++++++++++ PLAYER " + player + " choosed " + random);
            gameService.move(player.getId(), random);
            System.out.println(Game.getInstance().toString());
        } else {
            if (random <= 6 && random >= 1) {
                createRandomeNumber(6, 1);
            } else {
                createRandomeNumber(13, 8);
            }
        }
    }
}
