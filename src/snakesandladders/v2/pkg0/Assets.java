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
 *
 * @author Zac
 */
public class Assets {

    //Objects
    private ArrayList<Assets> images;
    public String iconPath;
    public String name;

    //Constructors
    public Assets() {

        images = new ArrayList<>();
        images.add(new Assets("src/assets/icons/clover4_icon.png", "clover4"));
        images.add(new Assets("src/assets/icons/dice1_icon.png", "dice1"));
        images.add(new Assets("src/assets/icons/dice2_icon.png", "dice2"));
        images.add(new Assets("src/assets/icons/dice3_icon.png", "dice3"));
        images.add(new Assets("src/assets/icons/dice4_icon.png", "dice4"));
        images.add(new Assets("src/assets/icons/dice5_icon.png", "dice5"));
        images.add(new Assets("src/assets/icons/dice6_icon.png", "dice6"));
        images.add(new Assets("src/assets/icons/exchange_pawns_icon.png", "exchange_pawns"));
        images.add(new Assets("src/assets/icons/explosion_icon.png", "explosion"));
        images.add(new Assets("src/assets/icons/gravity_reversal_icon.png", "gravity_reversal"));
        images.add(new Assets("src/assets/icons/love_icon.png", "love"));
        images.add(new Assets("src/assets/icons/pawn_blue_icon.png", "pawn_blue"));
        images.add(new Assets("src/assets/icons/pawn_green_icon.png", "pawn_green"));
        images.add(new Assets("src/assets/icons/pawn_magenta_icon.png", "pawn_magenta"));
        images.add(new Assets("src/assets/icons/pawn_red_icon - Copy.png", "pawn_red"));
        images.add(new Assets("src/assets/icons/rethrow_dice_icon.png", "rethrow_dice"));
        images.add(new Assets("src/assets/icons/reverse_cancel_icon.png", "reverse_cancel"));
        images.add(new Assets("src/assets/icons/reverse_icon.png", "reverse"));
        images.add(new Assets("src/assets/icons/turtle_cancel_icon.png", "turtle_cancel"));
        images.add(new Assets("src/assets/icons/turtle_icon.png", "turtle"));

    }

    public Assets(String iconPath, String name) {

        this.iconPath = iconPath;
        this.name = name;

    }
    
    //Methods
    public ImageIcon getResizedIcon(String name, int width, int height) {
        
        ImageIcon resizedImage = null;
//        java.net.URL tempURL;
        
        for (int i = 0; i < images.size(); i++) {
            if(images.get(i).name.equals(name)) {
//                tempURL = getClass().getResource(images.get(i).iconPath);
                resizedImage = new ImageIcon(images.get(i).iconPath);
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
