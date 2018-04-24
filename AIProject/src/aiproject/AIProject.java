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
    static final int maxUtilityValue = -1000;
    static final int minUtilityValue = 1000;
    static ArrayList<JButton> jumpReference;
    
    //Need to create an arraylist of possible actions that the AI can take
    //Also need to make sure pieces cannot move backwards
    static class AlphaBeta{
        ArrayList<JButton> actions;
        ArrayList<JButton> boardCopy = al;
        int debug = 0;
        
        private ArrayList<Integer> validMoves(){
            ArrayList<Integer> holder = new ArrayList();
            
            return holder; 
        }
        public void alphaBetaSearch(){
            int value = maxValue(boardCopy,maxUtilityValue,minUtilityValue);
            System.out.println(value);
            
        }
        public int maxValue(ArrayList<JButton> state, int alpha, int beta){
            System.out.println(debug  +  " Max");
            debug++;
            if (completeBoard() != null){
                System.out.println(completeBoard().getForeground().equals(Color.black));
                if (completeBoard().getForeground().equals(Color.black)){
                    return 1000;
                }
                else{
                    return -1000;
                }
            }
            
            int value = maxUtilityValue;
            int counter = 0;
            actions = aiMoves(state);
            for (JButton x : actions){
                int indexOrigin = state.indexOf(jumpReference.get(counter));
                int indexDesired = state.indexOf(x);
                int diff = indexDesired - indexOrigin;
                if (diff == 5 || diff == 7){
                    state.get(indexOrigin).setText("");
                    state.get(indexDesired).setText("O");
                    state.get(indexDesired).setForeground(Color.black);
                }
                if (diff == 10){
                    state.get(indexOrigin).setText("");
                    state.get(indexOrigin+5).setText("");
                    state.get(indexDesired).setText("O");
                    state.get(indexDesired).setForeground(Color.black);
                    blackPlayerCounter++;
                }
                if (diff == 14){
                    state.get(indexOrigin).setText("");
                    state.get(indexOrigin+7).setText("");
                    state.get(indexDesired).setText("O");
                    state.get(indexDesired).setForeground(Color.black);
                    blackPlayerCounter++;
                }
                
                value = Math.max(value, minValue(state,alpha,beta));
                if (value >= beta)
                    return value;
                alpha = Math.max(alpha, value);
                counter++;
            }
            return value;
        }
        
        public int minValue(ArrayList<JButton> state,int alpha, int beta){
            System.out.println(debug + " Min");
            debug++;
            if (completeBoard() != null){
                if (completeBoard().getForeground().equals(Color.black)){
                    return 1000;
                }
                else{
                    return -1000;
                }
            }
            int value = minUtilityValue;
            int counter = 0;
            actions = aiMoves(state);
            for (JButton x : actions){
                int indexOrigin = state.indexOf(jumpReference.get(counter));
                int indexDesired = state.indexOf(x);
                int diff = indexDesired - indexOrigin;
                if (diff == 5 || diff == 7){
                    state.get(indexOrigin).setText("");
                    state.get(indexDesired).setText("O");
                    state.get(indexDesired).setForeground(Color.black);
                }
                if (diff == 10){
                    state.get(indexOrigin).setText("");
                    state.get(indexOrigin+5).setText("");
                    state.get(indexDesired).setText("O");
                    state.get(indexDesired).setForeground(Color.black);
                    blackPlayerCounter++;
                }
                if (diff == 14){
                    state.get(indexOrigin).setText("");
                    state.get(indexOrigin+7).setText("");
                    state.get(indexDesired).setText("O");
                    state.get(indexDesired).setForeground(Color.black);
                    blackPlayerCounter++;
                }
                
                value = Math.min(value, maxValue(state,alpha,beta));
                if (value <= alpha)
                    return value;
                beta = Math.min(beta, value);
                counter++;
            }
            return value;
        }
        
       
    }
    
    public static JButton completeBoard(){
        int counter = 0;
        JButton holder = null;
        for (JButton x : al){
            if (x.getText() != ""){
                if (counter == 0){
                    counter++;
                    holder = x;
                }
                if (counter == 1){
                    if (!x.getForeground().equals(holder.getForeground())){
                        return null;
                    }
                }
            }
        }
        return holder;
    }
    public static ArrayList<JButton> aiMoves(ArrayList<JButton> ref){
        ArrayList<JButton> temp = new ArrayList();
        jumpReference = new ArrayList();
        for (int x = 0; x < ref.size(); x++){
            if (ref.get(x).getForeground().equals(Color.black) && ref.get(x).getText().equals("O")){
                if (x+5 < ref.size()){
                    if (!ref.get(x+5).getText().equals("O") && !ref.get(x+5).getBackground().equals(Color.black)){
                        //System.out.println("Break1");
                        temp.add(ref.get(x+5));
                        jumpReference.add(ref.get(x));
                    }
                }
                if (x+7 <ref.size() && !ref.get(x+5).getBackground().equals(Color.black)){
                    if (!ref.get(x+7).getText().equals("O")){
                        //System.out.println("Break1");
                        temp.add(ref.get(x+5));
                        jumpReference.add(ref.get(x));
                    }
                }
                if (x + 10 < ref.size()){
                    if (!ref.get(x+10).getText().equals("O") && (ref.get(x+5).getForeground().equals(Color.white))){
                        //System.out.println("Break1");
                        temp.add(ref.get(x+10));
                        jumpReference.add(ref.get(x));
                    }
                }
                if (x + 14 < ref.size()){
                    if (!ref.get(x+14).getText().equals("O") && (ref.get(x+7).getForeground().equals(Color.white))){
                        //System.out.println("Break2");
                        temp.add(ref.get(x+14));
                        jumpReference.add(ref.get(x));
                    } 
                }
            }
        }
        return temp;
    }
    public static ArrayList<JButton> playerMoves(ArrayList<JButton> ref){
        ArrayList<JButton> temp = new ArrayList();
        jumpReference = new ArrayList();
        for (int x = 0; x < ref.size(); x++){
            if (ref.get(x).getForeground().equals(Color.white) && ref.get(x).getText().equals("O")){
                if (x-5 > 0 && ref.get(x-5).getBackground().equals(Color.red)){
                    if (!ref.get(x-5).getText().equals("O")){
                        System.out.println(x);
                        System.out.println("Break1");
                        temp.add(ref.get(x-5));
                        jumpReference.add(ref.get(x));
                    }
                }
                if (x-7 > 0 && ref.get(x-7).getBackground().equals(Color.red)){
                    if (!ref.get(x-7).getText().equals("O")){
                        System.out.println(x);
                        System.out.println("Break2");
                        temp.add(ref.get(x-7));
                        jumpReference.add(ref.get(x));
                    }
                }
                if (x - 10 > 0 && ref.get(x-10).getBackground().equals(Color.red)){
                    if (!ref.get(x-10).getText().equals("O")){
                        if (ref.get(x-5).getText().equals("O") && !ref.get(x-5).getForeground().equals(Color.white)){
                            System.out.println(x);
                            System.out.println("Break3");
                            temp.add(ref.get(x-10));
                            jumpReference.add(ref.get(x));
                        }
                    }
                }
                if (x - 14 > 0 && ref.get(x-14).getBackground().equals(Color.red)){
                    if (!ref.get(x-14).getText().equals("O")){
                        if (ref.get(x-7).getText().equals("O") &&!ref.get(x-7).getForeground().equals(Color.white)){
                            System.out.println(x);
                            System.out.println("Break4");
                            temp.add(ref.get(x-14));
                            jumpReference.add(ref.get(x));
                        }
                    } 
                }
            }
        }
        System.out.println();
        return temp;
    }
    public static boolean legalMove(JButton x){
        int destination = al.indexOf(x);
        int origin = al.indexOf(holder);
        if (destination - origin > 0)
            return false;
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
                        ArrayList<JButton> temp = playerMoves(al);
                        System.out.println(temp.size());
                    }
   
                }
            }
        }
    }
}
        




