package rvt;

import java.util.List;

public class TableRenderer {
    public static void printTable(List<Student> students) {
        String hr = "+------------+------------+---------------------------+--------------+---------------------+";
        System.out.println(hr);
        System.out.format("| %-10s | %-10s | %-25s | %-12s | %-19s |\n", "Vards", "Uzvards", "E-pasts", "Pers. kods", "Datums");
        System.out.println(hr);

        for (Student s : students) {
            String[] d = s.toArray();
            System.out.format("| %-10s | %-10s | %-25s | %-12s | %-19s |\n", d[0], d[1], d[2], d[3], d[4]);
        }
        System.out.println(hr);
    }
}