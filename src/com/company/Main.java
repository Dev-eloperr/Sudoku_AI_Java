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
