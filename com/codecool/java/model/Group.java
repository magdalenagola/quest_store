package codecool.java.model;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private List<User> students;
    private int id;
    private String name;
    private boolean isActive;

    public Group(List<User> students, int id, String name, boolean isActive) {
        this.students = students;
        this.id = id;
        this.name = name;
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        String studentNames = "";
        for (User student : students){
            studentNames += student.getName() + " " + student.getSurname() + " ";
        }
        return "ID: " + id + ", name: " + name + ", isActive: " + isActive + ", students: " + studentNames;
    }

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
