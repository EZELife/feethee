/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakesandladders.v2.pkg0.Logic.Squares;

import snakesandladders.v2.pkg0.Logic.Board;
import snakesandladders.v2.pkg0.Logic.Player;
import snakesandladders.v2.pkg0.SnakesAndLaddersV20;
import snakesandladders.v2.pkg0.Assets;
/**
 *
 * @author Zac
 */
public class CancelReverseSquare extends Square {

    /**
     *{@inheritDoc}
     * @param number
     */
    Assets assets = new Assets();
    public CancelReverseSquare(int number) {
        super(number);
        setIcon(assets.getResizedIcon("reverse_cancel", 35, 40));
    }

    /**
     *Removes reverse effect from player1 if it's true
     * @param player1
     * @param player2
     * @param board
     * @param snl
     */
    @Override
    public void applyEffect(Player player1, Player player2, Board board, SnakesAndLaddersV20 snl) {
        if (player1.isReverse()) {
            player1.setReverse(false);
            snl.getHistory().append(player1.getName()+" stepped on an cancel reverse square\n"+player1.getName()+"'s reverse effect has been removed");
        }
    }

}
