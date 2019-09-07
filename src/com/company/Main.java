package com.company;

import java.awt.*;
import java.util.ArrayList;

public class Main {
    /*
    SUDOKU SOLVER

    This program uses Dynamic arrays to save empty points which are later
    used to determine heuristics which are minimum value remaining box;

    Multithreading has also been used for efficiency and anti freezing of GUI

    *Description of various classes used*

    1) Main
        Initializes static array of sudoku, points etc, intializes GUI as well
    2) Solver
        This Class has the algorithm to solve sudoku, It aslo refreshes GUI after every step
        (Timer delay could be edited in this class)
    3) guiController
        Initializes GUI and provides various functions to retrieve/display/refresh the GUI
    4) sudokuInitializer
        This class allows input through Files
        It also has a commented function which could take array values via console
        (used only for debugging purpose)

     */
    static protected int[][] sudoku_array = new int[9][9]; //Stores the sudoku
    static protected ArrayList<Point> points = new ArrayList<>(); //stores the EMPTY points
    static protected guiController gui = new guiController();//Initializes program, calls sudokuInitializer as well


    public static void main(String[] args) {

        Thread t1 = new Thread(gui);
        t1.start();
    }
}
