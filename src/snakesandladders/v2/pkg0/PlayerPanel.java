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
    private String name;
    private JLabel pawn, nameLabel, positionText;
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
        name = playerName;
        nameLabel = new JLabel(name);
        positionText = new JLabel("Position "+1);
        
        //PlayerPanel
        setLayout(new GridBagLayout());
        //setBorder(border);
        
        //pawn
        pawn.setPreferredSize(new Dimension(60, 60));
        //pawn.setBorder(border);
        
        //name
        nameLabel.setFont(new Font("Serif", Font.PLAIN, 22));
        nameLabel.setForeground(Color.BLUE);
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
//        gbc.anchor = GridBagConstraints.LINE_START;
        add(nameLabel, gbc);
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
        this.name = name;
        nameLabel.setText(name);
        repaint();
    }
    
    private void updateTextColor(String color) {
        
        switch (color) {
            case "pawn_blue": nameLabel.setForeground(Color.BLUE);
            break;
            case "pawn_red": nameLabel.setForeground(Color.RED);
            break;
            case "pawn_green": nameLabel.setForeground(Color.GREEN);
            break;
            case "pawn_magenta": nameLabel.setForeground(Color.MAGENTA);
            break;
            default: nameLabel.setForeground(Color.BLACK);
            break;
        }
        nameLabel.repaint();
        
    }
    
    public void boldenName() {
//        System.out.println("Boldening "+name);
//        reAdd();
        nameLabel.setText("<HTML><U>"+name+"</U></HTML>");
        nameLabel.repaint();
    }
    
    public void unBoldenName() {
//        System.out.println("Unboldening "+name);
//        reAdd();
        nameLabel.setText(name);
        nameLabel.repaint();
    }
    
    //GetterSetters

    public JLabel getNameLabelString() {
        return nameLabel;
    }

    public void setNameLabel(JLabel name) {
        this.nameLabel = name;
    }
    
    
}
