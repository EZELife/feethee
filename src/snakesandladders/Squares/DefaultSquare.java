/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakesandladders.Squares;

import snakesandladders.main.Board;
import snakesandladders.main.Player;
import snakesandladders.main.SnakesAndLaddersV20;

/**
 *
 * @author Zac
 */
public class DefaultSquare extends Square {
    
    private boolean occupied;

    /**
     *{@inheritDoc}
     * Also initializes occupied as false
     * A square is considered occupied if it is the tail of a snake or the top
     * of a ladder
     * @param number
     */
    public DefaultSquare(int number) {
        super(number);
        occupied = false;
    }
    
    /**
     *{@inheritDoc}
     * Also initializes occupied as given
     * A square is considered occupied if it is the tail of a snake or the top
     * of a ladder
     * @param number
     * @param occupied
     */
    public DefaultSquare(int number, boolean occupied) {
        super(number);
        this.occupied = occupied;
    }

    /**
     *No effects are applied
     * @param player1
     * @param player2
     * @param board
     * @param snl
     */
    @Override
    public void applyEffect(Player player1, Player player2, Board board, SnakesAndLaddersV20 snl) {
    }

    /**
     *
     * @return
     */
    public boolean isOccupied() {
        return occupied;
    }

    /**
     *
     * @param occupied
     */
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
    
    

}
