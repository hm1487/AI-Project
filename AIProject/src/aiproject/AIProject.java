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
    static boolean aiFirst = false;
    static int moveCounter = 0;
    static boolean takenPiece = false;
    static int redPlayerCounter = 0;
    static int blackPlayerCounter = 0;
    static final int maxUtilityValue = -1000;
    static final int minUtilityValue = 1000;
    static ArrayList<Integer> jumpReference = new ArrayList();
    static ArrayList<Integer> jumpPlayerReference = new ArrayList();
    
    //Need to create an arraylist of possible actions that the AI can take
    //Also need to make sure pieces cannot move backwards
    static class AlphaBeta{
        ArrayList<Integer> actionsAI;
        ArrayList<Integer> actionsHuman;
        private ArrayList<JButton> boardCopy = al;
        ArrayList<Integer> state = new ArrayList();
        Node<ArrayList<Integer>> parent;
        int debug = 0;
        
        AlphaBeta(){
            for (JButton x : al){
                if (x.getText() == ""){
                    state.add(0);
                }
                else{
                    if (x.getForeground().equals(Color.white)){
                        state.add(1);
                    }
                    else{
                        state.add(2);
                    }
                }
            }
            Queue<Node<ArrayList<Integer>>> searchQueue = new QueueLinkedList<>();
            Node<ArrayList<Integer>> parent = new Node<ArrayList<Integer>>(state);
            searchQueue.enqueue(parent);
            int counter = 0;
            while (!searchQueue.isEmpty()){
                System.out.println("Depth: " + searchQueue.top().depth());
                actionsAI = aiMoves(searchQueue.top().getData());
                System.out.println("Possible Actions: " + actionsAI.size());
                if (counter % 2 == 0){
                for (int x = 0; x < actionsAI.size(); x++){
                  int indexOrigin = jumpReference.get(x);
                  int indexDesired = actionsAI.get(x);
                  boolean helpMe = true;
                  int diff = indexDesired - indexOrigin;
                  while (helpMe){
                     if (diff != 5 && diff != 7 && diff != 10 && diff != 14)
                         x++;
                     else if((diff == 5 && (indexDesired % 6 == 5))){
                         x++;
                     }
                     else if((diff == 7 && (indexDesired % 6 == 0))){
                         x++;
                     }
                     if (jumpReference.size() == x || actionsAI.size() == x){
                         break;
                     }
                     else{
                         helpMe = false;
                      }
                      indexOrigin = jumpReference.get(x);
                      indexDesired = actionsAI.get(x);
                      diff = indexDesired - indexOrigin;
                  }

                  if (diff == 10){
                      int temp = state.get(indexOrigin);
                      state.set(indexOrigin,0);
                      state.set(indexOrigin+5,0);
                      state.set(indexDesired, temp);
                      Node<ArrayList<Integer>> child = new Node<ArrayList<Integer>>(state,searchQueue.top());
                      searchQueue.enqueue(child);
                      //state.get(indexDesired).setForeground(Color.black);
                      //blackPlayerCounter++;
                      //ScoreCount.jLabel6.setText(blackPlayerCounter + "");
                  }
                  else if (diff == 14){
                      int temp = state.get(indexOrigin);
                      state.set(indexOrigin,0);
                      state.set(indexOrigin+7,0);
                      state.set(indexDesired,temp);
                      Node<ArrayList<Integer>> child = new Node<ArrayList<Integer>>(state,searchQueue.top());
                      searchQueue.enqueue(child);
                      //blackPlayerCounter++;
                      //ScoreCount.jLabel6.setText(blackPlayerCounter + "");
                  }
                  else if ((diff == 5 && (indexDesired % 6 != 0)) || (diff == 7 && (indexDesired % 6 != 5))){
                      int temp = state.get(indexOrigin);
                      state.set(indexOrigin,0);
                      state.set(indexDesired,temp);
                      Node<ArrayList<Integer>> child = new Node<ArrayList<Integer>>(state,searchQueue.top());
                      searchQueue.enqueue(child);
                      //state.get(indexDesired).setForeground(Color.black);
                  }
                }
                }
                else{
                actionsHuman = playerMoves(state);
                for (int x = 0; x < actionsHuman.size(); x++){
                    int indexOrigin = jumpPlayerReference.get(x);
                    int indexDesired = actionsHuman.get(x);
                    int diff = indexDesired - indexOrigin;
                    boolean helpMe = true;
                    while (helpMe){
                       if (diff != -5 && diff != -7 && diff != -10 && diff != -14)
                           x++;
                       else if((diff == -5 && (indexDesired % 6 == 5))){
                           x++;
                       }
                       else if((diff == -7 && (indexDesired % 6 == 0))){
                           x++;
                       }
                       else{
                           helpMe = false;
                        }
                       if (jumpPlayerReference.size() == x || actionsHuman.size() == x){
                           break;
                       }

                        indexOrigin = jumpPlayerReference.get(x);
                        indexDesired = actionsHuman.get(x);
                        diff = indexDesired - indexOrigin;
                    }
                    if (diff == -10){
                        int temp = state.get(indexOrigin);
                        state.set(indexOrigin,0);
                        state.set(indexOrigin-5,0);
                        state.set(indexDesired,temp);
                        //state.get(indexDesired).setForeground(Color.white);
                        //blackPlayerCounter++;
                    }
                    else if (diff == -14){
                        int temp = state.get(indexOrigin);
                        state.set(indexOrigin,0);
                        state.set(indexOrigin-7,0);
                        state.set(indexDesired,temp);
                        //state.get(indexDesired).setForeground(Color.white);
                        //blackPlayerCounter++;
                    }

                    else if (((diff == -5 && (indexDesired % 6 != 5)) || (diff == -7 && (indexDesired % 6 != 0)))){
                        int temp = state.get(indexOrigin);
                        state.set(indexOrigin,0);
                        state.set(indexDesired,temp);
                    }
                }
                }
                counter++;
                searchQueue.dequeue();
            }
        }
        public void alphaBetaSearch(){
            //System.out.println(state.size());
            //int value = maxValue(state,maxUtilityValue,minUtilityValue);
            
        }
        public int maxValue(ArrayList<Integer> state, int alpha, int beta){ // Why the hell is this changing the board?
            //System.out.println(debug  +  " Max");
            debug++;
            int counter = 0;
            if(state.size() > 36){
                while (state.size() > 36)
                    state.remove(state.size()-1);
            }
            //System.out.println();
            //System.out.println(aiMoves(state).size());
            if (aiMoves(state).size() == 0){
                return BoardPlayerEvaluation(state);
            }
            int value = maxUtilityValue;
            actionsAI = aiMoves(state);
            boolean helpMe = true;
            for (int x = 0; x < actionsAI.size(); x++){
                int indexOrigin = jumpReference.get(x);
                int indexDesired = actionsAI.get(x);
                int diff = indexDesired - indexOrigin;
                while (helpMe){
                   if (diff != 5 && diff != 7 && diff != 10 && diff != 14)
                       x++;
                   else if((diff == 5 && (indexDesired % 6 == 5))){
                       x++;
                   }
                   else if((diff == 7 && (indexDesired % 6 == 0))){
                       x++;
                   }
                   if (jumpReference.size() == x || actionsAI.size() == x){
                       break;
                   }
                   else{
                       helpMe = false;
                    }
                    indexOrigin = jumpReference.get(x);
                    indexDesired = actionsAI.get(x);
                    diff = indexDesired - indexOrigin;
                }

                if (diff == 10){
                    int temp = state.get(indexOrigin);
                    state.set(indexOrigin,0);
                    state.set(indexOrigin+5,0);
                    state.set(indexDesired, temp);
                    //state.get(indexDesired).setForeground(Color.black);
                    //blackPlayerCounter++;
                    //ScoreCount.jLabel6.setText(blackPlayerCounter + "");
                }
                else if (diff == 14){
                    int temp = state.get(indexOrigin);
                    state.set(indexOrigin,0);
                    state.set(indexOrigin+7,0);
                    state.set(indexDesired,temp);
                    //blackPlayerCounter++;
                    //ScoreCount.jLabel6.setText(blackPlayerCounter + "");
                }
                else if ((diff == 5 && (indexDesired % 6 != 0)) || (diff == 7 && (indexDesired % 6 != 5))){
                    int temp = state.get(indexOrigin);
                    state.set(indexOrigin,0);
                    state.set(indexDesired,temp);
                    //state.get(indexDesired).setForeground(Color.black);
                }
             
                value = Math.max(value, minValue(state,alpha,beta));
                if (value >= beta)
                    return value;
                alpha = Math.max(alpha, value);
                counter++;
            }
            return value;
        }
        
        public int minValue(ArrayList<Integer> state,int alpha, int beta){
            //System.out.println(debug + " Min");
            //System.out.println(playerMoves(state).size());
            debug++;
            int counter = 0;
            if(state.size() > 36){
                while (state.size() > 36)
                    state.remove(state.size()-1);
            }
            if (playerMoves(state).size() == 0){
                return BoardPlayerEvaluation(state);
            }
            int value = minUtilityValue;
            boolean helpMe = true;
            actionsHuman = playerMoves(state);
            for (int x = 0; x < actionsHuman.size(); x++){
                int indexOrigin = jumpPlayerReference.get(x);
                int indexDesired = actionsHuman.get(x);
                int diff = indexDesired - indexOrigin;
                while (helpMe){
                   if (diff != -5 && diff != -7 && diff != -10 && diff != -14)
                       x++;
                   else if((diff == -5 && (indexDesired % 6 == 5))){
                       x++;
                   }
                   else if((diff == -7 && (indexDesired % 6 == 0))){
                       x++;
                   }
                   else{
                       helpMe = false;
                    }
                   if (jumpPlayerReference.size() == x || actionsHuman.size() == x){
                       break;
                   }
                   
                    indexOrigin = jumpPlayerReference.get(x);
                    indexDesired = actionsHuman.get(x);
                    diff = indexDesired - indexOrigin;
                }
                if (diff == -10){
                    int temp = state.get(indexOrigin);
                    state.set(indexOrigin,0);
                    state.set(indexOrigin+5,0);
                    state.set(indexDesired,temp);
                    //state.get(indexDesired).setForeground(Color.white);
                    //blackPlayerCounter++;
                }
                else if (diff == -14){
                    int temp = state.get(indexOrigin);
                    state.set(indexOrigin,0);
                    state.set(indexOrigin+7,0);
                    state.set(indexDesired,temp);
                    //state.get(indexDesired).setForeground(Color.white);
                    //blackPlayerCounter++;
                }
                
                else if (((diff == -5 && (indexDesired % 6 != 5)) || (diff == -7 && (indexDesired % 6 != 0)))){
                    int temp = state.get(indexOrigin);
                    state.set(indexOrigin,0);
                    state.set(indexDesired,temp);
                }
                value = Math.min(value, maxValue(state,alpha,beta));
                if (value <= alpha)
                    return value;
                beta = Math.min(beta, value);
                counter++;
            }
            return value;
        }
        
        public int BoardAIEvaluation(ArrayList<Integer> board){
        int value = 0;
        for (int x = 0; x < board.size(); x++){
            if (x+5 < board.size()){
                if (board.get(x+5) == 0){
                    value += 1;
                }
            }
            if (x+7 <board.size()){
                if (board.get(x+7) == 0){
                    value += 1;    
                }
            }
            if (x + 10 < board.size()){
                if (board.get(x+10) == 0 && board.get(x+5) != board.get(x) && board.get(x) == 0){
                    value += 10;
                }
            }
            if (x + 14 < board.size()){
                if (board.get(x+14) == 0 && board.get(x+7) != board.get(x) && board.get(x) == 0){
                    value += 10;
                } 
            }
        }
        return value;
        }
    
        public int BoardPlayerEvaluation(ArrayList<Integer> board){
            int value = 0;
            for (int x = 0; x < board.size(); x++){
                if (x+5 < board.size()){
                    if (board.get(x+5) == 0){
                        value += 1;
                    }
                }
                if (x+7 <board.size()){
                    if (board.get(x+7) == 0){
                        value += 1;    
                    }
                }
                if (x + 10 < board.size()){
                    if (board.get(x+10) == 0 && board.get(x+5) != board.get(x) && board.get(x) == 0){
                        value += 10;
                    }
                }
                if (x + 14 < board.size()){
                    if (board.get(x+14) == 0 && board.get(x+7) != board.get(x) && board.get(x) == 0){
                        value += 10;
                    } 
                }
            }
            return value;
        }
        
       
    }
    
    public static ArrayList<Integer> aiMoves(ArrayList<Integer> ref){
        ArrayList<Integer> temp = new ArrayList();
        jumpReference = new ArrayList();
        for (int x = 0; x < ref.size(); x++){
            if (ref.get(x) != 0){
                if (x+5 < ref.size()){
                    if (ref.get(x+5) == 0 && x % 6 != 0 && ref.get(x) == 2){
                        //System.out.println("Break1 " + x);
                        temp.add(x+5);
                        jumpReference.add(x);
                    }
                }
                if (x+7 < ref.size()){
                    if (ref.get(x+7) == 0 && x % 6 != 5 && ref.get(x) == 2){
                    
                        //System.out.println("Break2 " + x);
                        temp.add(x+7);
                        jumpReference.add(x);
                    }
                }
                if (x + 10 < ref.size()){
                    if (ref.get(x+10) == 0 && (ref.get(x) != ref.get(x+5)) && ref.get(x+5) != 0){
                        //System.out.println("Break3 " + x);
                        temp.add(x+10);
                        jumpReference.add(x);
                    }
                }
                if (x + 14 < ref.size()){
                    if (ref.get(x+14) == 0 && (ref.get(x+7) != ref.get(x)) && ref.get(x+7) != 0){
                        //System.out.println("Break4 " + x );
                        temp.add(x+14);
                        jumpReference.add(x);
                    } 
                }
            }
        }
        return temp;
    }
    public static ArrayList<Integer> playerMoves(ArrayList<Integer> ref){
        ArrayList<Integer> temp = new ArrayList();
        jumpReference = new ArrayList();
        for (int x = 0; x < ref.size(); x++){
            if (ref.get(x) != 0){
                if (x-5 > 0){
                    if (ref.get(x-5) == 0 && x % 6 == 0){
                        //System.out.println("Break1");
                        temp.add(x-5);
                        jumpPlayerReference.add(x);
                    }
                }
                if (x-7 > 0){
                    if (ref.get(x-7) == 0 && x % 6 != 5){
                        //System.out.println("Break2");
                        temp.add(x-7);
                        jumpPlayerReference.add(x);
                    }
                }
                if (x - 10 > 0){
                    if (ref.get(x-10) == 0 && (ref.get(x) != ref.get(x-5)) && ref.get(x-5) != 0){
                        //System.out.println("Break3");
                        temp.add(x-10);
                        jumpReference.add(x);
                    }
                }
                if (x - 14 > 0){
                    if (ref.get(x-14) == 0 && (ref.get(x-7) != ref.get(x)) && ref.get(x-7) != 0){
                        //System.out.println("Break4");
                        temp.add(x-14);
                        jumpReference.add(x);
                    } 
                }
            }
        }
        return temp;
    }
    public static boolean legalMove(JButton x){
        int destination = al.indexOf(x);
        int origin = al.indexOf(holder);
        if (destination - origin > 0){
            //System.out.println("uh oh1" + destination + " " + origin);
            return false;
        }
        if (x.getText().equals("O")){
            //System.out.println("uh oh2" + destination + " " + origin);
            return false;
        }
        if (Math.abs(destination-origin) == 5 || Math.abs(destination-origin) == 7){
            //System.out.println("uh oh3" + destination + " " + origin);
            return true;
        }
        else if (al.get(origin-7).getText().equals("O") && (al.get(origin-7).getForeground().equals(Color.black))){
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
        else if (al.get(origin-5).getText().equals("O") && (al.get(origin-5).getForeground().equals(Color.black))){
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
        if (aiFirst == true){
            AlphaBeta ab = new AlphaBeta();
            ab.alphaBetaSearch();
        }
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
                else if(holdingPiece == true && !legalMove(jb)){
                    holder = null;
                    holdingPiece = false;
                }
            }
        }
    }
}
        




