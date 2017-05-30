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
public class ExplosionSquare extends Square {

    /**
     *{@inheritDoc}
     * @param number
     */
    Assets assets = new Assets();
    public ExplosionSquare(int number) {
        super(number);
        setIcon(assets.getResizedIcon("explosion", 35, 40));
    }

    /**
     *Sets player's square to the first square
     * @param player1
     * @param player2
     * @param board
     * @param snl
     */
    @Override
    public void applyEffect(Player player1, Player player2, Board board, SnakesAndLaddersV20 snl) {
        player1.setSquare(board.getBoardSquare(1));
        snl.getHistory().append(player1.getName()+" stepped on an explosion square\n"+player1.getName()+" moves to square 1");
    }

}
