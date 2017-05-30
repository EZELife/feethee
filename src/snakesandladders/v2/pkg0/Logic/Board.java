/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakesandladders.v2.pkg0.Logic;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Random;
import javax.swing.JPanel;
import snakesandladders.v2.pkg0.Assets;
import snakesandladders.v2.pkg0.Logic.Squares.CancelReverseSquare;
import snakesandladders.v2.pkg0.Logic.Squares.CancelTurtleSquare;
import snakesandladders.v2.pkg0.Logic.Squares.DefaultSquare;
import snakesandladders.v2.pkg0.Logic.Squares.EndSquare;
import snakesandladders.v2.pkg0.Logic.Squares.ExchangePawnsSquare;
import snakesandladders.v2.pkg0.Logic.Squares.ExplosionSquare;
import snakesandladders.v2.pkg0.Logic.Squares.GravityReversalSquare;
import snakesandladders.v2.pkg0.Logic.Squares.LadderSquare;
import snakesandladders.v2.pkg0.Logic.Squares.LoveSquare;
import snakesandladders.v2.pkg0.Logic.Squares.LuckySquare;
import snakesandladders.v2.pkg0.Logic.Squares.RethrowDiceSquare;
import snakesandladders.v2.pkg0.Logic.Squares.ReverseSquare;
import snakesandladders.v2.pkg0.Logic.Squares.SnakeSquare;
import snakesandladders.v2.pkg0.Logic.Squares.Square;
import snakesandladders.v2.pkg0.Logic.Squares.StartSquare;
import snakesandladders.v2.pkg0.Logic.Squares.TurtleSquare;
import snakesandladders.v2.pkg0.SnakesAndLaddersV20;

/**
 * The playing board. Includes 100 squares of varying types
 *
 * @author Zac
 */
public class Board extends JPanel implements Cloneable {

    private enum SpecialSquareType {
        SNAKE,
        LADDER,
        EXPLOSION,
        LOVE,
        EXCHANGE_PAWNS,
        RETHROW_DICE,
        LUCKY,
        GRAVITY_REVERSAL,
        REVERSE,
        CANCEL_REVERSE,
        TURTLE,
        CANCEL_TURTLE
    }

    //Fields
    private final int minTravelDistance = 10;
    private final int maxTravelDistance = 50;
    private final int specialSquareDensity = 2;
    private boolean rebuild; //Used to rebuild Board in case of failure
    private Random random;
    private Square[] boardSquares;
    private Player player1, player2;
    private Assets assets;
    private int gridPosCounter;
    // private Square[] square = new Square[100];

    //Enum
    /**
     *Used to determine what kind of squares will be used in building the board
     */
    public static enum Difficulty {

        /**
         *Easy difficulty
         */
        EASY,
        /**
         *Normal difficulty
         */
        NORMAL,
        /**
         *Hard difficulty
         */
        HARD,
        /**
         *Used for debugging
         */
        DEBUG
    }

    //Constructors
    /**
     *Creates 101 squares(square[0] is null), calls buildsBoard to build the 
     * board
     * @param difficulty
     */
    public Board(Difficulty difficulty) {
        this.setOpaque(false); //soc code
        //Init
        assets = new Assets();
        random = new Random();
        boardSquares = new Square[101];
        boardSquares[0] = null;

        //Logic
        //buildBoard(difficulty);
        //DEBUGGING
        buildBoard(difficulty);
//        printBoard();
        //buildBoard2(100);
        setPreferredSize(new Dimension(600, 600));

        int[] temp = {81, 61, 41, 21, 1};
        int[] temp2 = {100, 80, 60, 40, 20};
        setLayout(new GridLayout(10, 10, 1, 1));

        for (int i = 0; i < 10; i++) { //add jlabels to jpanel in correct order
            gridPosCounter = 0;
            int count1 = temp[(int) i / 2];
            int count2 = temp2[(int) i / 2];
            for (int j = 0; j < 10; j++) {
                if (i % 2 == 0) {
                    add(boardSquares[count2--]);
                    //boardSquares[count2--].setGridPos(gridPosCounter++);
                    revalidate();
                } else {
                    add(boardSquares[count1++]);
                    //boardSquares[count1++].setGridPos(gridPosCounter++);
                    revalidate();
                }
//                System.out.println("gridPosCounter = " + gridPosCounter);
            }
        }
        this.revalidate();
    }

