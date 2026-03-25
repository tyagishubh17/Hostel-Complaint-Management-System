package hcms;

import hcms.manager.ComplaintManager;
import hcms.manager.UserManager;
import hcms.model.Admin;
import hcms.model.Student;
import hcms.model.User;

import java.util.Scanner;

public class Main {
    private static UserManager userManager = new UserManager();
    private static ComplaintManager complaintManager = new ComplaintManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("     HOSTEL COMPLAINT MANAGEMENT SYSTEM (HCMS)    ");
        System.out.println("==================================================");

        boolean running = true;
        while (running) {
            System.out.println("\nMAIN MENU:");
            System.out.println("1. Student / Admin Login");
            System.out.println("2. Student Registration");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");
            
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    handleLogin();
                    break;
                case "2":
                    handleRegistration();
                    break;
                case "3":
                    System.out.println("Exiting HCMS. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void handleRegistration() {
        System.out.print("Enter new username: ");
        String username = scanner.nextLine().trim();
        if (username.isEmpty()) {
            System.out.println("Username cannot be empty.");
            return;
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();
        if (password.isEmpty()) {
            System.out.println("Password cannot be empty.");
            return;
        }

        if (userManager.registerStudent(username, password)) {
            System.out.println("Registration successful! You can now login.");
        } else {
            System.out.println("Registration failed. Username might already exist.");
        }
    }

    private static void handleLogin() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        User user = userManager.login(username, password);

        if (user == null) {
            System.out.println("Invalid credentials. Please try again.");
        } else if (user instanceof Admin) {
            System.out.println("\nLogin successful! Welcome Admin: " + user.getUsername());
            adminMenu((Admin) user);
        } else if (user instanceof Student) {
            System.out.println("\nLogin successful! Welcome Student: " + user.getUsername());
            studentMenu((Student) user);
        }
    }

    private static void studentMenu(Student student) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\nSTUDENT MENU:");
            System.out.println("1. Submit Complaint");
            System.out.println("2. View My Complaints");
            System.out.println("3. Logout");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("Enter issue description: ");
                    String issue = scanner.nextLine().trim();
                    if(issue.isEmpty()) {
                        System.out.println("Issue description cannot be empty!");
                        break;
                    }
                    System.out.print("Enter priority (Low, Medium, High): ");
                    String priority = scanner.nextLine().trim();
                    if(!priority.equalsIgnoreCase("Low") && !priority.equalsIgnoreCase("Medium") && !priority.equalsIgnoreCase("High")) {
                        priority = "Medium"; // Defaulting
                        System.out.println("Invalid priority, defaulting to Medium.");
                    }
                    complaintManager.addComplaint(student.getUsername(), issue, priority);
                    break;
                case "2":
                    System.out.println("\n--- My Complaints ---");
                    complaintManager.viewStudentComplaints(student.getUsername());
                    break;
                case "3":
                    System.out.println("Logging out...");
                    loggedIn = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void adminMenu(Admin admin) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\nADMIN MENU:");
            System.out.println("1. View All Complaints");
            System.out.println("2. Update Complaint Status");
            System.out.println("3. Delete Complaint");
            System.out.println("4. Logout");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.println("\n--- All Complaints ---");
                    complaintManager.viewAllComplaints();
                    break;
                case "2":
                    System.out.print("Enter Complaint ID to update: ");
                    String updateId = scanner.nextLine().trim();
                    System.out.println("Select New Status:");
                    System.out.println("1. Pending");
                    System.out.println("2. In Progress");
                    System.out.println("3. Resolved");
                    System.out.print("Choice: ");
                    String statusChoice = scanner.nextLine().trim();
                    String status = null;
                    if(statusChoice.equals("1")) status = "Pending";
                    else if(statusChoice.equals("2")) status = "In Progress";
                    else if(statusChoice.equals("3")) status = "Resolved";
                    
                    if(status != null) {
                        if(complaintManager.updateStatus(updateId, status)) {
                            System.out.println("Status updated successfully.");
                        } else {
                            System.out.println("Complaint ID not found.");
                        }
                    } else {
                        System.out.println("Invalid status choice.");
                    }
                    break;
                case "3":
                    System.out.print("Enter Complaint ID to delete: ");
                    String deleteId = scanner.nextLine().trim();
                    if(complaintManager.deleteComplaint(deleteId)) {
                        System.out.println("Complaint deleted successfully.");
                    } else {
                        System.out.println("Complaint ID not found.");
                    }
                    break;
                case "4":
                    System.out.println("Logging out...");
                    loggedIn = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
