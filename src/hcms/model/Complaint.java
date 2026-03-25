package hcms.model;

public class Complaint {
    private String id;
    private String studentName;
    private String issue;
    private String status; // Pending, In Progress, Resolved
    private String priority; // Low, Medium, High
    private String timestamp;

    public Complaint(String id, String studentName, String issue, String status, String priority, String timestamp) {
        this.id = id;
        this.studentName = studentName;
        this.issue = issue;
        this.status = status;
        this.priority = priority;
        this.timestamp = timestamp;
    }

    public String getId() { return id; }
    public String getStudentName() { return studentName; }
    public String getIssue() { return issue; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getPriority() { return priority; }
    public String getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return String.format("[%s] ID: %s | Student: %s | Priority: %s | Status: %s\nIssue: %s",
                timestamp, id, studentName, priority, status, issue);
    }
    
    public String toFileFormat() {
        // Sanitize issue to remove pipes and newlines that would break the format
        String safeIssue = issue.replace("|", "-").replace("\n", " ");
        return id + "|" + studentName + "|" + safeIssue + "|" + status + "|" + priority + "|" + timestamp;
    }
    
    public static Complaint fromFileFormat(String line) {
        String[] parts = line.split("\\|");
        if(parts.length >= 6) {
            return new Complaint(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
        }
        return null;
    }
}
