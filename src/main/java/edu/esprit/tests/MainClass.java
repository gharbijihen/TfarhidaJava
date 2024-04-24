package edu.esprit.tests;
import edu.esprit.entites.Moyen_transport;
import edu.esprit.entites.Trajet;
import edu.esprit.servies.Moyen_transportCrud;
import edu.esprit.servies.TrajetCrud;
import edu.esprit.tools.MyConnection;

import java.sql.Date;

public class MainClass {
    public static void main(String[] args) {
        MyConnection mc = new MyConnection();
        MyConnection mc1 = MyConnection.getInstance();
        java.sql.Date date = java.sql.Date.valueOf("2001-03-17");


        Moyen_transport l = new Moyen_transport("Bus", 200, "SidiBou", true,   false, "65e847bcebe63.jpg");
        Moyen_transportCrud lc = new Moyen_transportCrud();
        lc.ajouter(l);
        System.out.println("trajet id is " + l.id);
    }

}

