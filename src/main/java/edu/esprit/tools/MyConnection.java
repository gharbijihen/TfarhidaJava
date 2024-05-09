package edu.esprit.tools;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {

    private final String URL = "jdbc:mysql://localhost:3306/restaurant";
    private final String USER = "root";
    private final String PASSWORD = "";
    private Connection connection;
    private static MyConnection instance;

    public MyConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static MyConnection getInstance() {
        if(instance == null)
            instance = new MyConnection();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}