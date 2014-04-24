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
    
    public boolean addField(Field f){
        if(f.value == this.value){
            this.value *= 2;
            f.value = 0;
            return true;
        }
        return false;
    }
    
    public boolean tryTouch(){
        if(hasValue()){
            return false;
        }else{
            this.value = 2;
            return true;
        }
    }
    
}
