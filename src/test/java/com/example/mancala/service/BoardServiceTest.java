package com.example.mancala.service;

import com.example.mancala.model.Board;
import com.example.mancala.model.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BoardServiceTest {

    @Autowired
    BoardService boardService;

    @Test
    public void testCreateBoard() {
        Player[] players = new Player[2];
        Player player1 = new Player("Siavash", false, true, 0);
        Player player2 = new Player("Jack", false, false, 0);
        players[0] = player1;
        players[1] = player2;
        Board board = boardService.createBoard(14,6,players,player1);
        assertEquals(board.getPits().length, 14);
        assertEquals(board.getPits()[0].getNumOfStone(), 0);
        assertEquals(board.getPits()[0].getPlayerId(), player1.getId());
        assertEquals(board.getPits()[1].getPlayerId(), player1.getId());
        assertEquals(board.getPits()[1].getNumOfStone(), 6);
        assertEquals(board.getPits()[13].getNumOfStone(), 6);
        assertEquals(board.getPits()[13].getPlayerId(), player2.getId());
        assertEquals(board.getPits()[7].getNumOfStone(), 0);
    }
}
