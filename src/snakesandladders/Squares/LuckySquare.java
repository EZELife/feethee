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
public class LuckySquare extends Square {

    /**
     *{@inheritDoc}
     * @param number
     */
    Assets assets = new Assets();
    public LuckySquare(int number) {
        super(number);
        setIcon(assets.getResizedIcon("clover4", 35, 40));
    }

    /**
     *Grants player1 another roll and sets the lucky boolean to true so that in
     * case player1 rolls a 6, he wins
     * @param player1
     * @param player2
     * @param board
     * @param snl
     */
    @Override
    public void applyEffect(Player player1, Player player2, Board board, SnakesAndLaddersV20 snl) {
        
        snl.getHistory().append(player1.getName()+" stepped on a lucky square");
        
        player1.setLucky(true);
        snl.getRollButton().setSelected(false);
        snl.getRollButton().setEnabled(true);
        snl.setEndTurnCondition(false);
        
    }

}
