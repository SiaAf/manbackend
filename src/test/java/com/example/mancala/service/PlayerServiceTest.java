package com.example.mancala.service;

import com.example.mancala.model.Board;
import com.example.mancala.model.Game;
import com.example.mancala.model.Pit;
import com.example.mancala.model.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlayerServiceTest {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private GameService gameService;

    @Test
    public void testTheMove() {
        Player[] players = new Player[2];
        Pit[] pits = new Pit[14];
        Player player1 = new Player("Siavash", false, true, 0);
        Player player2 = new Player("Jack", false, false, 0);
        players[0] = player1;
        players[1] = player2;
        Pit pit1 = new Pit(0, 0, players[0].getId());
        Pit pit2 = new Pit(1, 6, players[0].getId());
        Pit pit3 = new Pit(2, 6, players[0].getId());
        Pit pit4 = new Pit(3, 6, players[0].getId());
        Pit pit5 = new Pit(4, 6, players[0].getId());
        Pit pit6 = new Pit(5, 6, players[0].getId());
        Pit pit7 = new Pit(6, 6, players[0].getId());
        Pit pit8 = new Pit(7, 0, players[1].getId());
        Pit pit9 = new Pit(8, 6, players[1].getId());
        Pit pit10 = new Pit(9, 6, players[1].getId());
        Pit pit11 = new Pit(10, 6, players[1].getId());
        Pit pit12 = new Pit(11, 6, players[1].getId());
        Pit pit13 = new Pit(12, 6, players[1].getId());
        Pit pit14 = new Pit(13, 6, players[1].getId());
        pits[0] = pit1;
        pits[1] = pit2;
        pits[2] = pit3;
        pits[3] = pit4;
        pits[4] = pit5;
        pits[5] = pit6;
        pits[6] = pit7;
        pits[7] = pit8;
        pits[8] = pit9;
        pits[9] = pit10;
        pits[10] = pit11;
        pits[11] = pit12;
        pits[12] = pit13;
        pits[13] = pit14;
        Board.getInstance().resetBoard();
        Board board = Board.getInstance();
        board.setPits(pits);
        Board.getInstance().setNumberOfPits(14);
        Game.getInstance().resetGame();
        Game game = Game.getInstance();
        game.setPlayers(players);
        game.setBoard(board);
        Game currentGame = gameService.move(players[0].getId(), 4);
        assertEquals(currentGame.getBoard().getPits()[4].getNumOfStone(), 0);
        assertEquals(currentGame.getBoard().getPits()[0].getNumOfStone(), 0);
        assertEquals(currentGame.getBoard().getPits()[7].getNumOfStone(), 0);
        assertEquals(currentGame.getBoard().getPits()[8].getNumOfStone(), 7);
    }

    @Test
    public void testTheMoveOnBigPits() {
        Player[] players = new Player[2];
        Pit[] pits = new Pit[14];
        Player player1 = new Player("Siavash", false, true, 0);
        Player player2 = new Player("Jack", false, false, 0);
        players[0] = player1;
        players[1] = player2;
        Pit pit1 = new Pit(0, 0, players[0].getId());
        Pit pit2 = new Pit(1, 7, players[0].getId());
        Pit pit3 = new Pit(2, 7, players[0].getId());
        Pit pit4 = new Pit(3, 6, players[0].getId());
        Pit pit5 = new Pit(4, 6, players[0].getId());
        Pit pit6 = new Pit(5, 0, players[0].getId());
        Pit pit7 = new Pit(6, 7, players[0].getId());
        Pit pit8 = new Pit(7, 0, players[1].getId());
        Pit pit9 = new Pit(8, 0, players[1].getId());
        Pit pit10 = new Pit(9, 8, players[1].getId());
        Pit pit11 = new Pit(10, 8, players[1].getId());
        Pit pit12 = new Pit(11, 8, players[1].getId());
        Pit pit13 = new Pit(12, 8, players[1].getId());
        Pit pit14 = new Pit(13, 7, players[1].getId());
        pits[0] = pit1;
        pits[1] = pit2;
        pits[2] = pit3;
        pits[3] = pit4;
        pits[4] = pit5;
        pits[5] = pit6;
        pits[6] = pit7;
        pits[7] = pit8;
        pits[8] = pit9;
        pits[9] = pit10;
        pits[10] = pit11;
        pits[11] = pit12;
        pits[12] = pit13;
        pits[13] = pit14;
        Board.getInstance().resetBoard();
        Board board = Board.getInstance();
        board.setPits(pits);
        Board.getInstance().setNumberOfPits(14);
        Game.getInstance().resetGame();
        Game game = Game.getInstance();
        game.setPlayers(players);
        game.setBoard(board);
        Game currentBoard = gameService.move(players[0].getId(), 6);
        assertEquals(currentBoard.getBoard().getPits()[0].getNumOfStone(), 1);
        assertEquals(currentBoard.getBoard().getPits()[6].getNumOfStone(), 0);
        assertEquals(currentBoard.getBoard().getPits()[7].getNumOfStone(), 0);
        assertEquals(currentBoard.getBoard().getPits()[8].getNumOfStone(), 1);
    }

    @Test
    public void testUpdateActivePlayer() {
        Player[] players = new Player[2];
        Pit[] pits = new Pit[14];
        Player player1 = new Player("Siavash", false, true, 0);
        Player player2 = new Player("Jack", false, false, 0);
        players[0] = player1;
        players[1] = player2;
        Pit pit1 = new Pit(0, 0, players[0].getId());
        Pit pit2 = new Pit(1, 7, players[0].getId());
        Pit pit3 = new Pit(2, 7, players[0].getId());
        Pit pit4 = new Pit(3, 6, players[0].getId());
        Pit pit5 = new Pit(4, 6, players[0].getId());
        Pit pit6 = new Pit(5, 0, players[0].getId());
        Pit pit7 = new Pit(6, 7, players[0].getId());
        Pit pit8 = new Pit(7, 0, players[1].getId());
        Pit pit9 = new Pit(8, 0, players[1].getId());
        Pit pit10 = new Pit(9, 8, players[1].getId());
        Pit pit11 = new Pit(10, 8, players[1].getId());
        Pit pit12 = new Pit(11, 8, players[1].getId());
        Pit pit13 = new Pit(12, 8, players[1].getId());
        Pit pit14 = new Pit(13, 7, players[1].getId());
        pits[0] = pit1;
        pits[1] = pit2;
        pits[2] = pit3;
        pits[3] = pit4;
        pits[4] = pit5;
        pits[5] = pit6;
        pits[6] = pit7;
        pits[7] = pit8;
        pits[8] = pit9;
        pits[9] = pit10;
        pits[10] = pit11;
        pits[11] = pit12;
        pits[12] = pit13;
        pits[13] = pit14;
        Board.getInstance().resetBoard();
        Board board = Board.getInstance();
        board.setPits(pits);
        Board.getInstance().setNumberOfPits(14);
        Game.getInstance().resetGame();
        Game game = Game.getInstance();
        game.setPlayers(players);
        game.setBoard(board);
        gameService.move(game.getPlayers()[0].getId(), 6);
        System.out.println(Arrays.toString(board.getPits()));
        assertTrue(game.getPlayers()[0].isActive());
        gameService.move( players[0].getId(), 1);
        assertEquals(board.getPits()[7].getNumOfStone(), 0);
        assertEquals(board.getPits()[9].getNumOfStone(), 10);
        assertEquals(board.getPits()[10].getNumOfStone(), 9);
        assertEquals(board.getPits()[0].getNumOfStone(), 1);
        assertFalse(players[0].isActive());
        assertTrue(players[1].isActive());
    }

    @Test
    public void testCalculateScores() {
        Player[] players = new Player[2];
        Pit[] pits = new Pit[14];
        Player player1 = new Player("Siavash", false, true, 0);
        Player player2 = new Player("Jack", false, false, 0);
        players[0] = player1;
        players[1] = player2;
        Pit pit1 = new Pit(0, 18, players[0].getId());
        Pit pit2 = new Pit(1, 0, players[0].getId());
        Pit pit3 = new Pit(2, 0, players[0].getId());
        Pit pit4 = new Pit(3, 6, players[0].getId());
        Pit pit5 = new Pit(4, 4, players[0].getId());
        Pit pit6 = new Pit(5, 6, players[0].getId());
        Pit pit7 = new Pit(6, 6, players[0].getId());
        Pit pit8 = new Pit(7, 32, players[1].getId());
        Pit pit9 = new Pit(8, 0, players[1].getId());
        Pit pit10 = new Pit(9, 0, players[1].getId());
        Pit pit11 = new Pit(10, 0, players[1].getId());
        Pit pit12 = new Pit(11, 0, players[1].getId());
        Pit pit13 = new Pit(12, 0, players[1].getId());
        Pit pit14 = new Pit(13, 0, players[1].getId());
        pits[0] = pit1;
        pits[1] = pit2;
        pits[2] = pit3;
        pits[3] = pit4;
        pits[4] = pit5;
        pits[5] = pit6;
        pits[6] = pit7;
        pits[7] = pit8;
        pits[8] = pit9;
        pits[9] = pit10;
        pits[10] = pit11;
        pits[11] = pit12;
        pits[12] = pit13;
        pits[13] = pit14;
        Board.getInstance().resetBoard();
        Board board = Board.getInstance();
        board.setPits(pits);
        Board.getInstance().setNumberOfPits(14);
        Game.getInstance().resetGame();
        Game game = Game.getInstance();
        game.setPlayers(players);
        game.setBoard(board);
        playerService.calculateScores();
        assertEquals(game.getPlayers()[1].getScore(),32);
        assertEquals(game.getPlayers()[0].getScore(),40);
        assertTrue(game.getPlayers()[0].isWinner());
        assertFalse(game.getPlayers()[1].isWinner());
    }

}
