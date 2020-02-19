package codecool.java.dao;

import codecool.java.model.Group;
import codecool.java.model.Quest;
import codecool.java.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbStudentGroupDao extends DbConnectionDao implements StudentGroupDao {

    public DbStudentGroupDao() throws SQLException, ClassNotFoundException {
        super();
    }

    @Override
    public List<Student> selectGroupUsers(int id) throws SQLException {
        ResultSet rs;
        Student student;
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM users JOIN usertypes u on users.usertype_id = u.id JOIN student_details sd on users.id = sd.user_id JOIN student_groups sg on sg.id = ?;";
        Connection c = pool.getConnection();
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
        return students;
    }

    @Override
    public Group selectGroup(int id) throws SQLException {
        List<Student> students = selectGroupUsers(id);
        Group group = null;
        ResultSet rs = selectEntryById(id);
        while(rs.next()) {
            String name = rs.getString("name");
            boolean isActive = rs.getBoolean("is_active");
            group = new Group(students, id, name, isActive);
        }
        return group;
    }

    private ResultSet selectEntryById(int id) throws SQLException {
        String orderToSql = "SELECT * FROM student_groups WHERE id = ?;";
        Connection c = pool.getConnection();
        PreparedStatement ps = c.prepareStatement(orderToSql);
        ps.setInt(1, id);
        return ps.executeQuery();
    }

    @Override
    public void enableAllGroups() throws SQLException {
        String orderToSql = "UPDATE student_groups SET is_active = true;";
        Connection c = pool.getConnection();
        c.createStatement().execute(orderToSql);
    }

    @Override
    public void disableAllGroups() throws SQLException {
        String orderToSql = "UPDATE student_groups SET is_active = false;";
        Connection c = pool.getConnection();
        c.createStatement().execute(orderToSql);
    }

    @Override
    public List loadAll() throws SQLException {
        Group group;
        List<Object> groups = new ArrayList<>();
        List<Integer> groupIds = getGroupIds();
        for(int i : groupIds){
            group = selectGroup(i);
            groups.add(group);
        }
        return groups;
    }

    @Override
    public List<Integer> getGroupIds() throws SQLException {
        int id;
        List<Integer> groupIds = new ArrayList<>();
        String orderToSql = ("SELECT * FROM student_groups");
        Connection c = pool.getConnection();
        ResultSet rs = c.createStatement().executeQuery(orderToSql);
        while(rs.next()){
            id = rs.getInt("id");
            groupIds.add(id);
        }
        return groupIds;
    }

    @Override
    public void update(Object o) throws SQLException {
        Group group = (Group) o;
        String orderToSql = "UPDATE student_groups SET name = ? WHERE id = ?;";
        Connection c = pool.getConnection();
        PreparedStatement ps = c.prepareStatement(orderToSql);
        ps.setString(1, group.getName());
        ps.setInt(2, group.getId());
        ps.execute();
    }

    @Override
    public void disable(Object o) throws SQLException {
        Group group = (Group) o;
        String orderToSql = "UPDATE student_groups SET is_active = false WHERE id = ?;";
        Connection c = pool.getConnection();
        PreparedStatement ps = c.prepareStatement(orderToSql);
        ps.setInt(1, group.getId());
        ps.execute();
    }

    @Override
    public void activate(Object o) throws SQLException {
        Group group = (Group) o;
        String orderToSql = "UPDATE student_groups SET is_active = true WHERE id = ?;";
        Connection c = pool.getConnection();
        PreparedStatement ps = c.prepareStatement(orderToSql);
        ps.setInt(1, group.getId());
        ps.execute();
    }

    @Override
    public void save(Object o) throws SQLException {
        Group group = (Group) o;
        String orderToSql = "INSERT INTO student_groups (name) VALUES (?);";
        Connection c = pool.getConnection();
        PreparedStatement ps = c.prepareStatement(orderToSql);
        ps.setString(1, group.getName());
        ps.execute();
    }
}
