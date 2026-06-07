package rvt;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Student> students;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        students = FileHandler.loadStudents();

        while (true) {
            System.out.println("\nKomandas: register | show | remove | exit");
            System.out.print("> ");
            String cmd = sc.nextLine().toLowerCase().trim();

            if (cmd.equals("register")) register();
            else if (cmd.equals("show")) TableRenderer.printTable(students);
            else if (cmd.equals("remove")) remove();
            else if (cmd.equals("exit")) break;
            else System.out.println("Nezinama komanda.");
        }
    }

    private static void register() {
        System.out.print("Vards: "); 
        String v = sc.nextLine().trim();
        if (!Validator.isValidVards(v)) { System.out.println("Kluda varda!"); return; }

        System.out.print("Uzvards: "); 
        String u = sc.nextLine().trim();
        
        System.out.print("E-pasts: "); 
        String e = sc.nextLine().trim();
        if (!Validator.isValidEmail(e)) { System.out.println("Kluda e-pasta!"); return; }

        System.out.print("Personas kods (XXXXXX-XXXXX): "); 
        String pk = sc.nextLine().trim();
        if (!Validator.isValidPK(pk)) { System.out.println("Kluda personas koda!"); return; }

        // Parbaude vai personas kods jau eksiste
        for (Student s : students) {
            if (s.getPersonasKods().equals(pk)) {
                System.out.println("Students ar sadu PK jau eksiste!");
                return;
            }
        }

        String laiks = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        students.add(new Student(v, u, e, pk, laiks));
        FileHandler.saveStudents(students);
        System.out.println("Registrets veiksmigi!");
    }

    private static void remove() {
        System.out.print("Ievadi PK dzesanai: ");
        String pk = sc.nextLine().trim();
        boolean removed = students.removeIf(s -> s.getPersonasKods().equals(pk));
        
        if (removed) {
            FileHandler.saveStudents(students);
            System.out.println("Izdzests veiksmigi.");
        } else {
            System.out.println("Lietotajs ar sadu PK nav atrasts.");
        }
    }
}