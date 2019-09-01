package com.company;

import javax.swing.*;
import java.io.*;

class sudokuInitializer {
    /*
    Function to check whether the input given via file or manually is valid or not
    Thorws exception for Invalid Input
     */
    int checkValidInput(String input) throws Exception{
        int n = Integer.parseInt(input+"");
        if (n<10 && n>-1)
            return n;
        else throw new Exception();
    }
    /*
    Function to print content of the array
    Used for debugging situations
     */
    void printArray(){
        System.out.println("Printing...");
        for (int i=0;i<9;i++) {
            System.out.println();
            for (int j = 0; j < 9; j++)
                System.out.print(Main.sudoku_array[i][j] + " ");
        }
    }
    /*
    Takes input from file using Jfilechooser, and the input from the file is inserted in the array
    While inserting, it checks for valid input as well
     */
    void fileInput() throws IOException {
        JFileChooser fileChooser = new JFileChooser("D:\\Windows\\Documents");
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            BufferedReader br = new BufferedReader(new FileReader(selectedFile));//reads the file

            StringBuilder st = new StringBuilder(); //  To store Extracted string
            String stt;
            int i=0;
            while (((stt = br.readLine())) != null){ //while file has content
                st.insert(0,stt);
                for (int j=0;j<st.length();j++){
                    try {
                        Main.sudoku_array[i][j] = checkValidInput(st.charAt(j)+""); //checks valid input, catches exception
                    }catch (Exception E){
                        JOptionPane.showMessageDialog(null,"Error in extracting data from file\nExiting...");
                        System.exit(1);
                    }
                }
                i++;
                st.delete(0,st.length());
            }
        }
    }
    /*

    //NOTE : Method only to be used in catastrophic situations only, used to take in console input
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
