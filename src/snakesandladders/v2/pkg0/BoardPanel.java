package snakesandladders.v2.pkg0;

import snakesandladders.v2.pkg0.Logic.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import snakesandladders.v2.pkg0.Logic.Squares.CancelReverseSquare;
import snakesandladders.v2.pkg0.Logic.Squares.CancelTurtleSquare;
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
import snakesandladders.v2.pkg0.Logic.Squares.TurtleSquare;

public class BoardPanel extends JPanel {

    private JLabel label1 = new JLabel();
    private JLabel label2 = new JLabel();
    private Color myColor = Color.decode("#43B7BA");
    private ImageIcon player1;
    private ImageIcon player2;
    private Board board;
    private Assets assets;
    private Snake snake = new Snake();
    private LadderDrawingManualPanel ladder = new LadderDrawingManualPanel();
    private JLabel[] label = new JLabel[100];

    public BoardPanel(Board board) {
        this.board = board;
        assets = new Assets();
        this.setOpaque(true);
        setPreferredSize(new Dimension(600, 600));

        int[] temp = {81, 61, 41, 21, 1};
        int[] temp2 = {100, 80, 60, 40, 20};
        setLayout(new GridLayout(10, 10, 1, 1));

        for (int i = 0; i < 100; i++) {
            //change
            label[i] = (new JLabel());
            label[i].setLayout(new BoxLayout(label[i], BoxLayout.X_AXIS));
            label[i].setOpaque(true);
            label[i].setBackground(myColor);
            label[i].setText("" + (i + 1));
            label[i].setHorizontalAlignment(JLabel.RIGHT);
            label[i].setVerticalAlignment(JLabel.BOTTOM);
            label[i].revalidate();
        }
        for (int i = 0; i < 10; i++) { //add jlabels to jpanel in correct order

            int count1 = temp[(int) i / 2];
            int count2 = temp2[(int) i / 2];
            for (int j = 0; j < 10; j++) {
                if (i % 2 == 0) {
                    add(label[count2-- - 1]);
                    revalidate();
                } else {
                    add(label[count1++ - 1]);
                    revalidate();
                }
            }
        }
        this.revalidate();
    }

    public void setPlayerColors(String color, String color2) {
        player1 = assets.getResizedIcon(color, 30, 30);
        player2 = assets.getResizedIcon(color2, 30, 30);
    }

    public void updatePlayers(int i, int j) {
        label1.setIcon(player1);
        label[i - 1].add(label1);
        label2.setIcon(player2);
        label[j - 1].add(label2);
        repaint();
        revalidate();
    }

    public void updateSquares() {
        int i = 1;
        for (Square temp : board.getBoardSquares()) {

            if (temp == null) {
                continue;
            }
            if (temp instanceof ExplosionSquare) {
                label[i - 1].setIcon(assets.getResizedIcon("explosion", 35, 40));
            } else if (temp instanceof LoveSquare) {
                label[i - 1].setIcon(assets.getResizedIcon("love", 35, 40));
            } else if (temp instanceof ExchangePawnsSquare) {
                label[i - 1].setIcon(assets.getResizedIcon("exchange_pawns", 35, 40));
            } else if (temp instanceof RethrowDiceSquare) {
                label[i - 1].setIcon(assets.getResizedIcon("rethrow_dice", 35, 40));
            } else if (temp instanceof LuckySquare) {
                label[i - 1].setIcon(assets.getResizedIcon("clover4", 35, 40));
            } else if (temp instanceof GravityReversalSquare) {
                label[i - 1].setIcon(assets.getResizedIcon("gravity_reversal", 35, 40));
            } else if (temp instanceof ReverseSquare) {
                label[i - 1].setIcon(assets.getResizedIcon("reverse", 35, 40));
            } else if (temp instanceof TurtleSquare) {
                label[i - 1].setIcon(assets.getResizedIcon("turtle", 35, 40));
            } else if (temp instanceof CancelTurtleSquare) {
                label[i - 1].setIcon(assets.getResizedIcon("turtle_cancel", 35, 40));
            } else if (temp instanceof CancelReverseSquare) {
                label[i - 1].setIcon(assets.getResizedIcon("reverse_cancel", 35, 40));
            }
            label[i - 1].setHorizontalAlignment(JLabel.RIGHT);
            label[i - 1].setVerticalTextPosition(JLabel.BOTTOM);
            i++;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(getForeground());
        super.paintComponents(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        for (Square boardSquare : board.getBoardSquares()) {
            double width = label[1].getWidth() * 0.5;
            double height = label[1].getHeight() * 0.5;
            if (boardSquare instanceof SnakeSquare) {
                int dest_index = ((SnakeSquare) boardSquare).getDest().getNumber(); //cast boardSquare to snake, getDest returns object, get number the number of target dest
                int source_index = boardSquare.getNumber(); //same for source
                Point2D dest = label[dest_index - 1].getLocation(); //find the location on screen (Point2D) coords
                Point2D source = label[source_index - 1].getLocation(); //same for source
                dest.setLocation(dest.getX()+width, dest.getY()+height); //set their coords to be in the middle of the square
                source.setLocation(source.getX()+width, source.getY()+height); //->>-
                snake.setPoints(source, dest); //add to snake so it can draw
                snake.draw(g2);
            } else if (boardSquare instanceof LadderSquare) {
                int dest_index = ((LadderSquare) boardSquare).getDest().getNumber();
                int source_index = boardSquare.getNumber();
                Point2D dest = label[dest_index - 1].getLocation();
                Point2D source = label[source_index - 1].getLocation();
                dest.setLocation(dest.getX()+width, dest.getY()+height);
                source.setLocation(source.getX()+width, source.getY()+height); 
                ladder.drawLadderBetweenPoints(g2, source, dest);
            }
        }
        g2.dispose();
    }
}
