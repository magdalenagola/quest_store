package codecool.java.dao;

import codecool.java.model.Mentor;
import codecool.java.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbMentorDAO extends DbConnectionDao implements MentorDAO {

    public DbMentorDAO(){
        super();
    }

    @Override
    public String getPrimarySkillById(int mentorId){
        String primarySkill = null;
        Connection c = dbconnection.getConnection();
        try {
            PreparedStatement ps = c.prepareStatement(String.format("SELECT primary_skill FROM mentor_details WHERE user_id = %d;", mentorId));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                primarySkill = rs.getString("primary_skill");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return primarySkill;
    }

    @Override
    public void save(Object t){
        Connection c = dbconnection.getConnection();
        Mentor mentor = (Mentor) t;
        try {
            PreparedStatement ps = c.prepareStatement("INSERT INTO users(email, password, name, surname, usertype_id, is_active) VALUES(?, ?, ?, ?, ?, ?);");
            ps.setString(1, mentor.getLogin());
            ps.setString(2, mentor.getPassword());
            ps.setString(3, mentor.getName());
            ps.setString(4, mentor.getSurname());
            ps.setInt(5, 2);
            ps.setBoolean(6, true);
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    };

    public void saveDetails(Mentor mentor){
        Connection c = dbconnection.getConnection();
        try {
            PreparedStatement query = c.prepareStatement("SELECT id FROM users WHERE email = ?;");
            query.setString(1, mentor.getLogin());
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                int newId = rs.getInt("id");
                PreparedStatement ps = c.prepareStatement("INSERT INTO mentor_details (user_id, primary_skill, date_hired, earnings) VALUES (?, ?, CURRENT_DATE, ?);");
                ps.setInt(1, newId);
                ps.setString(2, mentor.getPrimarySkill());
                ps.setInt(3, mentor.getEarnings());
                ps.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateDetails(Mentor mentor){
        Connection c = dbconnection.getConnection();
        try {
            PreparedStatement query = c.prepareStatement("SELECT id FROM users WHERE email = ?;");
            query.setString(1, mentor.getLogin());
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                int newId = rs.getInt("id");
                PreparedStatement ps = c.prepareStatement("UPDATE mentor_details SET primary_skill = ?, date_hired =  CURRENT_DATE, earnings = ? WHERE user_id = ?;");
                ps.setString(1, mentor.getPrimarySkill());
                ps.setInt(2, mentor.getEarnings());
                ps.setInt(3, newId);
                ps.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Mentor> loadAll(){
        Connection c = dbconnection.getConnection();
        List<Mentor> mentorList = new ArrayList<>();
        try {
            PreparedStatement ps = c.prepareStatement("SELECT * FROM users JOIN usertypes ON (users.usertype_id = usertypes.id) " +
                    "JOIN mentor_details ON (users.id = mentor_details.user_id) WHERE usertypes.id = 2;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
        }catch (SQLException e){
            e.printStackTrace();
        }
        return  mentorList;
    };

    public List<Mentor> loadAllActive(){
        Connection c = dbconnection.getConnection();
        List<Mentor> mentorList = new ArrayList<>();
        try {
            PreparedStatement ps = c.prepareStatement("SELECT * FROM users JOIN usertypes ON (users.usertype_id = usertypes.id) " +
                    "JOIN mentor_details ON (users.id = mentor_details.user_id) WHERE usertypes.id = 2 AND is_active = true;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
        }catch (SQLException e){
            e.printStackTrace();
        }
        return  mentorList;
    }

    public Mentor selectMentorById(int id){
        Connection c = dbconnection.getConnection();
        Mentor mentor = null;
        try{
        PreparedStatement ps = c.prepareStatement("SELECT * FROM users JOIN mentor_details ON users.id = mentor_details.user_id WHERE users.id = ?;");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                mentor = createMentor(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mentor;
    }


    public Mentor selectMentorByLogin(String login){
        Connection c = dbconnection.getConnection();
        Mentor mentor = null;
        try{
            PreparedStatement ps = c.prepareStatement("SELECT * FROM users JOIN mentor_details ON users.id = mentor_details.user_id WHERE email = ?;");
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                mentor = createMentor(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mentor;
    }

    private Mentor createMentor(ResultSet rs) throws SQLException {
        Mentor mentor = null;
        int id = rs.getInt("id");
        String email = rs.getString("email");
        String password = rs.getString("password");
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        boolean is_active = rs.getBoolean("is_active");
        String primarySkill = rs.getString("primary_skill");
        mentor = new Mentor(id,email,password,name,surname,primarySkill,is_active);
        return mentor;
    }

    @Override
    public void update(Object t){
        Connection c = dbconnection.getConnection();
        Mentor mentor = (Mentor) t;
        try {
            PreparedStatement ps = c.prepareStatement("UPDATE users SET email =?," +
                    "password = ?,name = ?,surname = ?,usertype_id = 2,is_active = true WHERE id = ?; ");
            ps.setString(1, mentor.getLogin());
            ps.setString(2, mentor.getPassword());
            ps.setString(3, mentor.getName());
            ps.setString(4, mentor.getSurname());
            ps.setInt(5, mentor.getId());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void disable(Object t){
        Connection c = dbconnection.getConnection();
        Mentor mentor = (Mentor) t;
        try {
            PreparedStatement ps = c.prepareStatement(String.format("UPDATE users SET is_active = false WHERE email = %s;", mentor.getLogin()));
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void activate(Object t){
        Connection c = dbconnection.getConnection();
        Mentor mentor = (Mentor) t;
        try {
            PreparedStatement ps = c.prepareStatement(String.format("UPDATE users SET is_active = true WHERE email = %s;", mentor.getLogin()));
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void delete(Object o){
        Connection c = dbconnection.getConnection();
        Mentor mentor = (Mentor) o;
        try {

            PreparedStatement ps = c.prepareStatement(" DELETE FROM mentor_details WHERE primary_skill = ?;");
            ps.setString(1, mentor.getPrimarySkill());
            ps.executeUpdate();
            ps = c.prepareStatement("DELETE FROM users WHERE email = ?;");
            ps.setString(1, mentor.getLogin());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
