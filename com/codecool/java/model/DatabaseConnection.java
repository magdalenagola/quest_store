package codecool.java.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public enum DatabaseConnection {
    INSTANCE;

    private String env;
    private Connection conn;

    private void initConn(){
        this.env = getEnv();
        String[] credentials = getDbCredentials();
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(credentials[0],
                    credentials[1], credentials[2]);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

//    public void setEnv(String env){
//        this.env = env;
//    }

    public Connection getConnection() {
        if (INSTANCE.conn == null) {
            initConn();
        }
        return INSTANCE.conn;
    }

    static String getEnv() {
        Scanner reader = null;
        try {
            reader = new Scanner(new File("com/codecool/resources/env/env"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return reader.nextLine();
    }

    String[] getDbCredentials() {
        Scanner reader = null;
        String[] credentials = new String[3];
        int i = 0;
        try {
            reader = new Scanner(new File("com/codecool/resources/env/" + this.env + "Env"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(reader.hasNextLine()){
            credentials[i] = reader.nextLine();
            i++;
        }
        return credentials;
    }
}