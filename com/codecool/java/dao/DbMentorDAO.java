package codecool.java.dao;

import codecool.java.model.BasicConnectionPool;
import codecool.java.model.Mentor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbMentorDAO implements MentorDAO {
    private BasicConnectionPool pool;

    public DbMentorDAO() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        this.pool = BasicConnectionPool.create("jdbc:postgresql://ec2-176-34-237-141.eu-west-1.compute.amazonaws.com:5432/dbc5jifafq3j1h?sslmode=require",
                "utiuhfgjckzuoq", "17954f632e3663cbadb55550dd636f4c3a645ade56c3342ee89f71fc732c9672");
    }

    @Override
    public String getPrimarySkillById(int mentorId) throws SQLException {
        String primarySkill = null;
        Connection c = pool.getConnection();
        PreparedStatement ps = c.prepareStatement(String.format("SELECT primary_skill FROM mentor_details WHERE user_id = %d;", mentorId));
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            primarySkill = rs.getString("primary_skill");
        }
        return primarySkill;
    }

    @Override
    public void save(Object t) throws SQLException {
        Connection c = pool.getConnection();
        Mentor mentor = (Mentor) t;
        PreparedStatement ps = c.prepareStatement("INSERT INTO users(email, password, name, surname, usertype_id, is_active) VALUES(?, ?, ?, ?, ?, ?);");
        ps.setString(1, mentor.getLogin());
        ps.setString(2, mentor.getPassword());
        ps.setString(3, mentor.getName());
        ps.setString(4, mentor.getSurname());
        ps.setInt(5, 2);
        ps.setBoolean(6, true);
        ps.executeUpdate();
    };

    @Override
    public List<Mentor> loadAll() throws SQLException {
        Connection c = pool.getConnection();
        List<Mentor> mentorList = new ArrayList<>();
        PreparedStatement ps = c.prepareStatement("SELECT * FROM users JOIN usertypes ON (users.usertype_id = usertypes.id) WHERE usertypes.id = 2;");
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            Integer id = rs.getInt("id");
            String email = rs.getString("email");
            String password = rs.getString("password");
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            boolean isActive = rs.getBoolean("is_active");
            Mentor mentor = new Mentor(id, email, password, name, surname, getPrimarySkillById(id), isActive);
            mentorList.add(mentor);
        }
        return  mentorList;
    };

    @Override
    public void update(Object t) throws SQLException {
        Connection c = pool.getConnection();
        Mentor mentor = (Mentor) t;
        PreparedStatement ps = c.prepareStatement(String.format("UPDATE users SET email =?," +
                "password = ?,name = ?,surname = ?,usertype_id = 1) WHERE id = %d;", mentor.getId()));
        ps.setString(1, mentor.getLogin());
        ps.setString(2, mentor.getPassword());
        ps.setString(3, mentor.getName());
        ps.setString(4, mentor.getSurname());
        ps.executeUpdate();
    };

    @Override
    public void disable(Object t) throws SQLException {
        Connection c = pool.getConnection();
        Mentor mentor = (Mentor) t;
        PreparedStatement ps = c.prepareStatement(String.format("UPDATE users SET is_active = false WHERE id = %d;", mentor.getId()));
        ps.executeUpdate();
    };

    @Override
    public void activate(Object t) throws SQLException {
        Connection c = pool.getConnection();
        Mentor mentor = (Mentor) t;
        PreparedStatement ps = c.prepareStatement(String.format("UPDATE users SET is_active = true WHERE id = %d;", mentor.getId()));
        ps.executeUpdate();
    };
}
