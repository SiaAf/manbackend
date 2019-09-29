package com.example.mancala.service;

import com.example.mancala.dto.GameDto;
import com.example.mancala.dto.PlayerDto;
import com.example.mancala.model.Board;
import com.example.mancala.model.Game;
import com.example.mancala.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.Arrays;

/**
 * Player service handles different tasks regarding the player activities naming create layer, move, repeat the move
 */

@Service
public class PlayerService {
    private final Logger log = LoggerFactory.getLogger(PlayerService.class);

    @Autowired
    private GameService gameService;

    /**
     * the method creates player
     *
     * @param name1 first player name.
     * @param name2 second player name
     * @return array of players
     */
    public Player[] createPlayers(String name1, String name2) {
        Player[] players = new Player[2];
        Player player1 = new Player(name1, false, true, 0);
        Player player2 = new Player(name2, false, false, 0);
        players[0] = player1;
        players[1] = player2;
        return players;
    }

    /**
     * the method checks if the last stone goes to on of the empty pits of the player.
     * if so collects all of the stones of oppsite side and his own and put it in the big pit
     *
     * @param playerId       current playerId
     * @param numberOfStones number of stones that left
     * @param nextPit        the pit Index
     */
    public void canUserCaptureStone(int playerId, int numberOfStones, int nextPit) {
        log.info("WE ARE INM CAPTUREING");
        if (numberOfStones == 1 && nextPit != 0 && nextPit != Board.getInstance().getNumberOfPits() / 2 &&
            Game.getInstance().getBoard().getPits()[nextPit].getNumOfStone() == 1
            && Game.getInstance().getBoard().getPits()[nextPit].getPlayerId() == playerId) {
            log.info("******************* I CAN CAPTURE HOOORAAAAAAAIIIIII");
            int oppositeIndex = Game.getInstance().getBoard().getPits()[nextPit].getOppositeIndex();
            int oppositePitStones = Game.getInstance().getBoard().getPits()[oppositeIndex].getNumOfStone();
            if (oppositeIndex > Board.getInstance().getNumberOfPits() / 2) {
                Game.getInstance().getBoard().getPits()[0].setNumOfStone(Game.getInstance().getBoard().getPits()[0].getNumOfStone() + oppositePitStones + 1);
            } else {
                Game.getInstance().getBoard().getPits()[Board.getInstance().getNumberOfPits() / 2]
                    .setNumOfStone(Game.getInstance().getBoard().getPits()[Board.getInstance().getNumberOfPits() / 2].getNumOfStone() + oppositePitStones + 1);
            }
            Game.getInstance().getBoard().getPits()[nextPit].setNumOfStone(0);
            Game.getInstance().getBoard().getPits()[oppositeIndex].setNumOfStone(0);
        }
    }

    /**
     * the method checks if the user got another chance to be active or not
     * the method checks if the last stone goes anywhere other than big pits. if so, the players active status will change, otherwise stay like before.
     */
    public void updateActivePlayer() {
        int indexOfArrivingPit = Game.getInstance().getBoard().getIndexOfArrivingPit();
        if (indexOfArrivingPit != 0 && indexOfArrivingPit != Board.getInstance().getNumberOfPits() / 2) {
            Arrays.stream(Game.getInstance().getPlayers()).forEach(player -> {
                if (player.isActive()) {
                    player.setActive(false);
                } else {
                    player.setActive(true);
                }
            });
        } else {
            log.info("*************************** I AM STILL ACTIVE ");
        }
    }

    /**
     * the method calculates the final score.
     * it loops though pits and collect the reaming stones in the related big pit and finally set the score.
     */
    public void calculateScores() {
        Arrays.stream(Game.getInstance().getBoard().getPits()).forEach(pit -> {
            if (pit.getId() < Board.getInstance().getNumberOfPits() / 2 && pit.getId() != 0) {
                Game.getInstance().getBoard().getPits()[0].setNumOfStone(Game.getInstance().getBoard().getPits()[0].getNumOfStone() + pit.getNumOfStone());
            }
            if (pit.getId() >= Board.getInstance().getNumberOfPits() / 2 && pit.getId() != Board.getInstance().getNumberOfPits() / 2) {
                Game.getInstance().getBoard().getPits()[Board.getInstance().getNumberOfPits() / 2]
                    .setNumOfStone(Game.getInstance().getBoard().getPits()[Board.getInstance().getNumberOfPits() / 2].getNumOfStone() + pit.getNumOfStone());
            }
        });
        Game.getInstance().getPlayers()[0].setScore(Game.getInstance().getBoard().getPits()[0].getNumOfStone());
        Game.getInstance().getPlayers()[1].setScore(Game.getInstance().getBoard().getPits()[Board.getInstance().getNumberOfPits() / 2].getNumOfStone());
        Game.getInstance().getPlayers()[1].setScore(Game.getInstance().getBoard().getPits()[Board.getInstance().getNumberOfPits() / 2].getNumOfStone());
        whoIsTheWinner();
    }

    /**
     * the method decides who wins the match.
     */
    private void whoIsTheWinner() {
        if (Game.getInstance().getPlayers()[0].getScore() > Game.getInstance().getPlayers()[1].getScore()) {
            Game.getInstance().getPlayers()[0].setWinner(true);
        } else if (Game.getInstance().getPlayers()[0].getScore() < Game.getInstance().getPlayers()[1].getScore()) {
            Game.getInstance().getPlayers()[1].setWinner(true);
        } else {
            Game.getInstance().setGameIsADraw(true);
        }
        Game.getInstance().getPlayers()[0].setActive(false);
        Game.getInstance().getPlayers()[1].setActive(false);
    }

    /**
     * the method creates playersDto.
     *
     * @return PlayerDto[]
     */
    public PlayerDto[] playersToPlayersDto() {
        PlayerDto[] playerDtos = new PlayerDto[2];
        Player[] players = Game.getInstance().getPlayers();
        int counter = 0;
        for (Player player : players) {
            playerDtos[counter] = playerToPlayerDto(player);
            counter++;
        }
        return playerDtos;
    }

    /**
     * the method creates playerDto.
     *
     * @param player
     * @return PlayerDto
     */
    public PlayerDto playerToPlayerDto(Player player) {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setActive(player.isActive());
        playerDto.setName(player.getName());
        playerDto.setScore(player.getScore());
        playerDto.setWinner(player.isWinner());
        playerDto.setId(player.getId());
        return playerDto;
    }
}
