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
public abstract class Square {

    //Fields
    private int number;

    //Constructors
    public Square(int number) {
        this.number = number;
    }

    //Methods
    public abstract void applyEffect(Player player1, Player player2, Board board, SnakesAndLaddersV20 snl);

    //GetterSetters
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}