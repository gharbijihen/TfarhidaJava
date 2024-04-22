package edu.esprit.tests;

import edu.esprit.entites.Activite;
import edu.esprit.entites.Categorie;
import edu.esprit.servies.ActiviteCrud;
import edu.esprit.servies.CategorieCrud;
import edu.esprit.tools.MyConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class MainClass {
    public static void main(String[] args) {
        //MyConnection mc = new MyConnection();
        //MyConnection mc1 = MyConnection.getInstance();

        Categorie c=new Categorie(5,"allo","allo");
        Categorie c1=new Categorie(6,"noura","nawara");

        CategorieCrud cc = new CategorieCrud();
        cc.ajouter(c);
        cc.ajouter(c1);
        try {
            cc.modifier(c1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        cc.supprimer(c);
        System.out.println("c id is "+c.id);
        //Activite p = new Activite(2,"Bikee", 20, "Nabeul", 10, "En cours","1", "5.jpg", 2);

        Activite p1 = new Activite(3,4,"Bikee", 20, "Nabeul", 5,"oui", "jolie", "5.jpg");

        ActiviteCrud ac = new ActiviteCrud();
        ac.ajouter1(p1);
        System.out.println(ac.afficher());
        System.out.println(cc.afficher());




    }

}

