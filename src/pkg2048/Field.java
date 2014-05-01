/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pkg2048;

import java.awt.Point;
import java.awt.geom.Point2D;

/**
 *
 * @author wisienkas
 */
class Field {

    public enum action{
        MERGED, MOVED, NOACTION;
    }
    
    private boolean movedThisRound;
    private int value;
    private final Point pos;
    
    public Field(int width, int height){
        value = 0;
        pos = new Point(width, height);
    }
    
    public Point getPosition(){
        return this.pos;
    }
    
    public int getValue(){
        return this.value;
    }
    
    public boolean hasValue(){
        return this.value != 0;
    }
    
    public action addField(Field f){
        if(!f.hasValue() || f.hasMerged()){
            return action.NOACTION;
        }else if(f.value == this.value){
            this.value *= 2;
            f.value = 0;
            return action.MERGED;
        }else if(this.value == 0){
            this.value = f.value;
            f.value = 0;
            return action.MOVED;
        }
        return action.NOACTION;
    }
    
    public boolean tryTouch(){
        if(hasValue()){
            return false;
        }else{
            this.value = 2;
            return true;
        }
    }
    
    public void setMerged(boolean moved) {
        this.movedThisRound = moved;
    }
    
    public boolean hasMerged(){
        return this.movedThisRound;
    }
    
    public boolean newRound() {
        this.movedThisRound = false;
        if(this.value == Application.WINCONDITION_SCORE){
            System.out.println("GAME WON!!");
            return true;
        }else{
            return false;
        }
    }
    
    void restart() {
        this.value = 0;
        this.movedThisRound = false;
    }
    
}