    /**
     *
     */
    public void updateSquares() {
        int i = 1;
        for (Square temp : boardSquares) { //reads the square list and adds propper icons

            if (temp == null) {
                continue;
            }
            if (temp instanceof ExplosionSquare) {
                boardSquares[i].setIcon(assets.getResizedIcon("explosion", 35, 40));
            } else if (temp instanceof LoveSquare) {
                boardSquares[i].setIcon(assets.getResizedIcon("love", 35, 40));
            } else if (temp instanceof ExchangePawnsSquare) {
                boardSquares[i].setIcon(assets.getResizedIcon("exchange_pawns", 35, 40));
            } else if (temp instanceof RethrowDiceSquare) {
                boardSquares[i].setIcon(assets.getResizedIcon("rethrow_dice", 35, 40));
            } else if (temp instanceof LuckySquare) {
                boardSquares[i].setIcon(assets.getResizedIcon("clover4", 35, 40));
            } else if (temp instanceof GravityReversalSquare) {
                boardSquares[i].setIcon(assets.getResizedIcon("gravity_reversal", 35, 40));
            } else if (temp instanceof ReverseSquare) {
                boardSquares[i].setIcon(assets.getResizedIcon("reverse", 35, 40));
            } else if (temp instanceof TurtleSquare) {
                boardSquares[i].setIcon(assets.getResizedIcon("turtle", 35, 40));
            } else if (temp instanceof CancelTurtleSquare) {
                boardSquares[i].setIcon(assets.getResizedIcon("turtle_cancel", 35, 40));
            } else if (temp instanceof CancelReverseSquare) {
                boardSquares[i].setIcon(assets.getResizedIcon("reverse_cancel", 35, 40));
            }
            i++;
        }
    }

    /**
     *
     * @param i
     * @param j
     */
    public void updatePlayers(int i, int j) { //can be made to increase size based on label[] width/height
        boardSquares[i].add(player1);
        boardSquares[j].add(player2);
        repaint();
    }

