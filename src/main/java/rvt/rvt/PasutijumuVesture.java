package rvt;

import java.io.*;
import java.util.*;

public class PasutijumuVesture {

    public static void main(String[] args) {

        String failaNosaukums = "orders.csv";
        double kopaSumma = 0.0;
        int pasutijumaNumurs = 1;

        // ==================== AUTOMĀTISKA FAILA IZVEIDE ====================
        File fails = new File(failaNosaukums);
        if (!fails.exists()) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(fails))) {
                pw.println("OrderID,Customer,Product,Quantity,Price");
                pw.println("1,Anna,Basketball,2,25.50");
                pw.println("2,Jānis,Shoes,1,59.99");
                pw.println("3,Līga,Jersey,3,19.99");
                System.out.println("✅ Izveidots fails: orders.csv");
            } catch (IOException e) {
                System.err.println("Neizdevās izveidot failu!");
                return;
            }
        }
        // =================================================================

        System.out.println("=== Pasūtījumu vēsture ===\n");

        try (BufferedReader br = new BufferedReader(new FileReader(failaNosaukums))) {
            
            String rinda;
            boolean pirmaRinda = true;

            while ((rinda = br.readLine()) != null) {
                if (pirmaRinda) {
                    pirmaRinda = false;
                    continue;
                }

                String[] dati = rinda.split(",");

                if (dati.length != 5) continue;

                String customer = dati[1].trim();
                String product  = dati[2].trim();
                int quantity    = Integer.parseInt(dati[3].trim());
                double price    = Double.parseDouble(dati[4].trim());

                double kopejaCena = quantity * price;
                kopaSumma += kopejaCena;

                // Manuāli rakstīti vārdi
                System.out.print("Pasūtījums #" + pasutijumaNumurs + ": ");
                System.out.print(customer + " pasūtīja ");
                System.out.print(quantity + " x " + product + " (");
                System.out.printf("%.2f", price);
                System.out.print(" EUR) -> Kopā: ");
                System.out.printf("%.2f", kopejaCena);
                System.out.println(" EUR");

                pasutijumaNumurs++;
            }

            System.out.print("\nKopējā pasūtījumu summa: ");
            System.out.printf("%.2f", kopaSumma);
            System.out.println(" EUR");

        } catch (Exception e) {
            System.err.println("Kļūda lasot failu: " + e.getMessage());
        }
    }
}