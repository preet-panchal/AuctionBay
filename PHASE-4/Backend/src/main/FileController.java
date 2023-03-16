package main;

import java.io.*;
import java.util.Scanner; // Import the Scanner class to read text files

public class FileController {

    public String filename;

    FileController() {
    }

    public void load(String filename) {
        try {
            File file = new File(filename);
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while opening the file.");
            e.printStackTrace();
        }
    }
}

