package edu.esprit.tests;
import edu.esprit.entites.Logement;
import edu.esprit.servies.LogementCrud;
import edu.esprit.tools.MyConnection;

import java.sql.SQLException;

public class MainClass {
    public static void main(String[] args) {
        MyConnection mc = new MyConnection();
        MyConnection mc1 = MyConnection.getInstance();

      //  Logement l = new Logement("Monastir centre", "monastir", 12345789, 220, "","en cours", "Hotel", 3, 1, 2);
        LogementCrud lc = new LogementCrud();
        //lc.ajouter(l);
       // System.out.println("logement id is " + l.id);
    }

}
