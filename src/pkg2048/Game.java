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

    private static final Random rnd = new Random();
    private final int SIZE;
    private Field[][] fields; // [->][^]
    private MovementListener keyboard;
    private int moves;
    
    public Game(int size) {
        this.SIZE = size;
        initialize();
        setup();
        insertRandom();
        insertRandom();
        
    }

    private void setup() {
        this.moves = 0;
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
        System.out.println("InsertResult: " + fields[choice.x][choice.y].tryTouch());
        this.repaint();
        return true;
    }
    
    @Override
    public boolean left() {
        boolean movedAny = false;
        for (int row = 0; row < SIZE; row++){
            int col = 1;
            while(col < 4){

                Field f_low = fields[col - 1][row];
                Field f_high = fields[col][row];
                
                switch (f_low.addField(f_high)){
                    case MERGED:
                        col++;
                        movedAny = true;
                        f_low.setMerged(true);
                        break;
                    case MOVED:
                        col--;
                        movedAny = true;
                        break;
                    case NOACTION:
                        col++;
                        break;
                }

                if(col < 1){
                    col = 1;
                }
            }
        }
        System.out.println("moved any: " + movedAny);
        if(movedAny){
            startNextRound();
        }
        return movedAny;
    }

    @Override
    public boolean right() {
        boolean movedAny = false;
        for (int row = 0; row < SIZE; row++){
            int max = SIZE - 2;
            int col = max;
            while(col >= 0){

                Field f_low = fields[col + 1][row];
                Field f_high = fields[col][row];
                
                switch (f_low.addField(f_high)){
                    case MERGED:
                        col--;
                        movedAny = true;
                        f_low.setMerged(true);
                        break;
                    case MOVED:
                        col++;
                        movedAny = true;
                        break;
                    case NOACTION:
                        col--;
                        break;
                }

                if(col > max){
                    col = max;
                }
            }
        }
        System.out.println("moved any: " + movedAny);
        if(movedAny){
            startNextRound();
        }
        return movedAny;
    }

    @Override
    public boolean up() {
        boolean movedAny = false;
        for (int col = 0; col < SIZE; col++){

            int row = 1;
            while(row < 4){

                Field f_low = fields[col][row - 1];
                Field f_high = fields[col][row];
                
                switch (f_low.addField(f_high)){
                    case MERGED:
                        row++;
                        movedAny = true;
                        f_low.setMerged(true);
                        break;
                    case MOVED:
                        row--;
                        movedAny = true;
                        break;
                    case NOACTION:
                        row++;
                        break;
                }

                if(row < 1){
                    row = 1;
                }
            }
        }
        System.out.println("moved any: " + movedAny);
        if(movedAny){
            startNextRound();
        }
        return movedAny;
    }

    @Override
    public boolean down() {
        boolean movedAny = false;
        for (int col = 0; col < SIZE; col++){
            int max = SIZE - 2;
            int row = max;
            while(row >= 0){

                Field f_low = fields[col][row + 1];
                Field f_high = fields[col][row];
                
                switch (f_low.addField(f_high)){
                    case MERGED:
                        row--;
                        movedAny = true;
                        f_low.setMerged(true);
                        break;
                    case MOVED:
                        row++;
                        movedAny = true;
                        break;
                    case NOACTION:
                        row--;
                        break;
                }

                if(row > max){
                    row = max;
                }
            }
        }
        System.out.println("moved any: " + movedAny);
        if(movedAny){
            startNextRound();
        }
        return movedAny;
    }

    @Override
    public void restart() {
        this.moves = 0;
        for (int col = 0; col < SIZE; col++){
            for (int row = 0; row < SIZE; row++){
                fields[col][row].restart();
            }
        }
        insertRandom();
        insertRandom();
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

    private void startNextRound() {
        setFieldsNewRound();
        insertRandom();
    }

    private void setFieldsNewRound() {
        this.moves++;
        System.out.println("Moves: " + this.moves + "!");
        for (int col = 0; col < SIZE; col++){
            for (int row = 0; row < SIZE; row++){
                if(fields[col][row].newRound()){
                    this.moves--;
                    System.out.println("game won now!");
                    return;
                }
            }
        }
    }
}
