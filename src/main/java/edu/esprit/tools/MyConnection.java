package edu.esprit.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    private String URL="jdbc:mysql://localhost:3306/testtest";
    private String USER="root";
    private String PWD="";
    Connection cnx;
    public static MyConnection instance;
    public MyConnection() {
        try {
            cnx= DriverManager.getConnection(URL,USER,PWD);
            System.out.println("Connexion etablie !");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection getCnx() {
        try {
            if (cnx == null || cnx.isClosed()) {
                cnx = DriverManager.getConnection(URL, USER, PWD);
                System.out.println("Nouvelle connexion établie.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la création de la connexion : " + e.getMessage());
        }

        return cnx;
    }
    public  static MyConnection getInstance() {
        if(instance== null){
            instance=new MyConnection();
        }
        return instance;

    }
    public void closeConnection() {
        try {
            if (cnx != null && !cnx.isClosed()) {
                cnx.close();
                System.out.println("Connexion fermée.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
        }
    }


}
