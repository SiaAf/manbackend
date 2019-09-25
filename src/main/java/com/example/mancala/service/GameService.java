package com.example.mancala.service;

import com.example.mancala.model.Board;
import com.example.mancala.model.Game;
import com.example.mancala.model.Pit;
import com.example.mancala.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Game service handles different tasks regarding the game
 */

@Service
public class GameService {

    @Autowired
    private BoardService boardService;
    @Autowired
    private PlayerService playerService;

    public Game createGame(int numberOfPits, int numberOfStones, Player[] players) {
        Board board = boardService.createBoard(numberOfPits, numberOfStones, players, players[0]);
        Game game =  new Game(board,players,false,false);
        return game;
    }

    /**
     * the method checks if the game is over.
     * it checks if the number of empty pits which owned by a player is equal to the number that we expect ((noOfPits- 2 big pits)/2) the game is over
     *
     * @param game current status of the board
     */
    public void gameOver(Game game) {
        int noOfPits = Board.numberOfPits;
        int player1 = game.getPlayers()[0].getId();
        int player2 = game.getPlayers()[1].getId();
        List<Integer> players = Arrays.stream(game.getBoard().getPits()).filter(pit -> pit.getNumOfStone() == 0 && (pit.getId() != 0 && pit.getId() != noOfPits / 2))
            .map(Pit::getPlayerId).collect(Collectors.toList());
        if (Collections.frequency(players, player1) == (noOfPits - 2) / 2) {
            game.setGameOver(true);
            playerService.calculateScores(game);
        } else if (Collections.frequency(players, player2) == (noOfPits - 2) / 2) {
            game.setGameOver(true);
            playerService.calculateScores(game);
        }
    }

}
