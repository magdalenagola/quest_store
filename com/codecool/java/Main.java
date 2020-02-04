package codecool.java;

import codecool.java.model.UserDAO;

public class Main{
    public static void main(String[] args) {
        System.out.println("hello");
        UserDAO dao = new UserDAO();
        dao.selectUser();
    }
}