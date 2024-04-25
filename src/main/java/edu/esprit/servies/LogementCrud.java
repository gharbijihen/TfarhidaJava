package edu.esprit.servies;

import edu.esprit.entites.Equipement;
import edu.esprit.entites.Logement;
import edu.esprit.tools.MyConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LogementCrud implements IcrudL<Logement>{
    private ComboBox<String> type_log; // Modifier le type en fonction du type réel de votre ComboBox

    // Setter pour type_log
    public void setTypeLogComboBox(ComboBox<String> type_log) {
        this.type_log = type_log;
    }

    @Override
    public boolean ajouter(Logement logement) {
        String req1 = "INSERT INTO logement(nom,prix,localisation,num,image,note_moyenne,etat,type_log) VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req1);
            pst.setString(1, logement.getNom()); //tab3a ? eli fel values
            pst.setInt(2, logement.getPrix());
            pst.setString(3, logement.getLocalisation());
            pst.setInt(4, logement.getNum());
            pst.setString(5, logement.getImage());
            pst.setInt(6, logement.getNote_moyenne());
            pst.setString(7, logement.getEtat());

           // pst.setInt(9, logement.getEquipement_id());
            pst.setString(8,logement.getType_log());
          //  pst.setInt(9, logement.getEquipement().getId());// Utiliser l'ID de l'équipement associé au logement

           // pst.setInt(9, logement.getUser_id());
           // pst.setInt(10, logement.getEquipement_id());


            pst.executeUpdate();
            System.out.println("logement ajoutéé!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public void modifier(Logement logement) {
        final String query="UPDATE logement SET nom = ?, prix = ?, localisation = ?, note_moyenne = ?, image = ?, num = ?, type_log = ? WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setString(1, logement.getNom());
            pst.setInt(2, logement.getPrix());
            pst.setString(3, logement.getLocalisation());
            pst.setInt(4, logement.getNote_moyenne());
            pst.setString(5, logement.getImage());
            pst.setInt(6, logement.getNum());
            pst.setString(7, logement.getType_log());
            pst.setInt(8, logement.getId());

            pst.executeUpdate();
            System.out.println("Logement modifié avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification du logement : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void supprimer(Logement logement) throws SQLException {
        final String query = "DELETE FROM logement WHERE id=? ";
        try(PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);){
            pst.setInt(1,logement.getId());
            pst.executeUpdate();
        }

    }


    @Override
    // Dans LogementCrud.java

    public ObservableList<Logement> afficher() {
        ObservableList<Logement> logements = FXCollections.observableArrayList();

        String req3 = "SELECT * FROM logement";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(req3);
            while (rs.next()) {
                Logement logement = new Logement();
                logement.setId(rs.getInt("id"));
                logement.setNom(rs.getString("nom"));
                logement.setPrix(rs.getInt("prix"));
                logement.setLocalisation(rs.getString("localisation"));
                logement.setNum(rs.getInt("num"));
                logement.setImage(rs.getString("image"));
                logement.setEtat(rs.getString("etat"));
                logement.setType_log(rs.getString("type_log"));
                logement.setNote_moyenne(rs.getInt("note_moyenne"));
                logement.setEquipement_id(rs.getInt("equipement_id"));
                EquipementCrud equipementservice=new EquipementCrud();
                logement.setEquipement(equipementservice.getById(logement.getEquipement_id()));
                logements.add(logement);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des données : " + e.getMessage());
        }
        return logements;
    }


    public static Logement getLogementParId(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Logement logement = null;

        try {
            conn = MyConnection.getInstance().getCnx();
            if (conn == null) {
                throw new SQLException("La connexion à la base de données est nulle.");
            }

            String query = "SELECT * FROM logement WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int equipement_id = rs.getInt("equipement_id");
                String nom = rs.getString("nom");
                int prix = rs.getInt("prix");
                String localisation = rs.getString("localisation");
                int num = rs.getInt("num");
                int note_moyenne = rs.getInt("note_moyenne");
                String etat = rs.getString("etat");
                String image = rs.getString("image");
                String type_log = rs.getString("type_log");

                logement = new Logement(id, equipement_id, nom, prix, localisation, num, etat, note_moyenne,type_log,image);
            } else {
                System.out.println("Aucune activité trouvée avec l'ID : " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Vous pouvez gérer l'exception en renvoyant une valeur par défaut ou en lançant une nouvelle exception personnalisée
        } finally {
            // Fermer les ressources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return logement;
    }





}

