package codecool.java.dao;

import codecool.java.model.User;
import codecool.java.model.UserFactory;

import java.net.HttpCookie;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;


public class DbAuthorizationDAO extends DbConnectionDao implements LoginDao {

    public DbAuthorizationDAO() throws ClassNotFoundException, SQLException {
        super();
    }

    @Override
    public ResultSet findLoginInfo(String providedLogin, String providedPassword) throws SQLException {
        Connection c = dbconnection.getConnection();
        PreparedStatement ps = c.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?;");
        ps.setString(1, providedLogin);
        ps.setString(2, providedPassword);
        ResultSet rs = ps.executeQuery();
        dbconnection.closeConnection(c);
        return rs;
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

    public void saveCookie(int userId, Optional<HttpCookie> cookie) throws SQLException, ParseException {
        Connection c = dbconnection.getConnection();
        PreparedStatement ps = c.prepareStatement("INSERT INTO cookies (session_id, user_id, creation_date, expiration_date, is_active) VALUES (?, ?, ?, ?, ?);");
        ps.setString(1, cookie.get().getValue());
        ps.setInt(2, userId);
        Date creationDate = new Date();
        Date expDate = new Date();
        expDate.setTime(creationDate.getTime() + cookie.get().getMaxAge() * 1000);
        ps.setTimestamp(3, new java.sql.Timestamp(creationDate.getTime()));
        ps.setTimestamp(4, new java.sql.Timestamp(expDate.getTime()));
        ps.setBoolean(5, true);
        ps.executeUpdate();
        dbconnection.closeConnection(c);
    }

    public void disableCookie(String sessionID) throws SQLException {
        Connection c = dbconnection.getConnection();
        PreparedStatement ps = c.prepareStatement("UPDATE cookies SET is_active = false WHERE session_id = ?;");
        ps.setString(1, sessionID);
        ps.executeUpdate();
        dbconnection.closeConnection(c);
    }


}
