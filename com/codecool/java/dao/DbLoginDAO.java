package codecool.java.dao;

import codecool.java.model.BasicConnectionPool;
import codecool.java.model.User;
import codecool.java.model.UserFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbLoginDAO implements LoginDao {
    private BasicConnectionPool pool;

    public DbLoginDAO() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        this.pool = BasicConnectionPool.create("jdbc:postgresql://ec2-176-34-237-141.eu-west-1.compute.amazonaws.com:5432/dbc5jifafq3j1h?sslmode=require",
                "utiuhfgjckzuoq", "17954f632e3663cbadb55550dd636f4c3a645ade56c3342ee89f71fc732c9672");
    }

    @Override
    public ResultSet findLoginInfo(String providedLogin, String providedPassword) throws SQLException {
        Connection c = pool.getConnection();
        PreparedStatement ps = c.prepareStatement("SELECT * FROM USER" +
                "WHERE email = ? AND password = ?;");
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
