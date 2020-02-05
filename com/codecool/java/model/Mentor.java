package codecool.java.model;

import codecool.java.controller.MentorController;

public class Mentor extends User{
    private int id;
    private String login;
    private String password;
    private String name;
    private String surname;

    public Mentor(int id,String login, String password,String name,String surname){
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }
    @Override
    public void start() {
        MentorController controller = new MentorController();
        controller.run(this);
    }

}
