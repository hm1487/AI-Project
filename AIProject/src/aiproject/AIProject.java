/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aiproject;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.*;

/**
 *
 * @author htoomin
 */
public class AIProject {

    /**
     * @param args the command line arguments
     */
    static boolean holdingPiece = false;
    static ArrayList <JButton> al = new ArrayList<JButton>();
    static JButton holder;
    static boolean ready = false;
    static int moveCounter = 0;
    static boolean takenPiece = false;
    static int redPlayerCounter = 0;
    static int blackPlayerCounter = 0;
    final int maxUtilityValue = -1000;
    final int minUtilityValue = 1000;
    
    //Need to create an arraylist of possible actions that the AI can take
    //Also need to make sure pieces cannot move backwards
    public class AlphaBeta{
        ArrayList<JButton> actions;
        
        AlphaBeta(ArrayList<JButton> moves){
            actions = moves;
        }
        private ArrayList<Integer> validMoves(){
            ArrayList<Integer> holder = new ArrayList();
            
            return holder; 
        }
        public void alphaBetaSearch(){
            int value = maxValue(maxUtilityValue,minUtilityValue);
            //Actual taking of action goes here
            
        }
        public int maxValue(int alpha, int beta){
            int value = maxUtilityValue;
            
            return value;
        }
        
        public int minValue(int alpha, int beta){
            int value = minUtilityValue;
            
            return value;
        }
        
       
    }
    
    public static ArrayList<JButton> legalMoves(){
        ArrayList<JButton> temp = new ArrayList();
        for (int x = 0; x < al.size(); x++){
            if (al.get(x).getForeground().equals(Color.black) && al.get(x).getText().equals("O")){
                if (x+5 < al.size()){
                    if (!al.get(x+5).getText().equals("O") && !al.get(x+5).getBackground().equals(Color.black)){
                        //System.out.println("Break1");
                        temp.add(al.get(x+5));
                    }
                }
                if (x+7 <al.size() && !al.get(x+5).getBackground().equals(Color.black)){
                    if (!al.get(x+7).getText().equals("O")){
                        //System.out.println("Break1");
                        temp.add(al.get(x+5));
                    }
                }
                if (x + 10 < al.size()){
                    if (!al.get(x+10).getText().equals("O") && (al.get(x+5).getForeground().equals(Color.white))){
                        System.out.println("Break1");
                        temp.add(al.get(x+10));
                    }
                }
                if (x + 14 < al.size()){
                    if (!al.get(x+14).getText().equals("O") && (al.get(x+7).getForeground().equals(Color.white))){
                        System.out.println("Break2");
                        temp.add(al.get(x+14));
                    } 
                }
            }
        }
        return temp;
    }
    
    public static boolean legalMove(JButton x){
        int destination = al.indexOf(x);
        int origin = al.indexOf(holder);
        if (x.getText().equals("O"))
            return false;
        if (Math.abs(destination-origin) == 5 || Math.abs(destination-origin) == 7)
            return true;
        else if (al.get(origin+5).getText().equals("O") && !(al.get(origin+5).getForeground().equals(holder.getForeground()))){
            System.out.println("We got to break 1");
            if (Math.abs(destination-origin) == 10 || !x.getText().equals("O")){
                al.get(origin+5).setText("");
                if (holder.getForeground() == Color.white){
                    redPlayerCounter++;
                    ScoreCount.jLabel5.setText(redPlayerCounter+"");
                }
                else{
                    blackPlayerCounter++;
                    ScoreCount.jLabel6.setText(blackPlayerCounter+"");
                }
                return true;
            }
        }
        else if (al.get(origin+7).getText().equals("O") && !(al.get(origin+7).getForeground().equals(holder.getForeground()))){
            System.out.println("We got to break 2");
            if (destination - origin == 14 || !x.getText().equals("O")){
                al.get(origin+7).setText("");
                if (holder.getForeground() == Color.white){
                    redPlayerCounter++;
                    ScoreCount.jLabel5.setText(redPlayerCounter+"");
                }
                else{
                    blackPlayerCounter++;
                    ScoreCount.jLabel6.setText(blackPlayerCounter+"");
                }
                return true;
            }
        }
        else if (al.get(origin-7).getText().equals("O") && !(al.get(origin-7).getForeground().equals(holder.getForeground()))){
            if (destination - origin == -14 || !x.getText().equals("O")){
                al.get(origin-7).setText("");
                if (holder.getForeground() == Color.white){
                    redPlayerCounter++;
                    ScoreCount.jLabel5.setText(redPlayerCounter+"");
                }
                else{
                    blackPlayerCounter++;
                    ScoreCount.jLabel6.setText(blackPlayerCounter+"");
                }
                return true;
            }
        }
        else if (al.get(origin-5).getText().equals("O") && !(al.get(origin-5).getForeground().equals(holder.getForeground()))){
            if (destination-origin == -10 || !x.getText().equals("O")){
                al.get(origin-5).setText("");
                if (holder.getForeground() == Color.white){
                    redPlayerCounter++;
                    ScoreCount.jLabel5.setText(redPlayerCounter+"");
                }
                else{
                    blackPlayerCounter++;
                    ScoreCount.jLabel6.setText(blackPlayerCounter+"");
                }
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args) throws InterruptedException {
        IntroScreen is = new IntroScreen();
        is.setVisible(true);
        while (!ready){
            Thread.sleep(100);
        }
        JFrame jf = new JFrame("Checkers!");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(500,500);
        
        JPanel jp = new JPanel();
        jp.setLayout(new GridLayout(6,6)); //Change the layout of the buttons here
        for (int x = 1; x < 37; x++){ //change the number of buttons here
            al.add(new JButton(Integer.toString(x)));
        }
        ScoreCount score = new ScoreCount();
        score.setVisible(true);
        int counter = 1;
        for (JButton x : al){
            x.setOpaque(true);
            x.setContentAreaFilled(true);
            x.setBorderPainted(false);
            x.addActionListener(new MyButtonAction());
            x.setText("");
            if (counter % 2 == 0){
                x.setBackground(Color.red);
                if (counter < 13)
                    x.setText("O");
                else if (counter > 27){
                    x.setText("O");
                    x.setForeground(Color.white);
                }
                else{
                    x.setForeground(null);
                }
            }
            else{
                x.setBackground(Color.black);
            }
            counter++;
            if (counter % 7 == 0)
                counter++;
            jp.add(x);
        }
        jf.add(jp);
        jf.setVisible(true);
    }
    static class MyButtonAction implements ActionListener{
        public void actionPerformed(ActionEvent e){
            JButton jb = (JButton) e.getSource();
            System.out.println(jb);
            if (!jb.getText().equals("") || (jb.getText().equals("") && holdingPiece == true)){
                if (jb.getForeground().equals(Color.black) && holdingPiece == false){
                    System.out.println("That piece is Black! Yuck!");
                    holdingPiece = false;
                } 
                else if (holdingPiece == false && !jb.getText().equals("") ){
                    holdingPiece = true;
                    holder = jb;
                }
                else if (holdingPiece == true && legalMove(jb)){
                    if (holder.getForeground().equals(Color.white)){
                        jb.setText("O");
                        holder.setText("");
                        jb.setForeground(Color.white);
                        moveCounter += 1;
                        holdingPiece = false;
                        
                        ArrayList<JButton> temporary = new ArrayList();
                        temporary = legalMoves();
                        System.out.println(temporary.size());
                    }
   
                }
            }
        }
    }
}
        




