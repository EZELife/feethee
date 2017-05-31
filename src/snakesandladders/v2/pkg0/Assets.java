/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakesandladders.v2.pkg0;

import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *Used for loading and resizing images used within the game
 * @author Zac
 */
public class Assets {

    //Objects
    private ArrayList<Assets> images;

    /**
     *
     */
    public String iconPath;

    /**
     *
     */
    public String name;

    //Constructors

    /**
     *Loads all image paths and the strings that will need to be called to
     * load them from another class
     */
    public Assets() {

        images = new ArrayList<>();
        images.add(new Assets("/assets/icons/clover4_icon.png", "clover4"));
        images.add(new Assets("/assets/icons/dice1_icon.png", "dice1"));
        images.add(new Assets("/assets/icons/dice2_icon.png", "dice2"));
        images.add(new Assets("/assets/icons/dice3_icon.png", "dice3"));
        images.add(new Assets("/assets/icons/dice4_icon.png", "dice4"));
        images.add(new Assets("/assets/icons/dice5_icon.png", "dice5"));
        images.add(new Assets("/assets/icons/dice6_icon.png", "dice6"));
        images.add(new Assets("/assets/icons/exchange_pawns_icon.png", "exchange_pawns"));
        images.add(new Assets("/assets/icons/explosion_icon.png", "explosion"));
        images.add(new Assets("/assets/icons/gravity_reversal_icon.png", "gravity_reversal"));
        images.add(new Assets("/assets/icons/love_icon.png", "love"));
        images.add(new Assets("/assets/icons/pawn_blue_icon.png", "pawn_blue"));
        images.add(new Assets("/assets/icons/pawn_green_icon.png", "pawn_green"));
        images.add(new Assets("/assets/icons/pawn_magenta_icon.png", "pawn_magenta"));
        images.add(new Assets("/assets/icons/pawn_red_icon - Copy.png", "pawn_red"));
        images.add(new Assets("/assets/icons/rethrow_dice_icon.png", "rethrow_dice"));
        images.add(new Assets("/assets/icons/reverse_cancel_icon.png", "reverse_cancel"));
        images.add(new Assets("/assets/icons/reverse_icon.png", "reverse"));
        images.add(new Assets("/assets/icons/turtle_cancel_icon.png", "turtle_cancel"));
        images.add(new Assets("/assets/icons/turtle_icon.png", "turtle"));

    }

    /**
     *Loads an image path and a string that will need to be called to
     * load the image from another class
     * @param iconPath
     * @param name
     */
    public Assets(String iconPath, String name) {

        this.iconPath = iconPath;
        this.name = name;

    }
    
    //Methods

    /**
     *Resizes and returns an icon
     * @param name
     * @param width
     * @param height
     * @return
     */
    public ImageIcon getResizedIcon(String name, int width, int height) {
        
        ImageIcon resizedImage = null;
//        java.net.URL tempURL;
        
        for (int i = 0; i < images.size(); i++) {
            if(images.get(i).name.equals(name)) {
                resizedImage = new ImageIcon(getClass().getResource(images.get(i).iconPath));
                break;
            }
        }
        
        if(resizedImage == null)
            System.out.println("den yparxei h eikona epistrefw null ;)");
        else
            resizedImage.setImage(resizedImage.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        
        return resizedImage;
        
    }

}
