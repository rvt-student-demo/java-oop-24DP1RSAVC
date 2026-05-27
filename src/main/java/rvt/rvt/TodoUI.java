package rvt;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

// Composition
public class TodoUI {
    private JFrame window;
    private JPanel panel; // Pievienojam Paneli

    private DefaultListModel<String> listModel;
    private JList<String>            taskList;
    private JTextField               inputField;
    private JLabel                   statusLabel;
    private TodoList                 todoList;

    public TodoUI(TodoList todoList) {
        this.todoList = todoList;
        initialize();
    }

    private void initialize() {
        window = new JFrame("Todo App");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(520, 600);
        window.setResizable(false);
        window.setLocationRelativeTo(null);

        // Inicializējam paneli
        panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBackground(new Color(245, 245, 250));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        window.add(panel);

        buildUI();
    }

    private void buildUI() {
        // ── Title ──────────────────────────────────────────────────────────
        JLabel titleLabel = new JLabel("\uD83D\uDCDD Todo List");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        titleLabel.setForeground(new Color(50, 50, 80));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        panel.add(titleLabel, BorderLayout.NORTH);

        // ── Task list ───────────────────────────────────────────────────────
        listModel = new DefaultListModel<>();
        taskList  = new JList<>(listModel);
        taskList.setFont(new Font("SansSerif", Font.PLAIN, 16));
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.setBackground(Color.WHITE);
        taskList.setForeground(new Color(40, 40, 60));
        taskList.setFixedCellHeight(40);
        taskList.setBorder(new EmptyBorder(4, 10, 4, 10));

        // Numbered rows + zebra stripes
        taskList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                JLabel lbl = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                lbl.setText("  " + (index + 1) + ".  " + value);
                if (!isSelected) {
                    lbl.setBackground(index % 2 == 0
                            ? Color.WHITE : new Color(235, 235, 245));
                }
                return lbl;
            }
        });

        JScrollPane scrollPane = new JScrollPane(taskList);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 215), 1));
        panel.add(scrollPane, BorderLayout.CENTER);

        // ── Bottom area ─────────────────────────────────────────────────────
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(new Color(245, 245, 250));

        // Input row
        JPanel inputRow = new JPanel(new BorderLayout(8, 0));
        inputRow.setBackground(new Color(245, 245, 250));
        inputRow.setBorder(new EmptyBorder(10, 0, 6, 0));

        inputField = new JTextField();
        inputField.setFont(new Font("SansSerif", Font.PLAIN, 15));
        inputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 210)),
                new EmptyBorder(6, 10, 6, 10)));
        inputField.addActionListener(e -> addTask()); // Enter = add

        JButton addButton = makeButton("Add", new Color(70, 130, 180));
        addButton.addActionListener(e -> addTask());

        inputRow.add(inputField,  BorderLayout.CENTER);
        inputRow.add(addButton,   BorderLayout.EAST);
        bottomPanel.add(inputRow);

        // Remove / Complete buttons
        JPanel buttonRow = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonRow.setBackground(new Color(245, 245, 250));
        buttonRow.setBorder(new EmptyBorder(0, 0, 10, 0));

        JButton removeButton = makeButton("Remove selected",  new Color(200, 80,  80));
        JButton completeButton = makeButton("Mark completed", new Color(80,  160, 100));
        removeButton.addActionListener(e  -> removeTask());
        completeButton.addActionListener(e -> completeTask());

        buttonRow.add(removeButton);
        buttonRow.add(completeButton);
        bottomPanel.add(buttonRow);

        // Status label
        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("SansSerif", Font.ITALIC, 13));
        statusLabel.setForeground(new Color(120, 120, 150));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.add(statusLabel);

        panel.add(bottomPanel, BorderLayout.SOUTH);
    }

    // ── Actions ──────────────────────────────────────────────────────────────

    private void addTask() {
        String text = inputField.getText().trim();
        if (text.isEmpty()) { setStatus("Please enter a task name.", false); return; }
        todoList.add(text);
        listModel.addElement(text);
        inputField.setText("");
        inputField.requestFocus();
        setStatus("Added: \"" + text + "\"", true);
    }

    private void removeTask() {
        int index = taskList.getSelectedIndex();
        if (index < 0) { setStatus("Select a task to remove.", false); return; }
        String removed = listModel.getElementAt(index);
        todoList.remove(index + 1);   // 1-based
        listModel.remove(index);
        setStatus("Removed: \"" + removed + "\"", true);
    }

    private void completeTask() {
        int index = taskList.getSelectedIndex();
        if (index < 0) { setStatus("Select a task to mark as completed.", false); return; }
        String task = todoList.complete(index + 1);  // 1-based
        listModel.remove(index);
        setStatus("Completed: \"" + task + "\" \u2713", true);
    }

    private void setStatus(String msg, boolean ok) {
        statusLabel.setForeground(ok ? new Color(60, 140, 80) : new Color(180, 60, 60));
        statusLabel.setText(msg);
    }

    // ── Helper ───────────────────────────────────────────────────────────────

    private JButton makeButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(10, 18, 10, 18));
        return btn;
    }

    public void show() {
        window.setVisible(true);
    }
}
