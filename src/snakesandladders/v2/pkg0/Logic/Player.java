/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//A TEST
package snakesandladders.v2.pkg0.Logic;

import javax.swing.JLabel;
import snakesandladders.v2.pkg0.Assets;
import snakesandladders.v2.pkg0.Logic.Squares.Square;

/**
 *
 * @author Zac
 */
public class Player extends JLabel{

    //Fields
    private Square square;
    private String name, color;
    private boolean reverse, turtle, lucky;
    private Assets assets; //soc code

    //Constructors
    public Player(Square square, String name, String color) {
        this.square = square;
        this.name = name;
        this.color = color;
        reverse = turtle = lucky = false;
        assets = new Assets(); //soc code
    }

    //Methods
    public void advance(int steps, Board board) {

        int endPos;
        
        if(lucky) {
            lucky = false;
            if(steps==6){
                square = board.getBoardSquare(100);
                return;
            }
        }

        if (turtle) {
            steps = Math.max(steps/2, 1);
        }

        if (reverse) {
            endPos = getPosition() - steps;
        } else {
            endPos = getPosition() + steps;
        }

        if (endPos > 100 || endPos < 0) {
            return;
        }

        square = board.getBoardSquare(endPos);
        
        //DEBUGGING
        System.out.println(name+" moves to square: "+square.getNumber());
    }
    
    public void reset(Board board){
        reverse = turtle = lucky = false;
        square = board.getBoardSquare(1);
    }

    public int getPosition() {
        return square.getNumber();
    }

    //GetterSetters
    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
        //DEBUGGING
        System.out.println(name+" moves to square: "+square.getNumber());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        setIcon(assets.getResizedIcon(color, 30, 30));
    }

    public boolean isReverse() {
        return reverse;
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }

    public boolean isTurtle() {
        return turtle;
    }

    public void setTurtle(boolean turtle) {
        this.turtle = turtle;
    }

    public boolean isLucky() {
        return lucky;
    }

    public void setLucky(boolean lucky) {
        this.lucky = lucky;
    }

}
