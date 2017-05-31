/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakesandladders.Squares;

import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import snakesandladders.main.Board;
import snakesandladders.main.Player;
import snakesandladders.main.SnakesAndLaddersV20;

/**
 *Abstract class extended by all the types of squares of the board
 * @author Zac
 */
public abstract class Square extends JLabel {

    //Fields
    private int number;

    private Color myColor = Color.decode("#43B7BA");

    //Constructors

    /**
     *Creates a square with the number given, adds the background color
     * and the text of the square.
     * @param number
     */
    public Square(int number) {
        this.number = number;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setOpaque(true);
        setBackground(myColor);
        setText("" + number);
        setHorizontalAlignment(JLabel.RIGHT);
        setVerticalAlignment(JLabel.BOTTOM);
        revalidate();
    }

    //Methods

    /**
     *Overridden by special squares to apply effects to the players and board
     * @param player1
     * @param player2
     * @param board
     * @param snl
     */
    public abstract void applyEffect(Player player1, Player player2, Board board, SnakesAndLaddersV20 snl);

    //GetterSetters

    /**
     *
     * @return
     */
    public int getNumber() {
        return number;
    }

    /**
     *
     * @param number
     */
    public void setNumber(int number) {
        this.number = number;
    }
}
