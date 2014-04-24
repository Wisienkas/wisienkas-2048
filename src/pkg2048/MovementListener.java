/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pkg2048;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author wisienkas
 */
public class MovementListener implements KeyListener{

    private Move game;
    
    public MovementListener(Move m){
        this.game = m;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
                int code = e.getKeyCode();
        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
            System.out.println("left");
            game.left();
        }else if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
            System.out.println("right");
            game.right();
        }else if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            System.out.println("up");
            game.up();
        }else if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            System.out.println("down");
            game.down();
        }else if(code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ESCAPE){
            System.out.println("restart");
            game.restart();
        }
    }
    
    
    
}
