package codecool.java.dao;

import codecool.java.controller.exceptions.NoSuchUserException;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface LoginDao {
    ResultSet findLoginInfo(String providedLogin, String providedPassword) throws SQLException;
    User logIn(String providedLogin, String providedPassword) throws SQLException, NoSuchUserException, ClassNotFoundException;
}
