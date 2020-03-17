package codecool.java.dao;

import codecool.java.model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbstudentDAO extends DbConnectionDao implements StudentDAO{

    public DbstudentDAO(){
        super();
    }

    public Student selectStudentById(int id){
        Connection c = dbconnection.getConnection();
        Student student = null;
        try{
            PreparedStatement ps = c.prepareStatement("SELECT * FROM users WHERE id = ?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                student = createStudent(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    public Student selectStudentByLogin(String login){
        Connection c = dbconnection.getConnection();
        Student student = null;
        try{
            PreparedStatement ps = c.prepareStatement("SELECT * FROM users WHERE email = ?;");
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                student = createStudent(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public List<Student> loadAll(){
        List<Student> result = new ArrayList<>();
        Connection c = dbconnection.getConnection();
        try{
        PreparedStatement ps = c.prepareStatement("SELECT * FROM users JOIN usertypes ON (users.usertype_id = usertypes.id) " +
                "WHERE usertype = 'Student';");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Student student = createStudent(rs);
            result.add(student);
        }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public List<Student> loadAllActive(){
        List<Student> result = new ArrayList<>();
        Connection c = dbconnection.getConnection();
        try{
            PreparedStatement ps = c.prepareStatement("SELECT * FROM users JOIN usertypes ON (users.usertype_id = usertypes.id) " +
                    "WHERE usertype = 'Student' AND is_active = true;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Student student = createStudent(rs);
                result.add(student);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private Student createStudent(ResultSet rs) throws SQLException {
        Student student = null;
            int id = rs.getInt("id");
            String email = rs.getString("email");
            String password = rs.getString("password");
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            boolean is_active = rs.getBoolean("is_active");
            student = new Student(id,email,password,name,surname,is_active);
            return student;

    }

    @Override
    public void save(Object o){
        Connection c = dbconnection.getConnection();
        try{
        Student student = (Student) o;
        PreparedStatement ps = c.prepareStatement("INSERT INTO users (email,password,name,surname,usertype_id,is_active) " +
                "VALUES(?,?,?,?,?,?)");
        ps.setString(1, student.getLogin());
        ps.setString(2, student.getPassword());
        ps.setString(3, student.getName());
        ps.setString(4, student.getSurname());
        ps.setInt(5, 1);
        ps.setBoolean(6, true);
        ps.executeUpdate();
            PreparedStatement query = c.prepareStatement("SELECT id FROM users WHERE email = ?;");
            query.setString(1,student.getLogin());
            ResultSet rs = query.executeQuery();
            if(!rs.next())
                throw new SQLException();
            while(rs.next()){
            int newId = rs.getInt("id");
            ps = c.prepareStatement("INSERT INTO student_details (user_id, group_id, coins) VALUES (?, 3, 0)");
            ps.setInt(1, newId);
            ps.executeUpdate();
        }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Object o){
        Connection c = dbconnection.getConnection();
        Student student = (Student) o;
        System.out.println(o.toString());
        try {
            PreparedStatement ps = c.prepareStatement("UPDATE users SET email =?," +
                    "password = ?,name = ?,surname = ?,usertype_id = 1,is_active = ? WHERE id = ?; ");
            ps.setString(1, student.getLogin());
            ps.setString(2, student.getPassword());
            ps.setString(3, student.getName());
            ps.setString(4, student.getSurname());
            ps.setBoolean(5, student.isActive());
            ps.setInt(6, student.getId());
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


    @Override
    public int getCoins(Student student){
        Connection c = dbconnection.getConnection();
        int studentCoins = 0;
        try{
            PreparedStatement ps = c.prepareStatement(String.format("SELECT  coins FROM student_details WHERE user_id = %d;", student.getId()));
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                studentCoins = rs.getInt("coins");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return studentCoins;
    }

    @Override
    public void updateCoins(Student student, int amount){
        Connection c = dbconnection.getConnection();
        try {
            PreparedStatement ps = c.prepareStatement(String.format("UPDATE student_details SET coins = (coins + ?)  WHERE user_id = %d;", student.getId()));
            ps.setInt(1, amount);
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


    @Override
    public void disable(Object o){
        Connection c = dbconnection.getConnection();
        Student student = (Student) o;
        try {
            PreparedStatement ps = c.prepareStatement(String.format("UPDATE users SET is_active = false WHERE id = %d;", student.getId()));
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    @Override
    public void activate(Object o){
        Connection c = dbconnection.getConnection();
        Student student = (Student) o;
        try {
            PreparedStatement ps = c.prepareStatement(String.format("UPDATE users SET is_active = true WHERE id = %d;", student.getId()));
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Student findStudentBySessionId(String sessionId) {
        Connection c = dbconnection.getConnection();
        Student student = null;
        try {
            PreparedStatement ps = c.prepareStatement("SELECT * FROM users JOIN cookies ON (users.id = cookies.user_id) " +
                    "WHERE session_id = ?;");
            ps.setString(1, sessionId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                boolean is_active = rs.getBoolean("is_active");
                student = new Student(id, email, password, name, surname, is_active);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }
}

