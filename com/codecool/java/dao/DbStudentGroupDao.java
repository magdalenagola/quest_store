package codecool.java.dao;

import codecool.java.model.Group;
import codecool.java.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbStudentGroupDao extends DbConnectionDao implements StudentGroupDao {

    public DbStudentGroupDao(){
        super();
    }

    @Override
    public List<Student> selectGroupUsers(int id){
        ResultSet rs;
        Student student;
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM users JOIN usertypes ON users.usertype_id = usertypes.id JOIN student_details sd on users.id = sd.user_id WHERE sd.group_id = ?;";
        Connection c = dbconnection.getConnection();
        try{
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1, id);
        rs = ps.executeQuery(query);
        while(rs.next()){
            int studentId = rs.getInt("users.id");
            String email = rs.getString("email");
            String password = rs.getString("password");
            String name = rs.getString("users.name");
            String surname = rs.getString("surname");
            boolean isActive = rs.getBoolean("users.is_active");
            student = new Student(studentId, email, password, name, surname,isActive);
            students.add(student);
        }
        }catch (SQLException e){
        e.printStackTrace();
    }
        return students;
    }

    @Override
    public Group selectGroup(int id){
        List<Student> students = selectGroupUsers(id);
        Group group = null;
        try{
        ResultSet rs = selectEntryById(id);
        while(rs.next()) {
            String name = rs.getString("name");
            boolean isActive = rs.getBoolean("is_active");
            group = new Group(students, id, name, isActive);
        }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return group;
    }

    private ResultSet selectEntryById(int id) {
        String orderToSql = "SELECT * FROM student_groups WHERE id = ?;";
        Connection c = dbconnection.getConnection();
        ResultSet rs = null;
        try{
            PreparedStatement ps = c.prepareStatement(orderToSql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public void enableAllGroups(){
        String orderToSql = "UPDATE student_groups SET is_active = true;";
        Connection c = dbconnection.getConnection();
        try {
            c.createStatement().execute(orderToSql);
        }catch (SQLException e){
        e.printStackTrace();
    }
    }

    @Override
    public void disableAllGroups() {
        String orderToSql = "UPDATE student_groups SET is_active = false;";
        Connection c = dbconnection.getConnection();
        try{
        c.createStatement().execute(orderToSql);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List loadAll(){
        Group group;
        List<Object> groups = new ArrayList<>();
        List<Integer> groupIds = getGroupIds();
        for (int i : groupIds) {
            group = selectGroup(i);
            groups.add(group);
        }
        return groups;
    }

    @Override
    public List<Integer> getGroupIds() {
        int id;
        List<Integer> groupIds = new ArrayList<>();
        String orderToSql = ("SELECT * FROM student_groups");
        Connection c = dbconnection.getConnection();
        try {
            ResultSet rs = c.createStatement().executeQuery(orderToSql);
            while (rs.next()) {
                id = rs.getInt("id");
                groupIds.add(id);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return groupIds;
    }

    @Override
    public void update(Object o) {
        Group group = (Group) o;
        String orderToSql = "UPDATE student_groups SET name = ? WHERE id = ?;";
        Connection c = dbconnection.getConnection();
        try{
        PreparedStatement ps = c.prepareStatement(orderToSql);
        ps.setString(1, group.getName());
        ps.setInt(2, group.getId());
        ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void disable(Object o) {
        Group group = (Group) o;
        String orderToSql = "UPDATE student_groups SET is_active = false WHERE id = ?;";
        Connection c = dbconnection.getConnection();
        try{
        PreparedStatement ps = c.prepareStatement(orderToSql);
        ps.setInt(1, group.getId());
        ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void activate(Object o){
        Group group = (Group) o;
        String orderToSql = "UPDATE student_groups SET is_active = true WHERE id = ?;";
        Connection c = dbconnection.getConnection();
        try{
        PreparedStatement ps = c.prepareStatement(orderToSql);
        ps.setInt(1, group.getId());
        ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void save(Object o){
        Group group = (Group) o;
        String orderToSql = "INSERT INTO student_groups (name) VALUES (?);";
        Connection c = dbconnection.getConnection();
        try {
            PreparedStatement ps = c.prepareStatement(orderToSql);
            ps.setString(1, group.getName());
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
