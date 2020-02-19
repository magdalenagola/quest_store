package codecool.java.dao;

import codecool.java.model.Group;
import codecool.java.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbStudentGroupDao extends DbIntermediateDao implements StudentGroupDao {

    public DbStudentGroupDao() throws SQLException, ClassNotFoundException {
    }

    @Override
    public List<Student> selectGroupUsers(int id) throws SQLException {
        ResultSet rs;
        Student student;
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM users JOIN usertypes ON users.usertype_id = usertypes.id JOIN student_details sd on users.id = sd.user_id JOIN student_groups sg on sd.group_id = sg.id WHERE student_groups.id = ?;";
        Connection c = dbconnection.getConnection();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1, id);
        rs = ps.executeQuery(query);
        while(rs.next()){
            int studentId = rs.getInt("id");
            String email = rs.getString("email");
            String password = rs.getString("password");
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            boolean isActive = rs.getBoolean("is_active");
            String group = rs.getString("student_groups.name");
            student = new Student(studentId, email, password, name, surname,isActive);
            students.add(student);
        }
        return students;
    }

    @Override
    public Group selectGroup(int id) throws SQLException {
        List<Student> students = selectGroupUsers(id);
        Group group = null;
        ResultSet rs = super.selectEntryById(id, "student_groups");
        while(rs.next()) {
            String name = rs.getString("name");
            boolean isActive = rs.getBoolean("is_active");
            group = new Group(students, id, name, isActive);
        }
        return group;
    }

    @Override
    public void enableAllGroups() throws SQLException {
        super.enableAllTableEntries("student_groups");
    }

    @Override
    public void disableAllGroups() throws SQLException {
        super.disableAllTableEntries("student_groups");
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
        ResultSet rs = super.selectAllFromTable("student_groups");
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
        Connection c = dbconnection.getConnection();
        PreparedStatement ps = c.prepareStatement(orderToSql);
        ps.setString(1, group.getName());
        ps.setInt(2, group.getId());
        ps.execute();
    }

    @Override
    public void disable(Object o) throws SQLException {
        Group group = (Group) o;
        super.disableTableEntryById(group.getId(), "student_groups");
    }

    @Override
    public void activate(Object o) throws SQLException {
        Group group = (Group) o;
        super.enableTableEntryById(group.getId(), "student_groups");
    }

    @Override
    public void save(Object o) throws SQLException {
        Group group = (Group) o;
        super.disableTableEntryById(group.getId(), "student_groups");
    }
}
