package codecool.java.model;

import codecool.java.controller.MentorController;

public class Mentor extends User{
    private int id;
    private String login;
    private String password;
    private String name;
    private String surname;
    private String primarySkill;
    private boolean isActive;

    public Mentor(int id,String login, String password,String name,String surname,String primarySkill,boolean isActive){
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.primarySkill = primarySkill;
        this.isActive = isActive;
    }
    public Mentor(String login, String password,String name,String surname,String primarySkill,boolean isActive){
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.primarySkill = primarySkill;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPrimarySkill() {
        return primarySkill;
    }

    public void setPrimarySkill(String primarySkill) {
        this.primarySkill = primarySkill;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public void start() {
        MentorController controller = new MentorController();
        controller.run();
    }

    @Override
    public String toString() {
        String response = "";
        response = String.format("ID: %d Login: %s Password: %s Name: %s Surname: %s Primary Skill: %s",
                getId(),getLogin(),getPassword(),getName(),getSurname(),getPrimarySkill());
        return response;
    }

}