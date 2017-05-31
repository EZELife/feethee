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
public class RethrowDiceSquare extends Square {

    /**
     *{@inheritDoc}
     * @param number
     */
    Assets assets = new Assets();
    public RethrowDiceSquare(int number) {
        super(number);
        setIcon(assets.getResizedIcon("rethrow_dice", 35, 40));
    }

    /**
     *Grants player1 another roll
     * @param player1
     * @param player2
     * @param board
     * @param snl
     */
    @Override
    public void applyEffect(Player player1, Player player2, Board board, SnakesAndLaddersV20 snl) {
        
        snl.getHistory().append(player1.getName()+" stepped on a rethrow dice square");
        
        snl.getRollButton().setSelected(false);
        snl.getRollButton().setEnabled(true);
        snl.setEndTurnCondition(false);
        
    }

}
