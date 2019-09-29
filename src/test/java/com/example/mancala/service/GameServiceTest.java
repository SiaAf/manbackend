package com.example.mancala.service;

import com.example.mancala.model.Board;
import com.example.mancala.model.Game;
import com.example.mancala.model.Pit;
import com.example.mancala.model.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameServiceTest {

    @Autowired
    GameService gameService;

    @Test
    public void testGameIsOver() {
        Pit[] pits = new Pit[14];
        Player[] players = new Player[2];
        Player player1 = new Player("Siavash", false, true, 0);
        Player player2 = new Player("Jack", false, false, 0);
        players[0] = player1;
        players[1] = player2;

        Pit pit1 = new Pit(0, 20, players[0].getId());
        Pit pit2 = new Pit(1, 1,players[0].getId());
        Pit pit3 = new Pit(2, 1,players[0].getId());
        Pit pit4 = new Pit(3, 5, players[0].getId());
        Pit pit5 = new Pit(4, 2, players[0].getId());
        Pit pit6 = new Pit(5, 0, players[0].getId());
        Pit pit7 = new Pit(6, 0, players[0].getId());
        Pit pit8 = new Pit(7, 43, players[1].getId());
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
//        Board board = new Board(pits, pits.length);
        Board.getInstance().resetBoard();
        Board board = Board.getInstance();
        board.setPits(pits);
        Board.getInstance().setNumberOfPits(pits.length);
//        Game game = new Game(board, players, false, false);
        Game.getInstance().resetGame();
        Game game = Game.getInstance();
        game.setBoard(board);
        game.setPlayers(players);
        gameService.gameOver();
        assertTrue(game.isGameOver());
    }
}
