package rvt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TodoDB {
    private static final String DB_URL = "jdbc:sqlite:todo.db";

    public TodoDB() {
        initSchema();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    private void initSchema() {
        String sql = "CREATE TABLE IF NOT EXISTS tasks ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "task TEXT NOT NULL"
                + ")";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Schema init failed: " + e.getMessage());
        }
    }

    public void add(String task) {
        String sql = "INSERT INTO tasks(task) VALUES(?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, task);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Add failed: " + e.getMessage());
        }
    }

    public List<String> findAll() {
        List<String> tasks = new ArrayList<>();
        String sql = "SELECT task FROM tasks ORDER BY id";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                tasks.add(rs.getString("task"));
            }
        } catch (SQLException e) {
            System.err.println("FindAll failed: " + e.getMessage());
        }
        return tasks;
    }

    public void removeByIndex(int oneBased) {
        String selectSql = "SELECT id FROM tasks ORDER BY id LIMIT 1 OFFSET ?";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(selectSql)) {
            ps.setInt(1, oneBased - 1);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                try (PreparedStatement del = conn.prepareStatement("DELETE FROM tasks WHERE id = ?")) {
                    del.setInt(1, id);
                    del.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.err.println("Remove failed: " + e.getMessage());
        }
    }
}
