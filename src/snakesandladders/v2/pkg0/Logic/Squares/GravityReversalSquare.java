/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakesandladders.v2.pkg0.Logic.Squares;

import java.util.ArrayList;
import snakesandladders.v2.pkg0.Logic.Board;
import snakesandladders.v2.pkg0.Logic.Player;
import snakesandladders.v2.pkg0.SnakesAndLaddersV20;

/**
 *
 * @author Zac
 */
public class GravityReversalSquare extends Square {

    public GravityReversalSquare(int number) {
        super(number);
    }

    @Override
    public void applyEffect(Player player1, Player player2, Board board, SnakesAndLaddersV20 snl) {

        int oldNum, newNum;
        ArrayList<Square> noTouch = new ArrayList<Square>(); //Used to ensure ladders that have swapped to snakes don't get swapped to ladders again
        
        
        
        for (Square i : board.getBoardSquares()) {
            if (i instanceof SnakeSquare && !noTouch.contains(i)) {
                oldNum = i.getNumber();
                newNum = ((SnakeSquare) i).getDest().getNumber();
                
                board.setBoardSquare(oldNum, new DefaultSquare(oldNum, true)); //Replaces previous SnakeSquare with a DefaultSquare
                board.setBoardSquare(newNum, new LadderSquare(newNum, board.getBoardSquare(oldNum))); //Replace LadderSquare to-be with a LadderSquare
                noTouch.add(board.getBoardSquare(newNum)); //This is probably redundant
            } else if(i instanceof LadderSquare && !noTouch.contains(i)) {
                oldNum = i.getNumber();
                newNum = ((LadderSquare) i).getDest().getNumber();
                
                board.setBoardSquare(oldNum, new DefaultSquare(oldNum, true)); //Replaces previous LadderSquare with a DefaultSquare
                board.setBoardSquare(newNum, new SnakeSquare(newNum, board.getBoardSquare(oldNum))); //Replace SnakeSquare to-be with a LadderSquare
                noTouch.add(board.getBoardSquare(newNum));
            }
        }
        
        
//        player2.setAppliedEffects(false);
        
        snl.getHistory().append(player1.getName()+" stepped on a gravity reversal square");
        
    }

}
