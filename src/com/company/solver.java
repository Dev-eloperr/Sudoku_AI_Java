package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

class solver implements Runnable{ //Implemented as a Thread to avoid freezing and maximum efficiency for GUI with heavy operation
    //Chooses a box with MINIMUM VALUES REMAINING (It acts as a heuristic which optimizes the search for the solution
    ArrayList<Point> sortedPoints = new ArrayList<>(); //It stores the sorted points according
                                                // to box with least number of values unfilled
    public void run(){//acts as a constructor for the class, works with Thread.start function

        setSortedPoints();          //sets sorted point
        //new addition

        if (Main.checkSol) {
            int checkSolved=0,temp,i,j, err_i=0,err_j=0;

            for (Point pointIndex : sortedPoints) {
                if (Main.sudoku_array[(int) pointIndex.getX()][(int) pointIndex.getY()] != 0) {
                    i = (int) pointIndex.getX();
                    j = (int) pointIndex.getY();

                    temp=Main.sudoku_array[i][j];
                    Main.sudoku_array[i][j]=0;
                    if (temp!=0 && !checkConstraint(temp, i, j)) {
                        err_i=i;
                        err_j=j;
                        checkSolved = 1;
                        Main.sudoku_array[i][j]=temp;
                        break;
                    }
                    Main.sudoku_array[i][j]=temp;

                }
            }
            if (checkSolved==0){
                JOptionPane.showMessageDialog(null,"correct");
            }else {
                //System.out.println(err_i+" "+i+" "+err_j+" "+j);
                Main.gui.errorCell(err_i,err_j);
                //JOptionPane.showMessageDialog(null,"Wrong");
            }
        }
        else {

            if (getResult()) {      //calls the solving function
                Main.gui.sud.printArray();
                System.out.println("Count of backtracking: " + count_iteration);
            }
        }

    }
    private int delay_time=5;
    solver(int time){
        delay_time=time;
    }
    /*
    This function creates a map for all the 9 boxes
    Sorts them, and creates a new arraylist of points which are sorted according to the boxes
     */
    private void setSortedPoints(){
        HashMap<String,Integer> box_size= new HashMap<>(); //creates a map with default frequency one
        box_size.put("00",0);
        box_size.put("03",0);
        box_size.put("06",0);

        box_size.put("30",0);
        box_size.put("33",0);
        box_size.put("36",0);

        box_size.put("60",0);
        box_size.put("63",0);
        box_size.put("66",0);
        /*
        Below loop, calculates the size of each of unfilled elements in each box
         */
        for (int i=0; i<Main.points.size(); i++) {
                String s = Integer.toString(boxRowCol((int)Main.points.get(i).getX()));
                s+=Integer.toString(+boxRowCol((int)Main.points.get(i).getY()));
                //System.out.println(s);

                switch (s) {
                    case "00":box_size.put("00",box_size.get("00")+1); break;
                    case "03":box_size.put("03",box_size.get("03")+1);break;
                    case "06":box_size.put("06",box_size.get("06")+1);break;

                    case "30":box_size.put("30",box_size.get("30")+1);break;
                    case "33":box_size.put("33",box_size.get("33")+1);break;
                    case "36":box_size.put("36",box_size.get("36")+1);break;

                    case "60":box_size.put("60",box_size.get("60")+1);break;
                    case "63":box_size.put("63",box_size.get("63")+1);break;
                    case "66":box_size.put("66",box_size.get("66")+1);break;
                }
        }

        box_size = sortByValue(box_size); //This function sorts the hash map by value

        /*
        Below loop sets the sortedpoints arraylist
         */
        for (String key:box_size.keySet())
            for (int i=0; i<Main.points.size(); i++) {
                String s = Integer.toString(boxRowCol((int) Main.points.get(i).getX()));
                s += Integer.toString(+boxRowCol((int) Main.points.get(i).getY()));
                if (s.equals(key)){
                    sortedPoints.add(new Point((int)Main.points.get(i).getX(),(int)Main.points.get(i).getY()));
                }

            }
    }
/*
This function is soleley used for sorting the MAP by using list and default collections fucntions
 */
    private static HashMap<String, Integer> sortByValue(HashMap<String , Integer> map_sorting)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list =
                new LinkedList<>(map_sorting.entrySet());


        list.sort(Comparator.comparing(Map.Entry::getValue));


        HashMap<String, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }


    /*
    This is the MAIN function which solves the sudoku by using sortedpoints arraylist and constraints
     */
    int count_iteration=0;
    private boolean getResult(){
        if (checkSolved()) {
            return true;
        }
        int row_num = -1,col_num = -1;
        /*
        sets col, row to get started
         */
        for (Point pointIndex : sortedPoints) {
            if (Main.sudoku_array[(int) pointIndex.getX()][(int) pointIndex.getY()] == 0) {
                row_num = (int) pointIndex.getX();
                col_num = (int) pointIndex.getY();
                break;
            }
        }

        /*
        it check for constraint and refreshes GUI after every move
         */
        for (int i=1; i<=9; i++){
            if (checkConstraint(i,row_num,col_num)){

                Main.sudoku_array[row_num][col_num] = i;

                //Main.gui.sud.printArray();

                Main.gui.refreshGUI();
                timer();
                if (getResult()){
                    //count_iteration++;
                    return true;
                }
                else{
                    Main.sudoku_array[row_num][col_num] = 0;
                    count_iteration++;
                    //Main.gui.sud.printArray();
                    Main.gui.refreshGUI();
                    timer();
                }
            }
        }
        return false;

    }
    /*
    adds delay
     */
    private void timer(){
        try {
            Thread.sleep(delay_time); //CHANGE DELAY TIME HERE , SET minimum 5ms for noticable difference and faster result
            //Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /*
    It checks weather the sudoku has been solved or not
     */
    private boolean checkSolved(){
        for (int i = 0 ; i < 9 ; i++)
            for (int j = 0 ; j < 9 ; j++)
                if (Main.sudoku_array[i][j] == 0)
                    return false;
        return true;
    }
    /*
    it checks constraint on rows, cols and boxes
     */
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
    /*
    Returns box number
     */
    private int boxRowCol(int num){
        if (num==0||num==1||num==2)
            return 0;
        else if (num==3||num==4||num==5)
            return 3;
        else
            return 6;
    }
}
