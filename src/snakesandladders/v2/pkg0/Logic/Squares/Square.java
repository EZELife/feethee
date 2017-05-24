/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakesandladders.v2.pkg0.Logic.Squares;

import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import snakesandladders.v2.pkg0.Logic.Board;
import snakesandladders.v2.pkg0.Logic.Player;
import snakesandladders.v2.pkg0.SnakesAndLaddersV20;

/**
 *
 * @author Zac
 */
public abstract class Square extends JLabel {

    //Fields
    private int number;
    private int gridPos = 0;

    private Color myColor = Color.decode("#43B7BA");

    //Constructors
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
    public abstract void applyEffect(Player player1, Player player2, Board board, SnakesAndLaddersV20 snl);

    //GetterSetters
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setGridPos(int gridPos) {
        this.gridPos = gridPos;
    }

    public int getGridPos() {
        return gridPos;
    }
}
