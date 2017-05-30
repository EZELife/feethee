/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakesandladders.v2.pkg0.Logic;

/**
 *Has the history of the current round, also has a boolean that determines
 * whether that history is hidden or not
 * @author Zac
 */
public class History {
    
    //Fields
    String history;
    boolean hidden;
    
    /**
     *Initializes history as an empty string and hidden as false
     */
    public History() {
        history = "";
        hidden = false;
    }
    
    /**
     *Empties history
     */
    public void reset(){
        history = "";
    }
    
    /**
     *Append history with the string given.
     * Meaning it adds a new line and the string given to history
     * @param stringToAppend
     */
    public void append(String stringToAppend) {
        
        history += stringToAppend+"\n";
        
    }
    
    /**
     *Returns history if hidden is false, otherwise returns an empty string
     * @return
     */
    public String getHistory() {
        
        if(hidden)
            return "";
        
        return history;
        
    }

    /**
     *
     * @return
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     *
     * @param hidden
     */
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
    
    
}
