package ui;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

// NOTE: borrows code from the JsonSerializationDemo
public class Main {
    public static void main(String[] args) {

        // EFFECTS: runs console program
//        try {
//            new MovieListApp();
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to run application: file not found");
//        }

        // EFFECTS: runs GUI program
        new MovieListGUI();
    }
}
