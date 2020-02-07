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
    @Override
    public void start() {
        MentorController controller = new MentorController();
        controller.run();
    }

}
