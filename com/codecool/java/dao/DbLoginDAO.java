package codecool.java.dao;

import codecool.java.model.User;
import codecool.java.model.UserFactory;

import java.sql.*;


public class DbLoginDAO extends DbConnectionDao implements LoginDao {

    public DbLoginDAO() throws ClassNotFoundException, SQLException {
        super();
    }

    @Override
    public ResultSet findLoginInfo(String providedLogin, String providedPassword) throws SQLException {
        Connection c = pool.getConnection();
        PreparedStatement ps = c.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?;");
        ps.setString(1, providedLogin);
        ps.setString(2, providedPassword);
        return ps.executeQuery();
    }

    @Override
    public User logIn(String providedLogin, String providedPassword) throws SQLException, ClassNotFoundException, NotInDatabaseException {
        ResultSet rs = findLoginInfo(providedLogin, providedPassword);
        UserFactory userFactory = new UserFactory();
        User user = null;
        if (!rs.next()) {
            throw new NotInDatabaseException();
        } else {
            do {
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String userPassword = rs.getString("password");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                int userTypeID = rs.getInt("usertype_id");
                boolean isActive = rs.getBoolean("is_active");

                user = userFactory.createUser(id, email, userPassword, name, surname, userTypeID, isActive);
            }
            while (rs.next());
        }

        return user;
    }
}
