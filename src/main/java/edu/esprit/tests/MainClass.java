package edu.esprit.tests;

import edu.esprit.entites.Activite;
import edu.esprit.entites.Categorie;
import edu.esprit.servies.ActiviteCrud;
import edu.esprit.servies.CategorieCrud;
import edu.esprit.tools.MyConnection;

import java.sql.SQLException;

public class MainClass {
    public static void main(String[] args) {
        MyConnection mc = new MyConnection();
        MyConnection mc1 = MyConnection.getInstance();

        Categorie c=new Categorie(3,"allo","allo");
        CategorieCrud cc = new CategorieCrud();
        //cc.ajouter(c);
        System.out.println("c id is "+c.id);
        Activite p = new Activite(2,"Bikee", 20, "Nabeul", 10, "En cours","1", "5.jpg", 2);
        ActiviteCrud ac = new ActiviteCrud();
        //ac.ajouter(p);
        System.out.println(ac.afficher());
        System.out.println(cc.afficher());
        try {
            ac.modifier(p);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            cc.modifier(c);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            ac.supprimer(p);
            cc.supprimer(c);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}

