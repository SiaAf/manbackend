package com.example.mancala.service;

import com.example.mancala.dto.BoardDto;
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
 * Board service handles different tasks regarding the board activities naming create board, gameOver
 */
@Service
public class BoardService {


    @Autowired
    private PitService pitService;


    /**
     * the method creates the board. The pit index 0 will be the big pit for the first player and starts from very right hand side.
     * Pit index numberOfPits/2 will be the big pit for the second player located on the very left side of the board
     *                  <-----
     * ======================================================
     *      |  6   |  5  |   4  |  3   |  2   |  1     |
     * 7    |----------------------------------------  |    0
     *      |  8   |  9  |   10 |  11  |  12  |  13    |
     * ======================================================
     *                  ----->
     *
     * @param numberOfPits   number of pits on the board including the big pits.
     * @param numberOfStones number of stones in each pit excluding big pits.
     * @param players        list of two players
     * @param player2        the second player who plays next
     * @return a complete board which has two players
     */
    public Board createBoard(int numberOfPits, int numberOfStones, Player[] players, Player player2) {
        Board.getInstance().resetBoard();
        Pit[] pits = new Pit[numberOfPits];
        Pit pit;
        Board board = Board.getInstance();
        for (int i = 0; i < numberOfPits; i++) {
            if (i < numberOfPits / 2) {
                pit = createPit(i, numberOfPits, numberOfStones, players[0]);
            } else {
                pit = createPit(i, numberOfPits, numberOfStones, players[1]);
            }
            pits[i] = pit;
        }
        board.setPits(pits);
        board.setNumberOfPits(numberOfPits);
        return board;
    }

    /**
     * @param index          index of each pit.
     * @param numberOfPits   total number of pits on the board
     * @param numberOfStones number of stones in each pit excluding big pits.
     * @param player         the players should be attached to the pit
     * @return a pit
     */
    private Pit createPit(int index, int numberOfPits, int numberOfStones, Player player) {
        if (index == 0 || index == numberOfPits / 2) {
            return new Pit(index, 0, player.getId());
        } else {
            return new Pit(index, numberOfStones, player.getId());
        }
    }

    /**
     * the method creates BoardDto.
     */
    public BoardDto boardToBoardDto() {
        Board board = Game.getInstance().getBoard();
        BoardDto boardDto = new BoardDto();
        boardDto.setPitDtos(pitService.pitsToPitsDto());
        boardDto.setIndexOfArrivingPit(board.getIndexOfArrivingPit());
        return boardDto;
    }

}
