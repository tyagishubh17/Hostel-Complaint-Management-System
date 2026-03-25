package hcms.manager;

import hcms.model.Complaint;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ComplaintManager {
    private static final String FILE_NAME = "complaints.txt";
    private static final String DELETED_DIR = "deleted_complaints";
    private List<Complaint> complaints;
    private int nextId;

    public ComplaintManager() {
        complaints = new ArrayList<>();
        nextId = 1;
        cleanupDeletedComplaints();
        loadComplaints();
    }

    public String generateId() {
        return "C" + String.format("%03d", nextId++);
    }

    public void addComplaint(String studentName, String issue, String priority) {
        String id = generateId();
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Complaint newComplaint = new Complaint(id, studentName, issue, "Pending", priority, timestamp);
        complaints.add(newComplaint);
        saveComplaints();
        System.out.println("Complaint registered successfully! Your ID is: " + id);
    }

    public void viewAllComplaints() {
        if (complaints.isEmpty()) {
            System.out.println("No complaints found in the system.");
            return;
        }
        for (Complaint c : complaints) {
            System.out.println("--------------------------------------------------");
            System.out.println(c);
        }
        System.out.println("--------------------------------------------------");
    }

    public void viewStudentComplaints(String studentName) {
        boolean found = false;
        for (Complaint c : complaints) {
            if (c.getStudentName().equals(studentName)) {
                System.out.println("--------------------------------------------------");
                System.out.println(c);
                found = true;
            }
        }
        if (found) {
            System.out.println("--------------------------------------------------");
        } else {
            System.out.println("You have no registered complaints.");
        }
    }

    public boolean updateStatus(String id, String newStatus) {
        Complaint complaint = findComplaintById(id);
        if(complaint != null) {
            complaint.setStatus(newStatus);
            saveComplaints();
            return true;
        }
        return false;
    }

    public boolean deleteComplaint(String id) {
        Complaint complaint = findComplaintById(id);
        if(complaint != null) {
            moveToDeleteFolder(complaint);
            complaints.remove(complaint);
            saveComplaints();
            return true;
        }
        return false;
    }
    
    public Complaint findComplaintById(String id) {
        for(Complaint c : complaints) {
            if(c.getId().equalsIgnoreCase(id)) return c;
        }
        return null;
    }

    private void moveToDeleteFolder(Complaint c) {
        File dir = new File(DELETED_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(dir, c.getId() + ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("Deleted On: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n");
            writer.write("--------------------------------------------------\n");
            writer.write(c.toString() + "\n");
        } catch (IOException e) {
            System.err.println("Error saving to deleted folder: " + e.getMessage());
        }
    }

    private void cleanupDeletedComplaints() {
        File dir = new File(DELETED_DIR);
        if (!dir.exists()) return;

        long sevenDaysMs = 7L * 24 * 60 * 60 * 1000L;
        long now = System.currentTimeMillis();

        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (now - file.lastModified() > sevenDaysMs) {
                    file.delete();
                }
            }
        }
    }

    private void loadComplaints() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int maxId = 0;
            while ((line = reader.readLine()) != null) {
                Complaint c = Complaint.fromFileFormat(line);
                if (c != null) {
                    complaints.add(c);
                    // Calculate nextId based on loaded complaints
                    try {
                        int num = Integer.parseInt(c.getId().substring(1));
                        if(num > maxId) maxId = num;
                    } catch (NumberFormatException ignored) {}
                }
            }
            nextId = maxId + 1;
        } catch (IOException e) {
            System.err.println("Error loading complaints: " + e.getMessage());
        }
    }

    private void saveComplaints() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Complaint c : complaints) {
                writer.write(c.toFileFormat());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving complaints: " + e.getMessage());
        }
    }
}
