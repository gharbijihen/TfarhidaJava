package edu.esprit.servies;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import edu.esprit.tools.MyConnection;
import edu.esprit.entites.Activite;

public class ActiviteCrud implements IcrudA<Activite> {
    @Override
    public void ajouter1(Activite activite) {
        String req1 = "INSERT INTO activitee(categorie_id,nom,prix,localisation,nb_P,etat,description_act,image) VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req1);
            pst.setInt(1,activite.getCategorie_id()); //tab3a ? eli fel values
            pst.setString(2, activite.getNom()); //tab3a ? eli fel values
            pst.setInt(3, activite.getPrix());
            pst.setString(4, activite.getLocalisation());
            pst.setInt(5, activite.getNb_P()); // Set nb_P
            pst.setString(6, activite.getEtat());
            pst.setString(7, activite.getDescription_act());
            pst.setString(8, activite.getImage());

            pst.executeUpdate();
            System.out.println("activite ajoutéé!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    /*@Override
    public void ajouter(Activite activite,String imagePath) {
        String req1 = "INSERT INTO activitee(categorie_id,nom,prix,localisation,nb_P,etat,image,description_act) VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req1);
            pst.setInt(1,activite.getCategorie_id()); //tab3a ? eli fel values
            pst.setString(2, activite.getNom()); //tab3a ? eli fel values
            pst.setInt(3, activite.getPrix());
            pst.setString(4, activite.getLocalisation());
            pst.setInt(5, activite.getNb_P()); // Set nb_P
            pst.setString(6, activite.getEtat());
            pst.setString(7, imagePath);
            pst.setString(8,activite.getDescription_act());
            pst.executeUpdate();
            System.out.println("activite ajoutéé!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }*/
    @Override
    public void ajouter(Activite activite,String imagePath) {
        String req1 = "INSERT INTO activitee(categorie_id,nom,prix,localisation,nb_P,etat,image,description_act) VALUES (?,?,?,?,?,?,?,?)";
        try {
            // Check if the category ID exists in the categorie table
            if (!categoryExists(activite.getCategorie_id())) {
                System.out.println("Invalid category ID");
                return;
            }

            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req1);
            pst.setInt(1, activite.getCategorie_id());
            pst.setString(2, activite.getNom());
            pst.setInt(3, activite.getPrix());
            pst.setString(4, activite.getLocalisation());
            pst.setInt(5, activite.getNb_P());
            pst.setString(6, activite.getEtat());
            pst.setString(7, imagePath);
            pst.setString(8, activite.getDescription_act());
            pst.executeUpdate();
            System.out.println("Activite ajoutée!");
        } catch (SQLException e) {
            System.out.println("Error adding activite: " + e.getMessage());
        }
    }

    // Check if the category ID exists in the categorie table
    private boolean categoryExists(int categoryId) throws SQLException {
        String query = "SELECT id FROM categorie WHERE id = ?";
        PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement(query);
        statement.setInt(1, categoryId);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next(); // If next() returns true, category ID exists
    }


    @Override
    public void modifier(Activite activite) throws SQLException {
        final String query="UPDATE activitee SET nom = ?, prix = ?, localisation = ?,nb_P = ?,etat= ?,image = ?,description_act= ?,categorie_id= ?  WHERE id= ?";
        try( PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);){

            pst.setString(1,activite.getNom());
            pst.setInt(2,activite.getPrix());
            pst.setString(3,activite.getLocalisation());
            pst.setInt(4,activite.getNb_P());
            pst.setString(5,activite.getEtat());
            pst.setString(6,activite.getImage());
            pst.setString(7,activite.getDescription_act());
            pst.setInt(8,activite.getCategorie_id());
            pst.setInt(9,activite.getId());
            pst.executeUpdate();
        }
    }




    @Override
    public void supprimer(Activite activite) throws SQLException {
        final String query = "DELETE FROM activitee WHERE id=? ";
        try(PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);){
            pst.setInt(1,activite.getId());
            pst.executeUpdate();
        }
    }


    @Override
    public List<Activite> afficher() {
        List<Activite>activites=new ArrayList<>();
        String req3="SELECT * FROM activitee";
        try {
            Statement st=MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs= st.executeQuery(req3);
            while (rs.next())
            {
                Activite p;
                p = new Activite();
                p.setId(rs.getInt("id"));
                p.setNom(rs.getString("nom")); //avec label
                p.setPrix(rs.getInt("prix"));
                p.setLocalisation(rs.getString("localisation"));
                p.setNb_P(rs.getInt("nb_P"));
                p.setEtat(rs.getString("etat"));
                p.setImage(rs.getString("image"));
                p.setDescription_act(rs.getString("description_act"));
                p.setCategorie_id(rs.getInt("categorie_id"));
                activites.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  activites;
    }
}


