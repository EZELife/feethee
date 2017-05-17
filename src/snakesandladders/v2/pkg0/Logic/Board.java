/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakesandladders.v2.pkg0.Logic;

import java.util.Random;
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
import snakesandladders.v2.pkg0.Logic.Squares.OccupiedSquare;
import snakesandladders.v2.pkg0.Logic.Squares.RethrowDiceSquare;
import snakesandladders.v2.pkg0.Logic.Squares.ReverseSquare;
import snakesandladders.v2.pkg0.Logic.Squares.SnakeSquare;
import snakesandladders.v2.pkg0.Logic.Squares.Square;
import snakesandladders.v2.pkg0.Logic.Squares.StartSquare;
import snakesandladders.v2.pkg0.Logic.Squares.TurtleSquare;

/**
 *
 * @author Zac
 */
public class Board implements Cloneable {

    //Allows Cloning of the class for resetting the game
    @Override
    public Board clone() {
        try {
            final Board result = (Board) super.clone();
            result.boardSquares = boardSquares.clone();
            return result;
        } catch (final CloneNotSupportedException ex) {
            throw new AssertionError();
        }
    }

    //Enum
    public enum Difficulty {
        EASY, NORMAL, HARD, DEBUG
    }

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

    //Constructors
    public Board(Difficulty difficulty) {

        //Init
        random = new Random();
        boardSquares = new Square[101];
        boardSquares[0] = null;

        //Logic
        buildBoard(difficulty);
        
        //DEBUGGING
        buildBoard(Difficulty.DEBUG);
        printBoard();
        //buildBoard2(100);
    }

    //Methods
    private void initializeBoard() {
        for (int i = 1; i <= 100; i++) {
            boardSquares[i] = new DefaultSquare(i);
        }
        boardSquares[1] = new StartSquare();
        boardSquares[100] = new EndSquare();
    }

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
                buildSpecialSquare(SpecialSquareType.SNAKE, 4);
                buildSpecialSquare(SpecialSquareType.LADDER, 4);
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
                    boardSquares[dest] = new OccupiedSquare(dest);

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
                    boardSquares[dest] = new OccupiedSquare(dest);

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

    private boolean isFree(int number) {
        
        //This code doesn't work 100% reliably not sure why <BUG>
        for (int i = Math.max(number - specialSquareDensity, 2);
                i <= Math.min(number + specialSquareDensity, 99); i++) {
            if (!(boardSquares[i] instanceof DefaultSquare)) {
                return false;
            }
        }

        if (boardSquares[number] instanceof DefaultSquare) {
            return true;
        }

        return false;

    }

    //GetterSetters
    public Square[] getBoardSquares() {
        return boardSquares;
    }

    public void setBoardSquares(Square[] boardSquares) {
        this.boardSquares = boardSquares;
    }

    public Square getBoardSquare(int num) {
        return boardSquares[num];
    }

    public void setBoardSquare(int num, Square square) {
        boardSquares[num] = square;
    }

    //DEBUGGING
    public void printBoard() {
        for (Square temp : boardSquares) {
            if (temp == null) {
                continue;
            }

            if (temp instanceof SnakeSquare) {
                System.out.println(temp.getNumber() + ": Snake | " + temp.getNumber() + " - " + ((SnakeSquare) temp).getDest().getNumber());
            } else if (temp instanceof LadderSquare) {
                System.out.println(temp.getNumber() + ": Ladder | " + temp.getNumber() + " - " + ((LadderSquare) temp).getDest().getNumber());
            } else if (temp instanceof ExplosionSquare) {
                System.out.println(temp.getNumber() + ": Explosion");
            } else if (temp instanceof LoveSquare) {
                System.out.println(temp.getNumber() + ": Love");
            } else if (temp instanceof ExchangePawnsSquare) {
                System.out.println(temp.getNumber() + ": ExchangePawns");
            } else if (temp instanceof RethrowDiceSquare) {
                System.out.println(temp.getNumber() + ": RethrowDice");
            } else if (temp instanceof LuckySquare) {
                System.out.println(temp.getNumber() + ": Lucky");
            } else if (temp instanceof GravityReversalSquare) {
                System.out.println(temp.getNumber() + ": GRAVREVERSE");
            } else if (temp instanceof ReverseSquare) {
                System.out.println(temp.getNumber() + ": Reverse");
            } else if (temp instanceof TurtleSquare) {
                System.out.println(temp.getNumber() + ": Turtle");
            } else if (temp instanceof CancelTurtleSquare) {
                System.out.println(temp.getNumber() + ": CancelTurtle");
            } else if (temp instanceof CancelReverseSquare) {
                System.out.println(temp.getNumber() + ": CancelReverse");
            } else if (temp instanceof StartSquare) {
                System.out.println(temp.getNumber() + ": StartSquare");
            } else if (temp instanceof EndSquare) {
                System.out.println(temp.getNumber() + ": EndSquare");
            }
        }
    }

    public void printBoard2() {

        int sn, la;
        sn = la = 0;

        for (Square temp : boardSquares) {
            if (temp == null) {
                continue;
            }

            if (temp instanceof SnakeSquare) {
                sn++;
            } else if (temp instanceof LadderSquare) {
                la++;
            }
        }
        System.out.println("SNAKES\t" + sn + "\tLADDERS\t" + la);
    }

    private void buildBoard2(int x) {
        for (int i = 0; i < x; i++) {
            buildBoard(Difficulty.DEBUG);
            printBoard2();
        }
    }

    public static void main(String[] args) {
        Board board = new Board(Difficulty.DEBUG);
    }

}
