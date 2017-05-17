/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakesandladders.v2.pkg0.Logic.Squares;

import snakesandladders.v2.pkg0.Logic.Board;
import snakesandladders.v2.pkg0.Logic.Player;
import snakesandladders.v2.pkg0.SnakesAndLaddersV20;

/**
 *
 * @author Zac
 */
public class LadderSquare extends Square {

    private Square dest;

    public LadderSquare(int number, Square dest) {
        super(number);
        this.dest = dest;
    }

    @Override
    public void applyEffect(Player player1, Player player2, Board board, SnakesAndLaddersV20 snl) {
        player1.setSquare(dest);
        snl.getHistory().append(player1.getName()+" stepped on a ladder\n"+player1.getName()+" has moved to square "+player1.getSquare().getNumber());
    }

    public Square getDest() {
        return dest;
    }

    public void setDest(Square dest) {
        this.dest = dest;
    }

}
