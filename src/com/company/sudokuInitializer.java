package com.company;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

class sudokuInitializer {
    void printArray(){
        System.out.println("Printing...");
        for (int i=0;i<9;i++) {
            System.out.println();
            for (int j = 0; j < 9; j++)
                System.out.print(Main.sudoku_array[i][j] + " ");
        }
    }
    void fileInput() throws IOException {
        System.out.println("opening Jfilechooser");
        //JFrame f = new JFrame("Choose a file");
        JFileChooser fileChooser = new JFileChooser("D:\\Windows\\Documents");
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            BufferedReader br = new BufferedReader(new FileReader(selectedFile));

            StringBuilder st = new StringBuilder();
            String stt;
            int i=0;
            while (((stt = br.readLine())) != null){
                st.insert(0,stt);
                for (int j=0;j<st.length();j++){
                        Main.sudoku_array[i][j] = Integer.parseInt(st.charAt(j)+"");
                    //System.out.print(i+","+j+":"+Main.sudoku_array[i][j]+"  ");
                }
                i++;
                //System.out.println();
                st.delete(0,st.length());
            }
        }
    }
    /*

    //NOTE : Class only to be used in catastrophic situations only
    void manualInput(){
        String[] str = new String[9];
        Scanner input = new Scanner(System.in);
        int i=0;

        StringBuilder st = new StringBuilder();
        for (int k = 0; k < 9 ; k++){
            str[k] = input.nextLine();
            st.insert(0,str[k]);
            for (int j=0;j<st.length();j++){
                Main.sudoku_array[i][j] = Integer.parseInt(st.charAt(j)+"");
            }
            i++;
            st.delete(0,st.length());
        }
    }

    */
}
