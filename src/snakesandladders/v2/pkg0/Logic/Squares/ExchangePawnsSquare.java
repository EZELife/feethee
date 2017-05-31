/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakesandladders.v2.pkg0.Logic.Squares;
import snakesandladders.v2.pkg0.Assets;
import snakesandladders.v2.pkg0.Logic.Board;
import snakesandladders.v2.pkg0.Logic.Player;
import snakesandladders.v2.pkg0.SnakesAndLaddersV20;

/**
 *
 * @author Zac
 */
public class ExchangePawnsSquare extends Square {

    /**
     *{@inheritDoc}
     * @param number
     */
    Assets assets = new Assets();
    public ExchangePawnsSquare(int number) {
        super(number);
        setIcon(assets.getResizedIcon("exchange_pawns", 35, 40));
    }

    /**
     *Swaps the pawns position
     * @param player1
     * @param player2
     * @param board
     * @param snl
     */
    @Override
    public void applyEffect(Player player1, Player player2, Board board, SnakesAndLaddersV20 snl) {
        Square temp = player1.getSquare();
        player1.setSquare(player2.getSquare());
        player2.setSquare(temp);
        snl.getHistory().append(player1.getName()+" stepped on an exchange pawns square");
    }

}
