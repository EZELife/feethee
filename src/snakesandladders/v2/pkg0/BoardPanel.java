package snakesandladders.v2.pkg0;

import snakesandladders.v2.pkg0.Logic.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import javax.swing.JPanel;
import snakesandladders.v2.pkg0.Logic.Squares.LadderSquare;
import snakesandladders.v2.pkg0.Logic.Squares.SnakeSquare;
import snakesandladders.v2.pkg0.Logic.Squares.Square;

public class BoardPanel extends JPanel {

    
    private Snake snake;
    private LadderDrawingManualPanel ladder;
    private Board board;

    public BoardPanel(Board board) {
        setOpaque(false);
        snake = new Snake();
        ladder = new LadderDrawingManualPanel();
        this.board = board;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        for (Square boardSquare : board.getBoardSquares()) {
            double width = board.getSquare()[1].getWidth() * 0.5;
            double height = board.getSquare()[1].getHeight() * 0.5;
            if (boardSquare instanceof SnakeSquare) {
                int dest_index = ((SnakeSquare) boardSquare).getDest().getNumber(); //cast boardSquare to snake, getDest returns object, get number the number of target dest
                int source_index = boardSquare.getNumber(); //same for source
                Point2D dest = board.getSquare()[dest_index - 1].getLocation(); //find the location on screen (Point2D) coords
                Point2D source = board.getSquare()[source_index - 1].getLocation(); //same for source
                dest.setLocation(dest.getX() + width, dest.getY() + height); //set their coords to be in the middle of the square
                source.setLocation(source.getX() + width, source.getY() + height); //->>-
                snake.setPoints(source, dest); //add to snake so it can draw
                snake.draw(g2);
            } else if (boardSquare instanceof LadderSquare) {
                int dest_index = ((LadderSquare) boardSquare).getDest().getNumber();
                int source_index = boardSquare.getNumber();
                Point2D dest = board.getSquare()[dest_index - 1].getLocation();
                Point2D source = board.getSquare()[source_index - 1].getLocation();
                dest.setLocation(dest.getX() + width, dest.getY() + height);
                source.setLocation(source.getX() + width, source.getY() + height);
                ladder.drawLadderBetweenPoints(g2, source, dest);
            }
        }
        g2.dispose();
    }
}
