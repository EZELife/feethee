/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakesandladders.Squares;

import snakesandladders.main.Board;
import snakesandladders.main.Player;
import snakesandladders.main.SnakesAndLaddersV20;
import snakesandladders.main.Assets;
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
