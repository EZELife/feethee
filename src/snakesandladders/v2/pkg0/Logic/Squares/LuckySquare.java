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
