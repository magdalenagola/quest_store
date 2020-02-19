package codecool.java.dao;

import codecool.java.model.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private DatabaseConnection dbconnection;

    public UserDao() throws SQLException, ClassNotFoundException {
        dbconnection = new DatabaseConnection();
    }

    public ResultSet findLoginInfo(String login, String password) throws SQLException {
        String query = "SELECT * FROM users JOIN usertypes ON users.usertype_id = usertypes.id WHERE email = ? AND password = ?;";
        Connection c = dbconnection.getConnection();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, login);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        return rs;
    }
}