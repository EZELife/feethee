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
public class LoveSquare extends Square {

    /**
     *{@inheritDoc}
     * @param number
     */
    public LoveSquare(int number) {
        super(number);
    }

    /**
     *Sets player2's square to the first square
     * @param player1
     * @param player2
     * @param board
     * @param snl
     */
    @Override
    public void applyEffect(Player player1, Player player2, Board board, SnakesAndLaddersV20 snl) {
        
        snl.getHistory().append(player1.getName()+" stepped on a love square\n"+player2.getName()+" has moved to square 1");
        
        player2.setSquare(board.getBoardSquare(1));
    }

}
