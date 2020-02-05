package codecool.java;

import codecool.java.dao.UserDao;

import java.sql.SQLException;

public class Main{
    public static void main(String[] args) {
        System.out.println("hello");
        try {
            UserDao userdao = new UserDao();
            userdao.findLoginInfo("adrian@wii.com", "123");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}