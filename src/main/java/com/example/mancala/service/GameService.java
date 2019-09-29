package com.example.mancala.service;

import com.example.mancala.dto.GameDto;
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

    public void createGame(int numberOfPits, int numberOfStones, Player[] players) {
        Game.getInstance().resetGame();
        Board board = boardService.createBoard(numberOfPits, numberOfStones, players, players[0]);
        Game game = Game.getInstance();
        game.setBoard(board);
        game.setPlayers(players);
    }

    /**
     * the method checks if the game is over.
     * it checks if the number of empty pits which owned by a player is equal to the number that we expect ((noOfPits- 2 big pits)/2) the game is over
     */
    public void gameOver() {
        int noOfPits = Board.getInstance().getNumberOfPits();
        int player1 = Game.getInstance().getPlayers()[0].getId();

        int player2 = Game.getInstance().getPlayers()[1].getId();
        List<Integer> players = Arrays.stream(Game.getInstance().getBoard().getPits())
            .filter(pit -> pit.getNumOfStone() == 0 && (pit.getId() != 0 && pit.getId() != noOfPits / 2))
            .map(Pit::getPlayerId).collect(Collectors.toList());
        if (Collections.frequency(players, player1) == (noOfPits - 2) / 2) {
            Game.getInstance().setGameOver(true);
            playerService.calculateScores();
        } else if (Collections.frequency(players, player2) == (noOfPits - 2) / 2) {
            Game.getInstance().setGameOver(true);
            playerService.calculateScores();
        }
    }

    /**
     * the method creates GameDto.
     */
    public GameDto gameToGameDto() {
        GameDto gameDto = new GameDto();
        Game game = Game.getInstance();
        gameDto.setBoard(boardService.boardToBoardDto());
        gameDto.setPlayers(playerService.playersToPlayersDto());
        gameDto.setGameIsADraw(game.isGameIsADraw());
        gameDto.setGameOver(game.isGameOver());
        return gameDto;
    }


    /**
     * the method fulfil the move activity. if the user is active then it's his/her turn to play.
     * the method gets the pit index, based on that gets the number of stones which is in the pit. After this step the current pit's stones gets 0
     * Now it starts to propagate the stones to the other pits one by one till the stones get finished.
     *
     * @param playerId the playerId of active player
     * @param pitIndex the pit index that the active players wants play with it
     * @return updated game
     */
    public Game move(int playerId, int pitIndex) {
        int numberOfStones = Game.getInstance().getBoard().getPits()[pitIndex].getNumOfStone();
        Game.getInstance().getBoard().getPits()[pitIndex].setNumOfStone(0);
        int nextPit = pitIndex + 1;
        while (numberOfStones > 0) {
            if (nextPit > Board.getInstance().getNumberOfPits() - 1) {
                nextPit = 0;
            }
            if ((nextPit == 0 || nextPit == Board.getInstance().getNumberOfPits() / 2)
                && Game.getInstance().getBoard().getPits()[nextPit].getPlayerId() != playerId) {
                nextPit++;
            }
            Game.getInstance().getBoard().getPits()[nextPit].setNumOfStone(Game.getInstance().getBoard().getPits()[nextPit].getNumOfStone() + 1);
            Game.getInstance().getBoard().setIndexOfArrivingPit(nextPit);
            playerService.canUserCaptureStone(playerId, numberOfStones, nextPit);
            numberOfStones--;
            nextPit++;
        }
        playerService.updateActivePlayer();
        gameOver();
        return Game.getInstance();
    }
}
