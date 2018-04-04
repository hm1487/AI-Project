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
    public boolean logicChecker(JFrame jf){
        return true;
    }
    
    public static boolean legalMove(JButton x){
        int index1 = al.indexOf(x);
        int index2 = al.indexOf(holder);
        if (Math.abs(index1-index2) == 5 || Math.abs(index1-index2) == 7)
            return true;
        return false;
    }
    public static void main(String[] args) {
        JFrame jf = new JFrame("Checkers!");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(500,500);
        
        JPanel jp = new JPanel();
        jp.setLayout(new GridLayout(6,6)); //Change the layout of the buttons here
        for (int x = 1; x < 37; x++){ //change the number of buttons here
            al.add(new JButton(Integer.toString(x)));
        }
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
                    }
                    else{
                        jb.setText("O");
                        holder.setText("");
                        jb.setForeground(Color.black);
                    }
                    holdingPiece = false;
                        
                }
            }
        }
    }
}
        




