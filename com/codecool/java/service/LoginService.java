package codecool.java.service;

import codecool.java.dao.DAO;
import codecool.java.dao.UserDao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginService {
    private UserDao dao;

    public LoginService() throws SQLException, ClassNotFoundException {
        this.dao = new UserDao();
    }

    public User logIn(String providedLogin, String providedPassword) throws SQLException {
        ResultSet rs = dao.findLoginInfo(providedLogin, providedPassword);
        UserFactory userFactory = new UserFactory();
        if (!rs.next()) {
            throw new NoSuchUserException();
        } else {
            do {
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String userType = rs.getString("usertypes.usertype");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                boolean isActive = rs.getBoolean("is_active");
                User user = userFactory.createUser(id, email, password, userType, name, surname, isActive);
            } while (rs.next());
            return user;
        }
    }
}