package hcms.manager;

import hcms.model.Admin;
import hcms.model.Student;
import hcms.model.User;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private static final String FILE_NAME = "users.txt";
    // Using a map for quick lookup: username -> password
    private Map<String, String> students;
    private Admin admin;

    public UserManager() {
        students = new HashMap<>();
        // Hardcoded admin
        admin = new Admin("admin", "admin123");
        loadUsers();
    }

    public boolean registerStudent(String username, String password) {
        if (students.containsKey(username) || admin.getUsername().equals(username)) {
            return false; // Username already exists
        }
        students.put(username, password);
        saveUsers();
        return true;
    }

    public User login(String username, String password) {
        if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
            return admin;
        }
        if (students.containsKey(username) && students.get(username).equals(password)) {
            return new Student(username, password);
        }
        return null;
    }

    private void loadUsers() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    students.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
    }

    private void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Map.Entry<String, String> entry : students.entrySet()) {
                writer.write(entry.getKey() + "|" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }
}
