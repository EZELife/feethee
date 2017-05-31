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
public class ReverseSquare extends Square {

    /**
     *
     * @param number
     */
    Assets assets = new Assets();
    public ReverseSquare(int number) {
        super(number);
        setIcon(assets.getResizedIcon("reverse", 35, 40));
    }

    /**
     *Sets player1's reverse boolean true.
     * player1's movements are reversed
     * @param player1
     * @param player2
     * @param board
     * @param snl
     */
    @Override
    public void applyEffect(Player player1, Player player2, Board board, SnakesAndLaddersV20 snl) {
        if (!player1.isReverse()) {
            player1.setReverse(true);
            snl.getHistory().append(player1.getName()+" stepped on a reverse square\n"+player1.getName()+"'s movements have been reversed");
        }
    }

}
