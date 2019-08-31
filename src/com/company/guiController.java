package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

class guiController extends JFrame {

    private JPanel grid = new JPanel(new GridLayout(9, 9));
    private JPanel buttons = new JPanel(new GridLayout(2, 1));
    private JTextField[][] fieldArray = new JTextField[9][9];

    private sudokuInitializer sud = new sudokuInitializer();
    private int flag;

    guiController() {
        flag = chooseInputStream();
        guiInit();
        //sud.printArray();
    }

    private int chooseInputStream() {
        String[] options = new String[]{"File", "Manual"};
        int response = JOptionPane.showOptionDialog(null, "Choose one way", "",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        System.out.println(response);
        if (response == 0) {
            try {
                sud.fileInput();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return response;
    }

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
        JButton solve = new JButton("Solve");
        solve.setBorder(null);
        solve.setBackground(Color.white);
        buttons.add(solve);
        buttons.add(new JLabel());
        buttons.setBackground(Color.white);
        add(buttons, BorderLayout.SOUTH);
        solve.addActionListener(e -> {
            if (flag != 0)
                getText();
            sud.printArray();
            solve.setEnabled(false);
        });

        setSize(400, 500);
        //setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void getText() {
        String temp;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                    temp = fieldArray[i][j].getText();
                    if (!temp.equals("")) {
                        Main.sudoku_array[i][j] = Integer.parseInt(temp);
                        fieldArray[i][j].setEditable(false);
                    }
            }
        }
    }

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
