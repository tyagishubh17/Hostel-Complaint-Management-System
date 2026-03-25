# HOSTEL COMPLAINT MANAGEMENT SYSTEM (HCMS)

A console-based Java application that allows hostel students to register and track complaints, and allow administrators to seamlessly manage, update, and resolve those issues. 

Built entirely using Core Java principles (Object-Oriented Programming, Collections, File I/O), the application persists all data locally without the need for external database software.

---

## 📌 Problem Statement

Hostel students regularly face issues like Broken fans, Water supply failures, or Network outages. Without a proper system, these complaints are raised verbally or informally — leading to delays, no tracking, and zero accountability. HCMS solves this by providing a structured, role-based platform for complaint submission and resolution.

---

## ✨ Features

-  **Student Registration & Login** — Secure account creation with username/password
-  **Admin Login** — Hardcoded admin credentials for controlled access
-  **Submit Complaints** — Students submit issues with priority (Low / Medium / High)
-  **Unique Complaint IDs** — Auto-generated IDs (e.g., C001, C002) for every complaint
-  **View Complaints** — Students see only their own, while Admin sees all
-  **Status Tracking** — Three states: `Pending` → `In Progress` → `Resolved`
-  **Soft Delete with 7-Day Retention** — Complaints deleted (by admin) are archived for 7 days before permanent removal, stored in txt format under <br>`deleted_complaints` folder
-  **File-Based Persistence** — All data saved to `complaints.txt` and `users.txt`

---

## 🛠️ Tech Stack

| Component        | Details                       |
|------------------|-------------------------------|
| Language         | Java (JDK 8+)                 |
| Paradigm         | Object-Oriented Programming   |
| Data Storage     | Text files (pipe-delimited)   |
| IDE              | VS Code / IntelliJ IDEA       |
| Build Tool       | Manual `javac` / IDE compiler |

---

## 🚀 Installation & Setup

### Prerequisites
- Java JDK 8 or above installed
- A terminal or IDE (VS Code / IntelliJ IDEA)

### Steps

1. **Clone or download the repository**
   ```bash
   git clone git@github.com:tyagishubh17/Hostel-Complaint-Management-System.git
   ```

2. **Compile the source files**
   ```bash
   mkdir -p out && javac -d out src/hcms/model/*.java src/hcms/manager/*.java src/hcms/Main.java
   ```

3. **Run the application**
   ```bash
   java -cp out hcms.Main
   ```

> The `complaints.txt` and `users.txt` files will be created automatically in the project root on first run.

---

## 📖 Usage Instructions

### As a Student
1. Select **Student Registration** from the main menu to create an account
2. Login using your credentials
3. Choose **Submit Complaint**, describe the issue, and set priority
4. Use **View My Complaints** to track your complaint's status

### As an Admin
1. Login with:
   - **Username:** `admin`
   - **Password:** `admin123`
2. View all registered complaints across all students
3. Update any complaint's status (Pending / In Progress / Resolved)
4. Delete resolved or invalid complaints (archived for 7 days)

---

## 📁 Project Structure

```
Hostel Complaint Management System/
│
├── src/
│   └── hcms/
│       ├── Main.java                   # Entry point, menus, user interaction
│       ├── model/
│       │   ├── User.java               # Abstract base class for all users
│       │   ├── Student.java            # Student user type
│       │   ├── Admin.java              # Admin user type
│       │   └── Complaint.java          # Complaint data model
│       └── manager/
│           ├── UserManager.java        # Registration, login, user file I/O
│           └── ComplaintManager.java   # Complaint CRUD, file I/O, soft delete
│
├── complaints.txt                      # Persistent complaint storage
├── users.txt                           # Persistent student credentials
└── deleted_complaints/                 # Temporary archive for deleted complaints
```

---

## ✅ Results

- Students can register, login, and submit complaints with a single workflow
- Every complaint gets a unique ID and timestamp for reference
- Admins can update statuses in real time, with changes saved instantly to file
- The system handles invalid inputs gracefully without crashing
- Deleted complaints are safely retained for 7 days before cleanup

---

## 🔮 Future Scope

-  GUI interface using Java Swing or JavaFX
-  Database integration with MySQL for scalable storage
-  Email/SMS notifications on status updates
-  Priority-based complaint sorting and escalation
-  Web or Android application version

---

## 👤 Author

**SHUBH TYAGI**  <br>
Mail: tyagishubh.workspace@gmail.com 
