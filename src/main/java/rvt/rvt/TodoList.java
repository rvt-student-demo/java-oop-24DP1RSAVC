package rvt;

import java.util.List;
import java.util.Scanner;

public class TodoList {
    private TodoDB db;

    public TodoList() {
        this.db = new TodoDB();
    }

    public void add(String task) {
        db.add(task);
    }

    public void print() {
        List<String> tasks = db.findAll();
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ": " + tasks.get(i));
        }
    }

    public void remove(int number) {
        db.removeByIndex(number);
    }

    public String complete(int number) {
        List<String> tasks = db.findAll();
        if (number < 1 || number > tasks.size()) {
            return null;
        }
        String task = tasks.get(number - 1);
        db.removeByIndex(number);
        return task;
    }

    public static void main(String[] args) {
        TodoList list = new TodoList();
        UserInterface ui = new UserInterface(list, new Scanner(System.in));
        ui.start();
    }
}
