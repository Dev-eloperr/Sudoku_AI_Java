package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

class guiController extends JFrame {
/*
This class handle GUI related functions and also allows GUI refresh whenever any move is taken by AI
 */
    private JPanel grid = new JPanel(new GridLayout(9, 9)); //Grid layout
    private JPanel buttons = new JPanel(new GridLayout(2, 1));
    private JTextField[][] fieldArray = new JTextField[9][9]; //array of fields for easy extraction

    private sudokuInitializer sud = new sudokuInitializer(); //initializes array from file
    private int flag;

    guiController() {
        flag = chooseInputStream(); //choose input method
        guiInit();//Initializes GUI
        //sud.printArray();
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
        System.out.println(response);
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
        JButton solve = new JButton("Solve");//Button to compute
        solve.setBorder(null);
        solve.setBackground(Color.white);
        buttons.add(solve);
        buttons.add(new JLabel());
        buttons.setBackground(Color.white);
        add(buttons, BorderLayout.SOUTH);
        solve.addActionListener(e -> { //If the button is pressed
            if (flag != 0) {
                try {
                    getText(); //takes in text from the GUI
                    sud.printArray(); //debugging purpose
                    solve.setEnabled(false);//cell value becomes fixed
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,"Invalid Input");//Throws error if the input is invalid
                }
            }
        });
        //Basic Frame parameters are set below this
        setSize(400, 500);
        //setVisible(true);//done in Main function
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
/*
takes in valid input and sets the GUI cells as non editable
 */
    private void getText() throws Exception{
        String temp;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                    temp = fieldArray[i][j].getText();
                    if (!temp.equals("")) {
                            Main.sudoku_array[i][j] = sud.checkValidInput(temp); //throws exception
                            fieldArray[i][j].setEditable(false);
                    }
            }
        }
    }
/*
Refreshes the GUI whenever AI makes some changes
 */
    void refreshGUI(){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (Main.sudoku_array[i][j] != 0) {
                    fieldArray[i][j].setText(Main.sudoku_array[i][j]+"");
                }
            }
        }
    }

}
