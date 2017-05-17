/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakesandladders.v2.pkg0.Logic;

/**
 *
 * @author Zac
 */
public class History {
    
    //Fields
    String history;
    boolean hidden;
    
    public History() {
        history = "";
        hidden = false;
    }
    
    public void reset(){
        history = "";
    }
    
    public void append(String stringToAppend) {
        
        history += stringToAppend+"\n";
        
    }
    
    public String getHistory() {
        
        if(hidden)
            return "";
        
        return history;
        
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
    
    
}
