package codecool.java.model;

import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO implements DAO{

    @Override
    public void selectUser() {
        try {
            Class.forName("org.postgresql.Driver");
            BasicConnectionPool bc = BasicConnectionPool.create("jdbc:postgresql://ec2-176-34-237-141.eu-west-1.compute.amazonaws.com:5432/dbc5jifafq3j1h?sslmode=require",
                    "utiuhfgjckzuoq", "17954f632e3663cbadb55550dd636f4c3a645ade56c3342ee89f71fc732c9672");
            System.out.println("SUCCESS");
            Connection c = bc.getConnection();
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users;");
            while(rs.next()){
                System.out.println(rs.getString("name"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
