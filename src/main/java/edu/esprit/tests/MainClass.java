package edu.esprit.tests;
import edu.esprit.entites.Reclamation;
import edu.esprit.servies.ReclamationCrud;
import edu.esprit.tools.MyConnection;

import java.util.Date;

public class MainClass {
    public static void main(String[] args) {
        MyConnection mc = new MyConnection();
        MyConnection mc1 = MyConnection.getInstance();

//       Reclamation r = new Reclamation("reclam1","descriptionreclam1",false,new Date(),"","Activité");
//        ReclamationCrud rc = new ReclamationCrud();
//        rc.ajouter(r);
//        rc.afficher().forEach(System.out::println);
    }
}
