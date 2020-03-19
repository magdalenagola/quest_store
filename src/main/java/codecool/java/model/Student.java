package codecool.java.model;

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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public void start(){
//        try {
//            StudentController studentController = new StudentController();
//            studentController.run(this);
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }

    }

    public int getId() {
        return id;
    }


    @Override
    public String toString() {
        String response = "";
        response = String.format("ID: %d Login: %s Password: %s Name: %s Surname: %s",
                getId(),getLogin(),getPassword(),getName(),getSurname());
        return response;
    }
}
