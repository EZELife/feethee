/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakesandladders.v2.pkg0;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *The timer of the game
 * @author Zac
 */
public class SnakeTimer extends JTextField {
    
    //Variables
    private Timer snakeTimer;
    private String timerString;
    private int sec =0, min =0, hour=0;
    
    //Constructors

    /**
     *Builds and starts a timer
     */
    public SnakeTimer() {
        setBackground(Color.decode("#e0e0e0"));
        
        //Init
        timerString = "00:00:00";
        snakeTimer = new Timer(1000, (ActionEvent e) -> {
            sec++;
            
            if(sec >= 60) {
                min++;
                sec = 0;
            }
            
            if(min >= 60) {
                hour++;
                min = 0;
            }
            
            timerString = String.format("%02d:%02d:%02d", hour, min, sec);
            
            setText(timerString);
            repaint();
        });
        
        //SnakeTimer
        setEditable(false);
        setHorizontalAlignment(SwingConstants.CENTER);
        setBorder(new EmptyBorder(0, 0, 0, 0));
        setFont(new Font("Serif", Font.PLAIN, 20));
        //setForeground(Color.WHITE);
        setText(timerString);
        
        snakeTimer.start();
    }
    
    //Methods

    /**
     *
     */
    public void reset() {
        timerString = "00:00:00";
        repaint();
        sec=min=hour=0;
        repaint();
    }
    
}
