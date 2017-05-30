/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakesandladders.v2.pkg0.Logic;

import javax.swing.JLabel;
import snakesandladders.v2.pkg0.Assets;
import snakesandladders.v2.pkg0.Logic.Squares.Square;

/**
 * The pawns used in the game. Has name, color, the square it's in and the
 * possible effects that can be applied to it
 *
 * @author Zac
 */
public class Player extends JLabel {

    //Fields
    private Square square;
    private String name, color;
    private boolean reverse, turtle, lucky;
    private Assets assets; //soc code

    //Constructors
    /**
     *Initializes the parameters given to their corresponding fields, sets all
     * effects as false
     * @param square
     * @param name
     * @param color
     */
    public Player(Square square, String name, String color) {
        this.square = square;
        this.name = name;
        this.color = color;
        reverse = turtle = lucky = false;
        assets = new Assets(); //soc code
    }

    //Methods
    /**
     *Method used for players movement.
     * Updates player's square based on the number of steps he has to take and
     * any effects he might have
     * 
     * Lucky effect transports the player to the last square if the player rolls
     * a 6
     * Turtle square halves the steps the player would normally take
     * Reverse square reverses the player's movement, e.g. if a player
     * is to take 5 steps he will move 5 steps backwards
     * A player cannot move to square 100 or 1 unless they make the take the
     * exact number of steps there
     * @param steps
     * @param board
     */
    public void advance(int steps, Board board) {

        int endPos;

        if (lucky) {
            lucky = false;
            if (steps == 6) {
                square = board.getBoardSquare(100);
                return;
            }
        }

        if (turtle) {
            steps = Math.max(steps / 2, 1);
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
//        System.out.println(name + " moves to square: " + square.getNumber());
    }

    /**
     *Sets all effects false and player's square to the first square
     * @param board
     */
    public void reset(Board board) {
        reverse = turtle = lucky = false;
        square = board.getBoardSquare(1);
    }

    /**
     *Returns the number of the player's square
     * @return
     */
    public int getPosition() {
        return square.getNumber();
    }

    //GetterSetters
    /**
     *
     * @return
     */
    public Square getSquare() {
        return square;
    }

    /**
     *
     * @param square
     */
    public void setSquare(Square square) {
        this.square = square;
        //DEBUGGING
//        System.out.println(name + " moves to square: " + square.getNumber());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getColor() {
        return color;
    }

    /**
     *
     * @param color
     */
    public void setColor(String color) {
        this.color = color;
        setIcon(assets.getResizedIcon(color, 30, 30));
    }

    /**
     *
     * @return
     */
    public boolean isReverse() {
        return reverse;
    }

    /**
     *
     * @param reverse
     */
    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }

    /**
     *
     * @return
     */
    public boolean isTurtle() {
        return turtle;
    }

    /**
     *
     * @param turtle
     */
    public void setTurtle(boolean turtle) {
        this.turtle = turtle;
    }

    /**
     *
     * @return
     */
    public boolean isLucky() {
        return lucky;
    }

    /**
     *
     * @param lucky
     */
    public void setLucky(boolean lucky) {
        this.lucky = lucky;
    }

}
