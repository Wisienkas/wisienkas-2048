/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pkg2048;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author wisienkas
 */
public class Application extends JFrame{
    
    public static final int WINCONDITION_SCORE = 2048;
    
    private Game game;
    
    public Application(){
        setPreferences();
        loadContent();
    }

    private void setPreferences() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setVisible(true);
        setLayout(new BorderLayout());
    }

    private void loadContent() {
        System.out.println("Loading Game...");
        game = new Game(4);
        add(this.game);
        System.out.println("Setting MovementListener!");
        this.addKeyListener(new MovementListener(this.game));
    }
}
