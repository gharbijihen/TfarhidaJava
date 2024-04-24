package edu.esprit.servies;

import edu.esprit.entites.Moyen_transport;
import edu.esprit.entites.Trajet;
import edu.esprit.tools.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TrajetCrud implements IcrudL<Trajet>{
    @Override
    public void ajouter(Trajet trajet) {
        // Vérifiez d'abord si le lieu de départ est null ou vide
        if (trajet.getLieu_depart() == null || trajet.getLieu_depart().isEmpty()) {
            System.out.println("Le lieu de départ est obligatoire");
            return; // Sortez de la méthode si le lieu de départ est vide
        }

        // Si le lieu de départ n'est pas vide, exécutez l'insertion dans la base de données
        String req1 = "INSERT INTO trajet(lieu_depart,lieu_arrivee,heure,date,moyen_transport_id) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req1);
            pst.setString(1,trajet.getLieu_depart());
            pst.setString(2,trajet.getLieu_arrivee());
            pst.setString(3,trajet.getHeure());
            pst.setDate(4,trajet.getDate());
            pst.setInt(5,trajet.getMoyen_transport_id());
            pst.executeUpdate();
            System.out.println("Trajet ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void modifier(Trajet trajet) throws SQLException {
        final String query="UPDATE trajet SET lieu_depart = ?, lieu_arrivee = ?, heure = ?,date = ?,moyen_transport_id= ? WHERE id= ?";
        try( PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);){

            pst.setString(1,trajet.getLieu_depart());
            pst.setString(2,trajet.getLieu_arrivee());
            pst.setString(3,trajet.getHeure());
            pst.setDate(4,trajet.getDate());
            pst.setInt(5,trajet.getMoyen_transport_id());
            pst.setInt(6,trajet.getId());



            pst.executeUpdate();
        }
    }

    @Override
    public void supprimer(Trajet trajet) throws SQLException {
        final String query = "DELETE FROM trajet WHERE id=? ";
        try(PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);){
            pst.setInt(1,trajet.getId());
            pst.executeUpdate();
        }

    }


    @Override
    public List<Trajet> afficher() {
        List<Trajet>trajets=new ArrayList<>();
        String req3="SELECT * FROM trajet";
        try {
            Statement st=MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs= st.executeQuery(req3);
            while (rs.next())
            {
                Trajet p=new Trajet();
                p.setId(rs.getInt("id"));
                p.setLieu_depart(rs.getString("lieu_depart")); //avec label
                p.setLieu_arrivee(rs.getString("lieu_arrivee"));
                p.setHeure(rs.getString("heure"));
                p.setDate(rs.getDate("date"));
                p.setMoyen_transport_id(rs.getInt("moyen_transport_id"));





                trajets.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  trajets;
    }
}

