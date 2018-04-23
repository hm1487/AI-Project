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
        
        private ArrayList<Integer> validMoves(){
            ArrayList<Integer> holder = new ArrayList();
            
            return holder; 
        }
        public void alphaBetaSearch(){
            int value = maxValue(boardCopy,maxUtilityValue,minUtilityValue);
            //Actual taking of action goes here
            
        }
        public int maxValue(ArrayList<JButton> state, int alpha, int beta){
            int value = maxUtilityValue;
            int counter = 0;
            actions = aiMoves(boardCopy);
            for (JButton x : actions){
                int indexOrigin = boardCopy.indexOf(jumpReference.get(counter));
                int indexDesired = boardCopy.indexOf(x);
                int diff = indexDesired - indexOrigin;
                if (diff == 5 || diff == 7){
                    boardCopy.get(indexOrigin).setText("");
                    boardCopy.get(indexDesired).setText("O");
                    boardCopy.get(indexDesired).setForeground(Color.black);
                }
                if (diff == 10){
                    boardCopy.get(indexOrigin).setText("");
                    boardCopy.get(indexOrigin+5).setText("");
                    boardCopy.get(indexDesired).setText("O");
                    boardCopy.get(indexDesired).setForeground(Color.black);
                    blackPlayerCounter++;
                }
                if (diff == 14){
                    boardCopy.get(indexOrigin).setText("");
                    boardCopy.get(indexOrigin+7).setText("");
                    boardCopy.get(indexDesired).setText("O");
                    boardCopy.get(indexDesired).setForeground(Color.black);
                    blackPlayerCounter++;
                }
                
                value = Math.max(value, minValue(boardCopy,alpha,beta));
                if (value >= beta)
                    return value;
                alpha = Math.max(alpha, value);
                counter++;
            }
            return value;
        }
        
        public int minValue(ArrayList<JButton> state,int alpha, int beta){
            int value = minUtilityValue;
            int counter = 0;
            actions = aiMoves(boardCopy);
            for (JButton x : actions){
                int indexOrigin = boardCopy.indexOf(jumpReference.get(counter));
                int indexDesired = boardCopy.indexOf(x);
                int diff = indexDesired - indexOrigin;
                if (diff == 5 || diff == 7){
                    boardCopy.get(indexOrigin).setText("");
                    boardCopy.get(indexDesired).setText("O");
                    boardCopy.get(indexDesired).setForeground(Color.black);
                }
                if (diff == 10){
                    boardCopy.get(indexOrigin).setText("");
                    boardCopy.get(indexOrigin+5).setText("");
                    boardCopy.get(indexDesired).setText("O");
                    boardCopy.get(indexDesired).setForeground(Color.black);
                    blackPlayerCounter++;
                }
                if (diff == 14){
                    boardCopy.get(indexOrigin).setText("");
                    boardCopy.get(indexOrigin+7).setText("");
                    boardCopy.get(indexDesired).setText("O");
                    boardCopy.get(indexDesired).setForeground(Color.black);
                    blackPlayerCounter++;
                }
                
                value = Math.min(value, maxValue(boardCopy,alpha,beta));
                if (value <= alpha)
                    return value;
                beta = Math.min(beta, value);
                counter++;
            }
            return value;
        }
        
       
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
                        
                        AlphaBeta ab = new AlphaBeta();
                        ab.alphaBetaSearch();
                    }
   
                }
            }
        }
    }
}
        




