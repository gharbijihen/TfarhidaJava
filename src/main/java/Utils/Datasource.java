package Utils;

import java.sql.*;
public class Datasource {
    private static Connection conn = null;
    private static String url = "jdbc:mysql://localhost:3306/integration";
    private static String user="root";
    private static String pwd="";
    private static Datasource D;
    private Datasource()
    {
        try {
            conn = DriverManager.getConnection(url,user,pwd);
            System.out.println("connexion etablie");

        } catch(SQLException e) {
            System.out.println(e);
        }

    }
    public static Connection getConn()
    {
        try {
        if(conn==null|| conn.isClosed())
            new Datasource();
    } catch (SQLException e) {
        System.out.println("Erreur lors de la création de la connexion : " + e.getMessage());
    }
        return conn;

    }
    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Connexion fermée.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
        }
    }

}
