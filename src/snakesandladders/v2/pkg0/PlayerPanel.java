/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakesandladders.v2.pkg0;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

/**
 *
 * @author Zac
 */
public class PlayerPanel extends JPanel {
    
    //Variables
    private GridBagConstraints gbc;
    private JLabel pawn, name, positionText;
    private ImageIcon pawnImg;
    private Border border;
    
    private Assets assets = new Assets();
    
    //Constructors
    public PlayerPanel(String playerName, String playerColor) {
        //setBackground(Color.decode("#e0e0e0"));
        
        //Init
        border = BorderFactory.createLineBorder(Color.BLACK);
        gbc = new GridBagConstraints();
        pawnImg = assets.getResizedIcon(playerColor, 60, 60);
        pawn = new JLabel("" , pawnImg, JLabel.CENTER);
        name = new JLabel(playerName);
        positionText = new JLabel("Position "+1);
        
        //PlayerPanel
        setLayout(new GridBagLayout());
        //setBorder(border);
        
        //pawn
        pawn.setPreferredSize(new Dimension(60, 60));
        //pawn.setBorder(border);
        
        //name
        name.setFont(new Font("Serif", Font.PLAIN, 22));
        name.setForeground(Color.BLUE);
        //name.setBorder(border);
        
        //position
        positionText.setFont(new Font("Serif", Font.PLAIN, 22));
        //position.setBorder(border);
        
        //Adds
        gbc.gridheight = 2;
        gbc.gridx = 0;
        add(pawn, gbc);
        gbc.gridheight = 1;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 0, 2, 0); //top left down right
        gbc.anchor = GridBagConstraints.LINE_START;
        add(name, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(positionText, gbc);
        
        
    }
    
    //Methods
    
    //Getter Setters
    public void updatePosition(int position) {
        positionText.setText("Position "+position);
        repaint();
    }
    
    public void updateColor(String color) {
        pawn.setIcon(assets.getResizedIcon(color, 60, 60));
        updateTextColor(color);
        repaint();
    }
    
    public void updateName(String name) {
        this.name.setText(name);
        repaint();
    }
    
    private void updateTextColor(String color) {
        
        switch (color) {
            case "pawn_blue": name.setForeground(Color.BLUE);
            break;
            case "pawn_red": name.setForeground(Color.RED);
            break;
            case "pawn_green": name.setForeground(Color.GREEN);
            break;
            case "pawn_magenta": name.setForeground(Color.MAGENTA);
            break;
            default: name.setForeground(Color.BLACK);
            break;
        }
        name.repaint();
        
    }
    
    public void boldenName() {
        name.setFont(new Font("Serif", Font.BOLD, 22));
        name.setText(name.getText());
        name.repaint();
    }
    
    public void unBoldenName() {
        name.setFont(new Font("Serif", Font.PLAIN, 22));
        name.setText(name.getText());
        name.repaint();
    }
    
    //GetterSetters

    public JLabel getNameString() {
        return name;
    }

    public void setName(JLabel name) {
        this.name = name;
    }
    
    
}
