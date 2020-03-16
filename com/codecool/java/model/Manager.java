package codecool.java.model;

public class Manager extends User {
    private int id;
    private String login;
    private String password;
    private String name;
    private String surname;
    private boolean isActive;

    public Manager(int id, String login, String password, String name, String surname, boolean isActive){
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.isActive = isActive;
    }

    @Override
    public void start(){
    }

    @Override
    public int getId() {
        return this.id;
    }

    public String toString() {
        String mentorInfo = "id: " + this.id + ", name: " + this.name;
        return mentorInfo;
    }

}
