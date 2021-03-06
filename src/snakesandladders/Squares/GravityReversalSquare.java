/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakesandladders.Squares;

import java.util.ArrayList;
import snakesandladders.main.Board;
import snakesandladders.main.Player;
import snakesandladders.main.SnakesAndLaddersV20;
import snakesandladders.main.Assets;
/**
 *
 * @author Zac
 */
public class GravityReversalSquare extends Square {

    /**
     *{@inheritDoc}
     * @param number
     */
    Assets assets = new Assets();
    public GravityReversalSquare(int number) {
        super(number);
        setIcon(assets.getResizedIcon("gravity_reversal", 35, 40));
    }

    /**
     *Reverses ladders and snakes.
     * Meaning that every ladders top and bottom become the head and tail of a
     * snake, and every snakes head and tail become the top and bottom of a
     * ladder
     * @param player1
     * @param player2
     * @param board
     * @param snl
     */
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
                
                board.getBoardSquare(oldNum).repaint();
                board.getBoardSquare(oldNum).revalidate();
            } else if (i instanceof LadderSquare && !noTouch.contains(i)) {
                oldNum = i.getNumber();
                newNum = ((LadderSquare) i).getDest().getNumber();

                board.setBoardSquare(oldNum, new DefaultSquare(oldNum, true)); //Replaces previous LadderSquare with a DefaultSquare
                board.setBoardSquare(newNum, new SnakeSquare(newNum, board.getBoardSquare(oldNum))); //Replace SnakeSquare to-be with a LadderSquare
                noTouch.add(board.getBoardSquare(newNum));
                board.getBoardSquare(oldNum).repaint();
                board.getBoardSquare(oldNum).revalidate();
            }
        }

//        player2.setAppliedEffects(false);
        if(player1 != null){
            snl.getHistory().append(player1.getName() + " stepped on a gravity reversal square");
            snl.timesGravityHasChanged++;
            System.out.println(""+snl.timesGravityHasChanged);
        } else {
            System.out.println("RESETGRAVITY");
        }
        int[] temp = {81, 61, 41, 21, 1};
        int[] temp2 = {100, 80, 60, 40, 20};
        board.removeAll();

        for (int i = 0; i < 10; i++) { //add jlabels to jpanel in correct order

            int count1 = temp[(int) i / 2];
            int count2 = temp2[(int) i / 2];
            for (int j = 0; j < 10; j++) {
                if (i % 2 == 0) {
                    board.add(board.getBoardSquare(count2--));
                    revalidate();
                } else {
                    board.add(board.getBoardSquare(count1++));
                    revalidate();
                }
            }
        }
       this.revalidate();
       
    }

}
