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
public class StartSquare extends Square {

    /**
     *{@inheritDoc}
     * The first square.
     * It's number is always 1.
     * Removes player's reverse effect if the value is true
     */
    public StartSquare() {
        super(1);
    }

    /**
     *Removes player1's reverse effect if it's true
     * @param player1
     * @param player2
     * @param board
     * @param snl
     */
    @Override
    public void applyEffect(Player player1, Player player2, Board board, SnakesAndLaddersV20 snl) {
        if(player1.isReverse()) {
            player1.setReverse(false);
            snl.getHistory().append(player1.getName()+"'s reverse effect has been removed");
        }
    }

}
