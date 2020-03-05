package codecool.java.dao;

import codecool.java.model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbstudentDAO extends DbConnectionDao implements StudentDAO{

    public DbstudentDAO() throws SQLException, ClassNotFoundException {
        super();
    }

    public Student selectStudentById(int id) throws SQLException {
        Connection c = dbconnection.getConnection();
        PreparedStatement ps = c.prepareStatement("SELECT * FROM users WHERE id = ?;");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Student student = null;
        try {
            while (rs.next()) {
                student = createStudent(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbconnection.closeConnection(c);
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
        } finally{
            dbconnection.closeConnection(c);
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
        } finally{
            dbconnection.closeConnection(c);
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
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            dbconnection.closeConnection(c);
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
        }finally {
            dbconnection.closeConnection(c);
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
        }finally {
            dbconnection.closeConnection(c);
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
        }finally {
            dbconnection.closeConnection(c);
        }
    }


    @Override
    public void disable(Object o) throws SQLException {
        Connection c = dbconnection.getConnection();
        Student student = (Student) o;
        PreparedStatement ps = c.prepareStatement(String.format("UPDATE users SET is_active = false WHERE id = %d;", student.getId()));
        ps.executeUpdate();
    };


    @Override
    public void activate(Object o) throws SQLException {
        Connection c = dbconnection.getConnection();
        Student student = (Student) o;
        PreparedStatement ps = c.prepareStatement(String.format("UPDATE users SET is_active = true WHERE id = %d;", student.getId()));
        ps.executeUpdate();
    };
    public Student findStudentBySessionId(String sessionId) throws SQLException {
        Connection c = dbconnection.getConnection();
        Student student = null;
        PreparedStatement ps = c.prepareStatement("SELECT * FROM users JOIN cookies ON (users.id = cookies.user_id) " +
                "WHERE session_id = ?;");
        ps.setString(1,sessionId);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
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
        c.close();
        return student;
    }
}

