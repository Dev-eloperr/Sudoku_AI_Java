package com.company;

import javax.swing.*;
import java.awt.*;

class guiController extends JFrame implements Runnable{
/*
This class handle GUI related functions and also allows GUI refresh whenever any move is taken by AI
 */
    private JPanel grid = new JPanel(new GridLayout(9, 9)); //Grid layout
    private JPanel buttons = new JPanel(new GridLayout(2, 3));
    private JTextField[][] fieldArray = new JTextField[9][9]; //array of fields for easy extraction

    sudokuInitializer sud = new sudokuInitializer(); //initializes array from file
    private int flag;
    private Runnable callSolver;

    public void run() {
        flag = chooseInputStream(); //choose input method
        guiInit();//Initializes GUI
        //sud.printArray();
        setVisible(true);
    }
/*
Function to display GUI for the user to choose input method method
File or Manual
 */
    private int chooseInputStream() {
        String[] options = new String[]{"File", "Manual"};
        int response = JOptionPane.showOptionDialog(null, "Choose one way", "",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        if (response == 0) {
            try {
                sud.fileInput();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"Critical Error");
            }
        }
        return response;
    }
/*
Initializes GUI with the given content from the array
 */
    private void guiInit() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                fieldArray[i][j] = new JTextField(1);
                if (Main.sudoku_array[i][j] != 0) {
                    fieldArray[i][j].setText(Main.sudoku_array[i][j] + "");
                    fieldArray[i][j].setEditable(false);
                    fieldArray[i][j].setBackground(Color.lightGray);
                } else
                    fieldArray[i][j].setText("");
                fieldArray[i][j].setFont(new Font("Arial", Font.ITALIC, 24));
                fieldArray[i][j].setHorizontalAlignment(JTextField.CENTER);

                if (j == 2 || j == 5)
                    fieldArray[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 3, Color.BLACK));
                else
                    fieldArray[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));

                if (i == 2 || i == 5) {
                    if (j == 2 || j == 5)
                        fieldArray[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.BLACK));
                    else
                        fieldArray[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 3, 1, Color.BLACK));
                }
                grid.add(fieldArray[i][j]);
            }

        }
        add(grid);

        JLabel timer_delay = new JLabel("Set step Delay(in ms)");
        JTextField delay_input = new JTextField("5");
        timer_delay.setHorizontalAlignment(SwingConstants.CENTER);
        delay_input.setHorizontalAlignment(SwingConstants.CENTER);
        delay_input.setFont(new Font("Arial", Font.ITALIC, 28));
        //delay_input.setPreferredSize(new Dimension(225,30));
        buttons.add(timer_delay);
        buttons.add(delay_input);

        JButton solve = new JButton("Solve");//Button to compute
        JButton Clear = new JButton("Clear");//Button to compute
        JButton CheckSol = new JButton("Checksol"); //Button to check

        CheckSol.setBorder(null);
        CheckSol.setBackground(Color.white);
        buttons.add(CheckSol);

        solve.setBorder(null);
        solve.setBackground(Color.white);
        buttons.add(solve);
        buttons.setBackground(Color.white);
        add(buttons, BorderLayout.SOUTH);
        solve.addActionListener(e -> { //If the button is pressed
            Main.checkSol = false;
            if (flag != 0) {
                try {
                    getText(true); //takes in text from the GUI
                    callSolver.run();


                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,"Invalid Input");//Throws error if the input is invalid
                    clearGUI();
                    //flag=-1;
                }
            }else {
                try {
                    callSolver.run();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,"Invalid Input");//Throws error if the input is invalid
                    clearGUI();
                    //flag=-1;
                }
            }

        });

        CheckSol.addActionListener(e -> { //If the button is pressed
            Main.checkSol = true;
            if (flag != 0) {
                try {
                    getText(false); //takes in text from the GUI
                    callSolver.run();


                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,"Invalid Input");//Throws error if the input is invalid
                    clearGUI();
                    //flag=-1;
                }
            }else {
                try {
                    getText(false);
                    callSolver.run();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,"Invalid Input");//Throws error if the input is invalid
                    clearGUI();
                    //flag=-1;
                }
            }

        });

        Clear.setBorder(null);
        Clear.setBackground(Color.white);
        buttons.add(Clear);
        Clear.addActionListener(e -> { //If the button is pressed
            clearGUI();


        });


        buttons.setPreferredSize(new Dimension(450,100));
        //Basic Frame parameters are set below this
        setSize(450, 550);
        //setVisible(true);//done in Main function
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        if (flag==0)
            Clear.setEnabled(false);

        /*
        This lambda expression calls solver Class and passes the array
         */
        callSolver = () -> {
            sud.printArray(); //debugging purpose

            solver solver = new solver(Integer.parseInt(delay_input.getText()));
            Thread t2 = new Thread(solver);
            t2.start();
            System.out.println("Solving...");
            //Clear.setEnabled(false);
            //solve.setEnabled(false);//cell value becomes fixed
        };
    }

    /*
    \Function below clears the GUI
    sets the array to 0
    and textfields to null
     */
    private void clearGUI(){
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++) {
                Main.sudoku_array[i][j] = 0;
                fieldArray[i][j].setEditable(true);
                fieldArray[i][j].setText("");
            }
        Main.points.clear();
        refreshGUI();
    }
/*
takes in valid input and sets the GUI cells as non editable
 */
    private void getText(boolean editable) throws Exception{
        String temp;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                    temp = fieldArray[i][j].getText();
                    if (!temp.equals("")) {
                        if(sud.checkValidInput(temp+""))
                            Main.sudoku_array[i][j] = Integer.parseInt(temp + "");//checks valid input, catches exception
                        if (editable) {
                            fieldArray[i][j].setEditable(false);
                            fieldArray[i][j].setBackground(Color.lightGray);
                        }
                    }else {
                        Main.points.add(new Point(i, j));
                        Main.sudoku_array[i][j] = 0;
                    }
            }
        }
        sud.printPoints();
    }
/*
Refreshes the GUI whenever AI makes some changes
 */
    protected void refreshGUI(){
        for (int i=0; i<Main.points.size();i++)
            if (Main.sudoku_array[(int)Main.points.get(i).getX()][(int)Main.points.get(i).getY()]!=0)
                fieldArray[(int)Main.points.get(i).getX()][(int)Main.points.get(i).getY()].setText(Main.sudoku_array[(int)Main.points.get(i).getX()][(int)Main.points.get(i).getY()]+"");
            else
                fieldArray[(int)Main.points.get(i).getX()][(int)Main.points.get(i).getY()].setText("");
    }
    protected void errorCell(int i, int j){
        for(int k=0,x=0,y=0;k<=Math.max(i,j);k++) {
            if (x<i)
                x++;
            if (y<j)
                y++;
            fieldArray[x][j].setBackground(Color.red);
            fieldArray[i][y].setBackground(Color.red);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (Main.points.contains(new Point(x,j)))
                fieldArray[x][j].setBackground(Color.white);
            else
                fieldArray[x][j].setBackground(Color.lightGray);
            if (Main.points.contains(new Point(i,y)))
                fieldArray[i][y].setBackground(Color.white);
            else
                fieldArray[i][y].setBackground(Color.lightGray);

        }
    }


}
