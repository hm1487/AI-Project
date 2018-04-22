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
    public boolean logicChecker(JFrame jf){
        return true;
    }
    
    public class Node{
        int data;
        List<Node> children;
    }
    
    public class Tree{
        Node root;
    }
    
    public class MiniMax{
        
        private ArrayList<Integer> validMoves(){
            ArrayList<Integer> holder = new ArrayList();
            
            return holder; 
        }
        
        
        private void move(int depth, String color, boolean maxPlayer){
            double alpha = Double.NEGATIVE_INFINITY;
            double beta = Double.POSITIVE_INFINITY;
        }
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
            System.out.println("We got to break 3");
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
            System.out.println("We got to break 4");
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
                if (counter > 27){
                    x.setText("O");
                    x.setForeground(Color.white);
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
            if (!jb.getText().equals("") || jb.getText().equals("") && holdingPiece == true){
                if (holdingPiece == false){
                    holdingPiece = true;
                    holder = jb;
                }
                else if (holdingPiece == true && legalMove(jb)){
                    if (holder.getForeground().equals(Color.white)){
                        jb.setText("O");
                        holder.setText("");
                        jb.setForeground(Color.white);
                        moveCounter += 1;
                    }
                    else{
                        jb.setText("O");
                        holder.setText("");
                        jb.setForeground(Color.black);
                        moveCounter+=1;
                    }
                    holdingPiece = false;
                        
                }
            }
        }
    }
}
        




