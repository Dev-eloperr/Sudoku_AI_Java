package com.company;

import java.awt.*;
import java.util.ArrayList;

public class Main {

    static protected int[][] sudoku_array = new int[9][9]; //Stores the sudoku
    static protected ArrayList<Point> points = new ArrayList<>(); //stores the EMPTY points
    static protected guiController gui = new guiController();//Initializes program, calls sudokuInitializer as well
    public static void main(String[] args) {

        //gui.setVisible(true);//sets the GUI as visible
        System.out.println("points : "+ points);

        Thread t1 = new Thread(gui);
        t1.start();
    }
}
class solver implements Runnable{
    //Choose a box with minimum empty values - MVP(minimum value remaining) heuristic
    //Reduce the domain of each box by prechecking the already fixed value in the box

    public void run(){
        if(getResult())
            Main.gui.sud.printArray();

    }
    private boolean getResult(){
        if (checkSolved()) {
            return true;
        }
        int row_num = -1;
        int col_num = -1;
        for (int i=0; i<9; i++)
            for (int j=0; j<9; j++){
                if (Main.sudoku_array[i][j] == 0){
                    row_num=i;
                    col_num=j;
                }
            }

        for (int i=1; i<=9; i++){
            if (checkConstraint(i,row_num,col_num)){

                Main.sudoku_array[row_num][col_num] = i;

                //Main.gui.sud.printArray();

                Main.gui.refreshGUI();
                timer();
                if (getResult()){
                    return true;
                }
                else{
                    Main.sudoku_array[row_num][col_num] = 0;

                    //Main.gui.sud.printArray();
                    Main.gui.refreshGUI();
                    timer();
                }
            }
        }
        return false;

    }
    private void timer(){
        try {
            Thread.sleep(10);
            //Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private boolean checkSolved(){
        for (int i = 0 ; i < 9 ; i++)
            for (int j = 0 ; j < 9 ; j++)
                if (Main.sudoku_array[i][j] == 0)
                    return false;
        return true;
    }
    private boolean checkConstraint(int value, int row_num,int col_num){
        /*
        Below for loop checks for the given value in row and col specified
         */
        for (int i = 0 ;i < 9 ; i++){
            if (Main.sudoku_array[row_num][i] == value ||
                    Main.sudoku_array[i][col_num] == value)
                return false;
        }
        /*
        Below code, checks the box for the given value
         */
        int boxRow_num = boxRowCol(row_num);
        int boxCol_num = boxRowCol(col_num);
        for (int i=boxRow_num; i<3+boxRow_num ;i++)
            for (int j=boxCol_num; j<3+boxCol_num ;j++){
                if (Main.sudoku_array[i][j] == value)
                    return false;
            }
        //if every constraint is satisfied
        return true;
    }
    private int boxRowCol(int num){
        if (num==0||num==1||num==2)
            return 0;
        else if (num==3||num==4||num==5)
            return 3;
        else
            return 6;
    }
}
