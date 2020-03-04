package codecool.java.dao;

import codecool.java.model.Mentor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbMentorDAO extends DbConnectionDao implements MentorDAO {

    public DbMentorDAO() throws SQLException, ClassNotFoundException {
        super();
    }

    @Override
    public String getPrimarySkillById(int mentorId) throws SQLException {
        String primarySkill = null;
        Connection c = dbconnection.getConnection();
        PreparedStatement ps = c.prepareStatement(String.format("SELECT primary_skill FROM mentor_details WHERE user_id = %d;", mentorId));
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            primarySkill = rs.getString("primary_skill");
        }
        return primarySkill;
    }

    @Override
    public void save(Object t) throws SQLException {
        Connection c = dbconnection.getConnection();
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
        Connection c = dbconnection.getConnection();
        List<Mentor> mentorList = new ArrayList<>();
        PreparedStatement ps = c.prepareStatement("SELECT * FROM users JOIN usertypes ON (users.usertype_id = usertypes.id) " +
                "JOIN mentor_details ON (users.id = mentor_details.user_id) WHERE usertypes.id = 2;");
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            Integer id = rs.getInt("id");
            String email = rs.getString("email");
            String password = rs.getString("password");
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            String primarySkill = rs.getString("primary_skill");
            int earnings = rs.getInt("earnings");
            boolean isActive = rs.getBoolean("is_active");
            Mentor mentor = new Mentor(id, email, password, name, surname, primarySkill, earnings, isActive);
            mentorList.add(mentor);
        }
        return  mentorList;
    };

    @Override
    public void update(Object t) throws SQLException {
        Connection c = dbconnection.getConnection();
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
        Connection c = dbconnection.getConnection();
        Mentor mentor = (Mentor) t;
        PreparedStatement ps = c.prepareStatement(String.format("UPDATE users SET is_active = false WHERE id = %d;", mentor.getId()));
        ps.executeUpdate();
    };

    @Override
    public void activate(Object t) throws SQLException {
        Connection c = dbconnection.getConnection();
        Mentor mentor = (Mentor) t;
        PreparedStatement ps = c.prepareStatement(String.format("UPDATE users SET is_active = true WHERE id = %d;", mentor.getId()));
        ps.executeUpdate();
    };
}
