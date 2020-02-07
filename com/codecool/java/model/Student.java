package codecool.java.model;

import codecool.java.controller.StudentController;

import java.sql.SQLException;

public class Student extends User {

    private int id;
    private String login;
    private String password;
    private String name;
    private String surname;
    private boolean isActive;

    public Student(int id, String login, String password, String name, String surname, boolean isActive) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.isActive = isActive;
    }

    public Student(String login, String password, String name, String surname, boolean isActive) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.isActive = isActive;
    }

    @Override
    public void start() throws SQLException, ClassNotFoundException {
        StudentController studentController = new StudentController();
        studentController.run(this.id);
    }
}
