package codecool.java.dao;

import codecool.java.model.BasicConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private BasicConnectionPool pool;

    public UserDao() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        this.pool = BasicConnectionPool.create("jdbc:postgresql://ec2-176-34-237-141.eu-west-1.compute.amazonaws.com:5432/dbc5jifafq3j1h?sslmode=require",
                "utiuhfgjckzuoq", "17954f632e3663cbadb55550dd636f4c3a645ade56c3342ee89f71fc732c9672");
    }

    public ResultSet findLoginInfo(String login, String password) throws SQLException {
        String query = "SELECT * FROM users JOIN usertypes ON users.usertype_id = usertypes.id WHERE email = ? AND password = ?;";
        Connection c = pool.getConnection();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, login);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        return rs;
    }
}