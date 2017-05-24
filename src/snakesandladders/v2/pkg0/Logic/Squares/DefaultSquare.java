/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakesandladders.v2.pkg0.Logic.Squares;

import snakesandladders.v2.pkg0.Logic.Board;
import snakesandladders.v2.pkg0.Logic.Player;
import snakesandladders.v2.pkg0.Logic.Squares.Square;
import snakesandladders.v2.pkg0.SnakesAndLaddersV20;

/**
 *
 * @author Zac
 */
public class DefaultSquare extends Square {
    
    private boolean occupied;

    public DefaultSquare(int number) {
        super(number);
        occupied = false;
    }
    
    public DefaultSquare(int number, boolean occupied) {
        super(number);
        this.occupied = occupied;
    }

    @Override
    public void applyEffect(Player player1, Player player2, Board board, SnakesAndLaddersV20 snl) {
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
    
    

}
