package hcms.model;

public class Student extends User {
    public Student(String username, String password) {
        super(username, password);
    }
    
    @Override
    public String toString() {
        return "Student{" + "username='" + getUsername() + '\'' + '}';
    }
}
