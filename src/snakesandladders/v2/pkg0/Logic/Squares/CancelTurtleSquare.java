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
public class CancelTurtleSquare extends Square {

    public CancelTurtleSquare(int number) {
        super(number);
    }

    @Override
    public void applyEffect(Player player1, Player player2, Board board, SnakesAndLaddersV20 snl) {
        if (player1.isTurtle()) {
            player1.setTurtle(false);
            snl.getHistory().append(player1.getName()+" stepped on an cancel turtle square\n" +player1.getName()+ "'s turtle effect has been removed");
        }
        player1.setAppliedEffects(true);
    }

}
