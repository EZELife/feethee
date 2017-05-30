/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakesandladders.v2.pkg0.Logic.Squares;

import snakesandladders.v2.pkg0.Logic.Board;
import snakesandladders.v2.pkg0.Logic.Player;
import snakesandladders.v2.pkg0.SnakesAndLaddersV20;

/**
 *
 * @author Zac
 */
public class EndSquare extends Square {

    /**
     *The last square of a board.
     * Number is always 100.
     */
    public EndSquare() {
        super(100);
    }

    /**
     *Announces the winner via history and sets endGameCondition to true
     * @param player1
     * @param player2
     * @param board
     * @param snl
     */
    @Override
    public void applyEffect(Player player1, Player player2, Board board, SnakesAndLaddersV20 snl) {
//        System.out.println("GAME FINISHED");
        snl.getHistory().append(player1.getName()+" Wins!");
        snl.setEndGameCondition(true);
    }

}
