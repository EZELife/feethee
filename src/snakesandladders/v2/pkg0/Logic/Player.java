/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakesandladders.v2.pkg0.Logic;

import snakesandladders.v2.pkg0.Logic.Squares.Square;

/**
 *
 * @author Zac
 */
public class Player {

    //Fields
    private Square square;
    private String name, color;
    private boolean reverse, turtle, lucky, appliedEffects;

    //Constructors
    public Player(Square square, String name, String color) {
        this.square = square;
        this.name = name;
        this.color = color;
        reverse = turtle = lucky = false;
        appliedEffects = true;
    }

    //Methods
    public void advance(int steps, Board board) {

        int endPos;
        
        if(lucky && steps == 6) {
            lucky = false;
            square = board.getBoardSquare(100);
            appliedEffects = false;
            return;
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
        appliedEffects = false;
        
        //DEBUGGING
        System.out.println(name+" moves to square: "+square.getNumber());
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
        appliedEffects = false;
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

    public boolean isAppliedEffects() {
        return appliedEffects;
    }

    public void setAppliedEffects(boolean appliedEffects) {
        this.appliedEffects = appliedEffects;
    }

}
