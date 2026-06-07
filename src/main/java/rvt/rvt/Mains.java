package rvt;

public class Mains {
    public static void main(String[] args) {
        // 1. un 2. daļas pārbaude
        Box bigBox = new Box(20);
        
        bigBox.add(new Book("Fyodor Dostoevsky", "Crime and Punishment", 2));
        bigBox.add(new CD("Pink Floyd", "Dark Side of the Moon", 1973));
        
        // 4. daļas pārbaude: Kaste kastē
        Box smallBox = new Box(5);
        smallBox.add(new Book("Robert Martin", "Clean Code", 1));
        
        bigBox.add(smallBox); // Pievienojam mazo kasti lielajā

        System.out.println(bigBox);
    }
}