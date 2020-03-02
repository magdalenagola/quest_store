package codecool.java.dao;

import codecool.java.model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbstudentDAO extends DbConnectionDao implements StudentDAO{

    public DbstudentDAO() throws SQLException, ClassNotFoundException {
    }

    @Override
    public List<Student> loadAll(){
        List<Student> result = new ArrayList<>();
        try{
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM users JOIN usertypes ON (users.usertype_id = usertypes.id) " +
                "WHERE usertype = 'Student';");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int id = rs.getInt("id");
            String email = rs.getString("email");
            String password = rs.getString("password");
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            boolean is_active = rs.getBoolean("is_active");
            Student student = new Student(id,email,password,name,surname,is_active);
            result.add(student);
        }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void save(Object o){
        try{
        Student student = (Student) o;
        PreparedStatement ps = conn.prepareStatement("INSERT INTO users (email,password,name,surname,usertype_id,is_active) " +
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
        }
    }

    @Override
    public void update(Object o){
        Student student = (Student) o;
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE users SET email =?," +
                    "password = ?,name = ?,surname = ?,usertype_id = 1,is_active = ? WHERE id = ?; ");
            ps.setString(1, student.getLogin());
            ps.setString(2, student.getPassword());
            ps.setString(3, student.getName());
            ps.setString(4, student.getSurname());
            ps.setBoolean(5, student.isActive());
            ps.setInt(5, student.getId());
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


    @Override
    public int getCoins(Student student){
        int studentCoins = 0;
        try{
            PreparedStatement ps = conn.prepareStatement(String.format("SELECT  coins FROM student_details WHERE user_id = %d;", student.getId()));
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
        try {
            PreparedStatement ps = conn.prepareStatement(String.format("UPDATE student_details SET coins = (coins + ?)  WHERE user_id = %d;", student.getId()));
            ps.setInt(1, amount);
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


    @Override
    public void disable(Object o) throws SQLException {
        Student student = (Student) o;
        PreparedStatement ps = conn.prepareStatement(String.format("UPDATE users SET is_active = false WHERE id = %d;", student.getId()));
        ps.executeUpdate();
    };


    @Override
    public void activate(Object o) throws SQLException {
        Student student = (Student) o;
        PreparedStatement ps = conn.prepareStatement(String.format("UPDATE users SET is_active = true WHERE id = %d;", student.getId()));
        ps.executeUpdate();
    };
    public Student findStudentBySessionId(String sessionId) throws SQLException {
        Student student = null;
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM users JOIN cookies ON (users.id = cookies.user_id) " +
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
        return student;
    }
}