    /**
     *
     * @param player1
     * @param player2
     */
    public void setPlayers(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    //Methods
    /**
     * Sets start, end square and initializes all other squares as default
     */
    private void initializeBoard() {
        for (int i = 1; i <= 100; i++) {
            boardSquares[i] = new DefaultSquare(i);
        }
        boardSquares[1] = new StartSquare();
        boardSquares[100] = new EndSquare();
    }
    
    /**
     * Builds board depending on difficulty given
     * Rebuilds board if rebuild is true
     * @param difficulty 
     */
    private void buildBoard(Difficulty difficulty) {

        rebuild = false;

        initializeBoard();
        switch (difficulty) {
            case EASY:
                buildSpecialSquare(SpecialSquareType.SNAKE, 3);
                buildSpecialSquare(SpecialSquareType.LADDER, 5);
                buildSpecialSquare(SpecialSquareType.RETHROW_DICE, 2);
                buildSpecialSquare(SpecialSquareType.LUCKY, 2);
                buildSpecialSquare(SpecialSquareType.GRAVITY_REVERSAL, 1);
                break;
            case NORMAL:
                buildSpecialSquare(SpecialSquareType.SNAKE, 4);
                buildSpecialSquare(SpecialSquareType.LADDER, 4);
                buildSpecialSquare(SpecialSquareType.LOVE, 1);
                buildSpecialSquare(SpecialSquareType.EXCHANGE_PAWNS, 1);
                buildSpecialSquare(SpecialSquareType.RETHROW_DICE, 1);
                buildSpecialSquare(SpecialSquareType.LUCKY, 1);
                buildSpecialSquare(SpecialSquareType.GRAVITY_REVERSAL, 1);
                buildSpecialSquare(SpecialSquareType.TURTLE, 1);
                buildSpecialSquare(SpecialSquareType.CANCEL_TURTLE, 1);
                break;
            case HARD:
                buildSpecialSquare(SpecialSquareType.SNAKE, 5);
                buildSpecialSquare(SpecialSquareType.LADDER, 2);
                buildSpecialSquare(SpecialSquareType.EXPLOSION, 1);
                buildSpecialSquare(SpecialSquareType.LOVE, 1);
                buildSpecialSquare(SpecialSquareType.EXCHANGE_PAWNS, 1);
                buildSpecialSquare(SpecialSquareType.GRAVITY_REVERSAL, 1);
                buildSpecialSquare(SpecialSquareType.REVERSE, 1);
                buildSpecialSquare(SpecialSquareType.CANCEL_REVERSE, 1);
                buildSpecialSquare(SpecialSquareType.TURTLE, 1);
                buildSpecialSquare(SpecialSquareType.CANCEL_TURTLE, 1);
                break;
            case DEBUG:
                for (int i = 2; i <= 7; i++) {
                    boardSquares[i] = new GravityReversalSquare(i);
                }
                buildSpecialSquare(SpecialSquareType.SNAKE, 8);
                buildSpecialSquare(SpecialSquareType.LADDER, 8);
//                buildSpecialSquare(SpecialSquareType.EXPLOSION, 1);
//                buildSpecialSquare(SpecialSquareType.LOVE, 1);
//                buildSpecialSquare(SpecialSquareType.EXCHANGE_PAWNS, 1);
//                buildSpecialSquare(SpecialSquareType.RETHROW_DICE, 1);
//                buildSpecialSquare(SpecialSquareType.LUCKY, 1);
//                buildSpecialSquare(SpecialSquareType.GRAVITY_REVERSAL, 1);
//                buildSpecialSquare(SpecialSquareType.REVERSE, 1);
//                buildSpecialSquare(SpecialSquareType.CANCEL_REVERSE, 1);
//                buildSpecialSquare(SpecialSquareType.TURTLE, 1);
//                buildSpecialSquare(SpecialSquareType.CANCEL_TURTLE, 1);
                break;

        }

        //method calls itself until a successful board is built
        if (rebuild) {
            //System.out.println("Rebuilding"); //DEBUGGING
            buildBoard(difficulty);
        }

    }
    
    /**
     * Builds and places special squares in free spots
     * @param type
     * @param number 
     */
    private void buildSpecialSquare(SpecialSquareType type, int number) {

        int dest, counter, randomNum, ceiling, floor;

        //counter stops this method in case of infinite loop
        //ceiling/floor is used to ensure cancel squares are placed appropriately
        counter = ceiling = 0;
        floor = 100;

        for (int i = 0; i < number; i++) {

            do {
                randomNum = random.nextInt(100) + 1;
            } while (!isFree(randomNum));

            switch (type) {

                case SNAKE:
                    dest = random.nextInt(100) + 1;
                    while (!isFree(randomNum) || !isFree(dest) || randomNum - dest < minTravelDistance || randomNum - dest > maxTravelDistance) {
                        randomNum = random.nextInt(100) + 1;
                        dest = random.nextInt(100) + 1;
                    }
                    boardSquares[randomNum] = new SnakeSquare(randomNum, boardSquares[dest]);
                    ((DefaultSquare) boardSquares[dest]).setOccupied(true);

                    //DEBUGGING
                    //System.out.println("Tried to make snake : SNAKE @ " + randomNum + " | " + boardSquares[randomNum].getNumber() + " - " + ((SnakeSquare) boardSquares[randomNum]).getDest().getNumber());
                    break;
                case LADDER:
                    dest = random.nextInt(100) + 1;
                    while (!isFree(randomNum) || !isFree(dest) || dest - randomNum < minTravelDistance || dest - randomNum > maxTravelDistance) {
                        randomNum = random.nextInt(100) + 1;
                        dest = random.nextInt(100) + 1;
                    }
                    boardSquares[randomNum] = new LadderSquare(randomNum, boardSquares[dest]);
                    ((DefaultSquare) boardSquares[dest]).setOccupied(true);

                    //DEBUGGING
                    //System.out.println("Tried to make ladder : LADDER @ " + randomNum + " | " + boardSquares[randomNum].getNumber() + " - " + ((LadderSquare) boardSquares[randomNum]).getDest().getNumber());
                    break;
                case EXPLOSION:
                    boardSquares[randomNum] = new ExplosionSquare(randomNum);
                    break;
                case LOVE:
                    boardSquares[randomNum] = new LoveSquare(randomNum);
                    break;
                case EXCHANGE_PAWNS:
                    boardSquares[randomNum] = new ExchangePawnsSquare(randomNum);
                    break;
                case RETHROW_DICE:
                    boardSquares[randomNum] = new RethrowDiceSquare(randomNum);
                    break;
                case LUCKY:
                    boardSquares[randomNum] = new LuckySquare(randomNum);
                    break;
                case GRAVITY_REVERSAL:
                    boardSquares[randomNum] = new GravityReversalSquare(randomNum);
                    break;
                case REVERSE:
                    boardSquares[randomNum] = new ReverseSquare(randomNum);
                    break;
                case CANCEL_REVERSE://ReverseSquare needs to be on the board before this is called IMPORTANT
                    for (Square temp : boardSquares) {
                        if (temp instanceof ReverseSquare) {
                            ceiling = temp.getNumber();
                            break;
                        }
                    }
                    while (!isFree(randomNum) || randomNum >= ceiling) {
                        counter++;
                        randomNum = random.nextInt(Math.max(ceiling - 1, 1)) + 1;
                        if (counter >= 1337) {
                            rebuild = true;
                            return; //In case of infinite loop returns for the method to be re-called
                        }
                    }
                    boardSquares[randomNum] = new CancelReverseSquare(randomNum);
                    break;
                case TURTLE:
                    boardSquares[randomNum] = new TurtleSquare(randomNum);
                    break;
                case CANCEL_TURTLE://TurtleSquare needs to be on the board before this is called IMPORTANT
                    for (Square temp : boardSquares) {
                        if (temp instanceof TurtleSquare) {
                            floor = temp.getNumber();
                            break;
                        }
                    }
                    while (!isFree(randomNum) || randomNum <= floor) {
                        counter++;
                        randomNum = random.nextInt(100) + 1;
                        if (counter >= 1337) {
                            rebuild = true;
                            return; //In case of infinite loop returns for the method to be re-called
                        }
                    }
                    boardSquares[randomNum] = new CancelTurtleSquare(randomNum);
                    break;
                default:
                    return;
            }
        }

    }
    
    /**
     * Checks and returns true if a special square can be built on square given
     * @param number
     * @return 
     */
    private boolean isFree(int number) {

        if (boardSquares[number] instanceof DefaultSquare && !((DefaultSquare) boardSquares[number]).isOccupied()) {
            return true;
        }
        return false;

    }

    /**
     *Resets board
     * @param snl
     */
    public void reset(SnakesAndLaddersV20 snl) {
        if (snl.timesGravityHasChanged % 2 != 0) {
            Square temp = new GravityReversalSquare(1000);
            temp.applyEffect(null, null, this, snl);
        }
        snl.timesGravityHasChanged = 0;
    }

    //GetterSetters
    /**
     *Returns boardSquares
     * @return
     */
    public Square[] getBoardSquares() {
        return boardSquares;
    }

    /**
     *
     * @param boardSquares
     */
    public void setBoardSquares(Square[] boardSquares) {
        this.boardSquares = boardSquares;
    }

    /**
     *Returns square specified from boardSquares
     * @param num
     * @return
     */
    public Square getBoardSquare(int num) {
        return boardSquares[num];
    }

    /**
     *
     * @param num
     * @param square
     */
    public void setBoardSquare(int num, Square square) {
        boardSquares[num] = square;
        repaint();
        revalidate();
    }

//    //DEBUGGING
//    /**
//     *
//     */
//    public void printBoard() {
//        for (Square temp : boardSquares) {
//            if (temp == null) {
//                continue;
//            }
//
//            if (temp instanceof SnakeSquare) {
//                System.out.println(temp.getNumber() + ": Snake | " + temp.getNumber() + " - " + ((SnakeSquare) temp).getDest().getNumber());
//            } else if (temp instanceof LadderSquare) {
//                System.out.println(temp.getNumber() + ": Ladder | " + temp.getNumber() + " - " + ((LadderSquare) temp).getDest().getNumber());
//            } else if (temp instanceof ExplosionSquare) {
//                System.out.println(temp.getNumber() + ": Explosion");
//            } else if (temp instanceof LoveSquare) {
//                System.out.println(temp.getNumber() + ": Love");
//            } else if (temp instanceof ExchangePawnsSquare) {
//                System.out.println(temp.getNumber() + ": ExchangePawns");
//            } else if (temp instanceof RethrowDiceSquare) {
//                System.out.println(temp.getNumber() + ": RethrowDice");
//            } else if (temp instanceof LuckySquare) {
//                System.out.println(temp.getNumber() + ": Lucky");
//            } else if (temp instanceof GravityReversalSquare) {
//                System.out.println(temp.getNumber() + ": GRAVREVERSE");
//            } else if (temp instanceof ReverseSquare) {
//                System.out.println(temp.getNumber() + ": Reverse");
//            } else if (temp instanceof TurtleSquare) {
//                System.out.println(temp.getNumber() + ": Turtle");
//            } else if (temp instanceof CancelTurtleSquare) {
//                System.out.println(temp.getNumber() + ": CancelTurtle");
//            } else if (temp instanceof CancelReverseSquare) {
//                System.out.println(temp.getNumber() + ": CancelReverse");
//            } else if (temp instanceof StartSquare) {
//                System.out.println(temp.getNumber() + ": StartSquare");
//            } else if (temp instanceof EndSquare) {
//                System.out.println(temp.getNumber() + ": EndSquare");
//            }
//        }
//    }
//
//    /**
//     *
//     */
//    public void printBoard2() {
//
//        int sn, la;
//        sn = la = 0;
//
//        for (Square temp : boardSquares) {
//            if (temp == null) {
//                continue;
//            }
//
//            if (temp instanceof SnakeSquare) {
//                sn++;
//            } else if (temp instanceof LadderSquare) {
//                la++;
//            }
//        }
//        System.out.println("SNAKES\t" + sn + "\tLADDERS\t" + la);
//    }
//
//    private void buildBoard2(int x) {
//        for (int i = 0; i < x; i++) {
//            buildBoard(Difficulty.DEBUG);
//            printBoard2();
//        }
//    }

//    public static void main(String[] args) {
//        Board board = new Board(Difficulty.DEBUG, player1, player2);
//    }
}
