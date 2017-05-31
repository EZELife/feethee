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
public class SnakeSquare extends Square {

    private Square dest;

    /**
     *{@inheritDoc}
     * @param number
     * @param dest
     */
    public SnakeSquare(int number, Square dest) {
        super(number);
        this.dest = dest;
    }

    /**
     *Moves player1 from current square to the squares dest
     * @param player1
     * @param player2
     * @param board
     * @param snl
     */
    @Override
    public void applyEffect(Player player1, Player player2, Board board, SnakesAndLaddersV20 snl) {
        player1.setSquare(dest);
        snl.getHistory().append(player1.getName()+" has stepped on a snake\n"+player1.getName()+" has moved to square "+player1.getSquare().getNumber());
    }

    /**
     *
     * @return
     */
    public Square getDest() {
        return dest;
    }

    /**
     *
     * @param dest
     */
    public void setDest(Square dest) {
        this.dest = dest;
    }

}
