package codecool.java.dao;

import codecool.java.model.BasicConnectionPool;
import codecool.java.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbstudentDAO implements StudentDAO{
    private BasicConnectionPool pool;

    public DbstudentDAO() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        this.pool = BasicConnectionPool.create("jdbc:postgresql://ec2-176-34-237-141.eu-west-1.compute.amazonaws.com:5432/dbc5jifafq3j1h?sslmode=require",
                "utiuhfgjckzuoq", "17954f632e3663cbadb55550dd636f4c3a645ade56c3342ee89f71fc732c9672");

    }

    @Override
    public List<T> loadAll() throws SQLException {
        List<Student> result = new ArrayList<>();
        Connection c = pool.getConnection();
        PreparedStatement ps = c.prepareStatement("SELECT * FROM USER JOIN ON user.usertype_id = usertypes.id " +
                "WHERE usertype = Student;");
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
        return (List<T>) result;
    }

    @Override
    public void save(T t) throws SQLException {
        Connection c = pool.getConnection();
        Student student = (Student) t;
        PreparedStatement ps = c.prepareStatement("INSERT INTO USER(email,password,name,surname,usertype_id,is_active) " +
                "VALUES(?,?,?,?,1,true)");
        ps.setString(1, student.getLogin());
        ps.setString(2, student.getPassword());
        ps.setString(3, student.getName());
        ps.setString(4, student.getSurname());
        ps.executeUpdate();
    }

    @Override
    public void update(T t) throws SQLException {
        Connection c = pool.getConnection();
        Student student = (Student) t;
        PreparedStatement ps = c.prepareStatement("UPDATE USER SET email =?," +
                "password = ?,name = ?,surname = ?,usertype_id = 1,is_active = ?) ");
        ps.setString(1, student.getLogin());
        ps.setString(2, student.getPassword());
        ps.setString(3, student.getName());
        ps.setString(4, student.getSurname());
        ps.setString(5, student.isActive());
        ps.executeUpdate();
    }


    @Override
    public int getCoins(Student student) throws SQLException {
        Connection c = pool.getConnection();
        Student student = (Student) t;
        int studentCoins = 0;
        PreparedStatement ps = c.prepareStatement(String.format("SELECT  coins FROM student_details WHERE user_id = %d;", student.getId()));
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            studentCoins = rs.getInt("coins");
        }
        return studentCoins;
    }

    @Override
    public void updateCoins(Student student, int amount) throws SQLException {
        Connection c = pool.getConnection();
        Student student = (Student) t;
        PreparedStatement ps = c.prepareStatement(String.format("UPDATE student_details SET coins = (coins + ?)  WHERE user_id = %d;", student.getId()));
        ps.setInt(1, amount);
        ps.executeUpdate();
    }


    @Override
    public void disable(T t) throws SQLException {
        Connection c = pool.getConnection();
        Student student = (Student) t;
        PreparedStatement ps = c.prepareStatement(String.format("UPDATE users SET is_active = false WHERE id = %d;", student.getId()));
        ps.executeUpdate();
    };


    @Override
    public void activate(T t) throws SQLException {
        Connection c = pool.getConnection();
        Student student = (Student) t;
        PreparedStatement ps = c.prepareStatement(String.format("UPDATE users SET is_active = true WHERE id = %d;", student.getId()));
        ps.executeUpdate();
    };
}

