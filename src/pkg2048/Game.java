/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2048;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author wisienkas
 */
public class Game extends JPanel implements Move{

    private static Random rnd = new Random();
    private final int SIZE;
    private Field[][] fields; // [->][^]
    private MovementListener keyboard;
    
    public Game(int size) {
        this.SIZE = size;
        initialize();
        setup();
        insertRandom();
        insertRandom();
        
    }

    private void setup() {
        this.fields = new Field[4][4];
        int interval;
        if(this.getWidth() > this.getHeight()){
            interval = this.getHeight() / SIZE;
        }else{
            interval = this.getWidth() / SIZE;
        }
        
        for (int col = 0; col < SIZE; col++) {
            for (int row = 0; row < SIZE; row++) {
                int x = (interval * col) + interval / 3;
                int y = (interval * row) + interval / 3;
                fields[col][row] = new Field(x, y);
            }
        }
        
        this.keyboard = new MovementListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int col = 0; col < SIZE; col++) {
            for (int row = 0; row < SIZE; row++){
                Field f = fields[col][row];
                int val = f.getValue();
                Point p = f.getPosition();
                g.drawString(Integer.toString(val), p.x, p.y);
            }
        }
    }

    private void initialize() {
        setSize(200, 200);
    }

    /**
     * 
     * @param from
     * @param to
     * @return 1 for merge, 2 for move, -1 for not moved
     */
    private int tryMove(Field from, Field to){
        if(to.hasValue()){
            if(to.getValue() == from.getValue()){
                to.addField(from);
                return 1;
            }else{
                return -1;
            }
        }else{
            if(from.hasValue()){
                to.addField(from);
                return 2;
            }else{
                return -1;
            }
        }
    }
    
    private boolean insertRandom(){
        Point[] checkMap = getPossible();
        if(checkMap.length == 0){
            return false;
        }
        Point choice = checkMap[rnd.nextInt(checkMap.length)];
        fields[choice.x][choice.y].tryTouch();
        return true;
    }
    
    @Override
    public boolean left() {
        boolean movedAny = false;
        for(int row = 0; row < SIZE; row++){
            for (int col = 1; col < SIZE; col++){
                for(int i = col - 1; i >= 0; i--){
                    int result = tryMove(fields[i + 1][row], fields[i + 1][row]);
                    if(result == 1 || result == -1){
                        if(result == 1){
                            movedAny = true;
                        }
                        break;
                    }else{
                        movedAny = true;
                    }
                }
            }
        }
        if(movedAny){
            insertRandom();
        }
        return movedAny;
    }

    @Override
    public boolean right() {
        boolean movedAny = false;
        for(int row = 0; row < SIZE; row++){
            for (int col = SIZE - 2; col < SIZE; col--){
                for(int i = col + 1; i < SIZE; i++){
                    int result = tryMove(fields[i - 1][row], fields[i][row]);
                    if(result == 1 || result == -1){
                        if(result == 1){
                            movedAny = true;
                        }
                        break;
                    }else{
                        movedAny = true;
                    }
                }
            }
        }
        if(movedAny){
            insertRandom();
        }
        return movedAny;
    }

    @Override
    public boolean up() {
        return false;
    }

    @Override
    public boolean down() {
        return false;
    }

    @Override
    public void restart() {
        setup();
    }

    private boolean canMove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Point[] getPossible() {
        ArrayList<Point> result_list = new ArrayList<>();
        for (int col = 0; col < SIZE; col++){
            for (int row = 0; row < SIZE; row++){
                if(!fields[col][row].hasValue()){
                    result_list.add(new Point(col, row));
                }
            }
        }
        Point[] result = new Point[result_list.size()];
        for (int i = 0; i < result.length; i++){
            result[i] = result_list.get(i);
        }
        return result;
    }
}
