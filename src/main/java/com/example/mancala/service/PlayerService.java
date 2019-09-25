package com.example.mancala.service;

import com.example.mancala.model.Board;
import com.example.mancala.model.Game;
import com.example.mancala.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.Arrays;

/**
 * Player service handles different tasks regarding the player activities naming create layer, move, repeat the move
 */

@Service
public class PlayerService {

    @Autowired
    BoardService boardService;

    @Autowired
    GameService gameService;

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
     * the method fulfil the move activity. if the user is active then it's his/her turn to play.
     * the method gets the pit index, based on that gets the number of stones which is in the pit. After this step the current pit's stones gets 0
     * Now it starts to propagate the stones to other pits one by one till the stones get finished.
     *
     * @param game     the current game.
     * @param player   the player who is active now
     * @param pitIndex the pit index that the active players wants play with it
     * @return updated game
     */
    public Game move(Game game, Player player, int pitIndex) {
        int numberOfStones = game.getBoard().getPits()[pitIndex].getNumOfStone();
        game.getBoard().getPits()[pitIndex].setNumOfStone(0);
        int nextPit = pitIndex + 1;
        while (numberOfStones > 0) {
            if (nextPit > Board.numberOfPits - 1) {
                nextPit = 0;
            }
            if ((nextPit == 0 || nextPit == Board.numberOfPits / 2) && game.getBoard().getPits()[nextPit].getPlayerId() != player.getId()) {
                nextPit++;
            }
            game.getBoard().getPits()[nextPit].setNumOfStone(game.getBoard().getPits()[nextPit].getNumOfStone() + 1);
            game.getBoard().setIndexOfArrivingPit(nextPit);
            canUserCaptureStone(game, player, numberOfStones, nextPit);
            numberOfStones--;
            nextPit++;
        }
        updateActivePlayer(game);
        gameService.gameOver(game);
        return game;
    }

    /**
     * the method checks if the last stone goes to on of the empty pits of the player.
     * if so collects all of the stones of oppsite side and his own and put it in the big pit
     *
     * @param game           the current game.
     * @param player         current player
     * @param numberOfStones number of stones that left
     * @param nextPit        the pit Index
     */
    private void canUserCaptureStone(Game game, Player player, int numberOfStones, int nextPit) {
        System.out.println("WE ARE INM CAPTUREING");
        System.out.println(game.getBoard().getPits()[nextPit].getPlayerId());
        System.out.println(player.getId());
        System.out.println(numberOfStones);
        System.out.println(game.getBoard().getPits()[nextPit].getNumOfStone());
        if (numberOfStones == 1 && nextPit != 0 && nextPit != Board.numberOfPits / 2 &&
            game.getBoard().getPits()[nextPit].getNumOfStone() == 1 && game.getBoard().getPits()[nextPit].getPlayerId() == player.getId()) {
            System.out.println("I CAN CAPTURE HOOORAAAAAAAIIIIII");
            int oppositeIndex = game.getBoard().getPits()[nextPit].getOppositeIndex();
            int oppositePitStones = game.getBoard().getPits()[oppositeIndex].getNumOfStone();
            if (oppositeIndex > Board.numberOfPits / 2) {
                game.getBoard().getPits()[0].setNumOfStone(game.getBoard().getPits()[0].getNumOfStone() + oppositePitStones + 1);
            } else {
                game.getBoard().getPits()[Board.numberOfPits / 2]
                    .setNumOfStone(game.getBoard().getPits()[Board.numberOfPits / 2].getNumOfStone() + oppositePitStones + 1);
            }
            game.getBoard().getPits()[nextPit].setNumOfStone(0);
            game.getBoard().getPits()[oppositeIndex].setNumOfStone(0);
        }
    }

    /**
     * the method checks if the user got another chance to be active or not
     * the method checks if the last stone goes anywhere other than big pits. if so the players active statuses will change, otherwise stay like before.
     *
     * @param game the current game.
     */
    private void updateActivePlayer(Game game) {
        int indexOfArrivingPit = game.getBoard().getIndexOfArrivingPit();
        if (indexOfArrivingPit != 0 && indexOfArrivingPit != Board.numberOfPits / 2) {
            Arrays.stream(game.getPlayers()).forEach(player -> {
                if (player.isActive()) {
                    player.setActive(false);
                } else {
                    player.setActive(true);
                }
            });
        } else {
            System.out.println("())))))))))))))))))))))))))))))))))))))))))) I AM STILL ACTIVE ");
        }
    }

    /**
     * the method calculates the final score.
     * it loops though pits and collect the reaming stones in the related big pit and finally set the score.
     *
     * @param game current status of the game
     */
    public void calculateScores(Game game) {
        Arrays.stream(game.getBoard().getPits()).forEach(pit -> {
            if (pit.getId() < Board.numberOfPits / 2 && pit.getId() != 0) {
                game.getBoard().getPits()[0].setNumOfStone(game.getBoard().getPits()[0].getNumOfStone() + pit.getNumOfStone());
            }
            if (pit.getId() >= Board.numberOfPits / 2 && pit.getId() != Board.numberOfPits / 2) {
                game.getBoard().getPits()[Board.numberOfPits / 2]
                    .setNumOfStone(game.getBoard().getPits()[Board.numberOfPits / 2].getNumOfStone() + pit.getNumOfStone());
            }
        });
        game.getPlayers()[0].setScore(game.getBoard().getPits()[0].getNumOfStone());
        game.getPlayers()[1].setScore(game.getBoard().getPits()[Board.numberOfPits / 2].getNumOfStone());
        game.getPlayers()[1].setScore(game.getBoard().getPits()[Board.numberOfPits / 2].getNumOfStone());
        whoIsTheWinner(game);
    }

    /**
     * the method decides who wins the match.
     *
     * @param game current status of the game
     */
    private void whoIsTheWinner(Game game) {
        if (game.getPlayers()[0].getScore() > game.getPlayers()[1].getScore()) {
            game.getPlayers()[0].setWinner(true);
        } else if (game.getPlayers()[0].getScore() < game.getPlayers()[1].getScore()) {
            game.getPlayers()[1].setWinner(true);
        } else {
            game.setGameIsADraw(true);
        }
        game.getPlayers()[0].setActive(false);
        game.getPlayers()[1].setActive(false);
    }
}
