package rvt;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Data  {

    public static void main(String[] args) {
        
        try (Scanner reader = new Scanner(new File("data.csv"))) {
            
            reader.nextLine(); // izlaižam header rindu
            
            while (reader.hasNextLine()) {
                String rinda = reader.nextLine();
                System.out.println(rinda); // izdrukā katru rindu
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
