package rvt;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TodoList {
    private List<String> tasks;

    public TodoList() {
        this.tasks = new ArrayList<>();
    }

    public void add(String task) {
        this.tasks.add(task);
    }

    public void print() {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ": " + tasks.get(i));
        }
    }

    public void remove(int number) {
        // number corresponds to printed index (1-based)
        this.tasks.remove(number - 1);
    }

    public String complete(int number) {
        // remove and return the completed task text
        return this.tasks.remove(number - 1);
    }

    public static void main(String[] args) {
        TodoList list = new TodoList();
        UserInterface ui = new UserInterface(list, new Scanner(System.in));
        ui.start();
    }
}
