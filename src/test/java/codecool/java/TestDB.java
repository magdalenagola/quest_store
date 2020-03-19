package codecool.java;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;

public class TestDB {

    @BeforeAll
    public static void initDb() {
        if (dbExists()) {
            dropDb();
        }
        makeDb();
        makeTableCards();
    }

    @Test
    public void shouldInsertAndSelectATestEntry() {
        insertTestEntry();
        selectTestEntry();
        assertTrue(true);
    }

    @AfterAll
    public static void teardownDb() {
        dropDb();
    }

    private static void makeTableCards() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", "postgres", "postgres");
            Statement createTable = null;
            createTable = conn.createStatement();
            String createCardsTable = "create table cards (" +
                    "id serial not null " +
                    "constraint cards_pk " +
                    "primary key, " +
                    "title varchar not null, " +
                    "description varchar not null, " +
                    "image varchar not null, " +
                    "quantity integer not null, " +
                    "is_active boolean default true not null, " +
                    "cost integer default 1 not null" +
                    ");" +
                    "alter table cards " +
                    "owner to postgres; " +
                    "create unique index cards_id_uindex " +
                    "on cards (id);";
            createTable.execute(createCardsTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void makeDb() {
        try {
            Statement createDb = null;
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", "postgres", "postgres");
            createDb = conn.createStatement();
            createDb.execute("CREATE DATABASE testdb;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void dropDb() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", "postgres", "postgres");
            Statement dropTableStatement = null;
            dropTableStatement = conn.createStatement();
            dropTableStatement.execute("DROP TABLE cards");
            Statement dropStatement = null;
            dropStatement = conn.createStatement();
            dropStatement.execute("DROP DATABASE testdb;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertTestEntry() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", "postgres", "postgres");
            String orderToSql = "INSERT INTO cards (title, description, image, quantity, is_active, cost) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement insertStmt = conn.prepareStatement(orderToSql);
            insertStmt.setString(1, "test_title");
            insertStmt.setString(2, "test_description");
            insertStmt.setString(3, "test_image");
            insertStmt.setInt(4, 1);
            insertStmt.setBoolean(5, true);
            insertStmt.setInt(6, 1);
            insertStmt.execute(orderToSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void selectTestEntry() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", "postgres", "postgres");
            ResultSet selectRs = null;
            String select = ("SELECT * FROM cards");
            selectRs = conn.createStatement().executeQuery(select);
            while (selectRs.next()) {
                String title = selectRs.getString("title");
                String description = selectRs.getString("description");
                String image = selectRs.getString("image");
                int quantity = selectRs.getInt("quantity");
                boolean isActive = selectRs.getBoolean("is_active");
                int cost = selectRs.getInt("cost");
                System.out.println(
                        cost +
                                description +
                                image +
                                isActive +
                                quantity +
                                title
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean dbExists() {
        try {
            Statement selectStatement = null;
            String check = "SELECT 1 from pg_database WHERE datname='testdb';";
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", "postgres", "postgres");
            selectStatement = conn.createStatement();
            ResultSet rs = selectStatement.executeQuery(check);
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
