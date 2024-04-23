package edu.esprit.servies;

import edu.esprit.entites.Logement;
import edu.esprit.tools.MyConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LogementCrud implements IcrudL<Logement>{
    private ComboBox<String> type_log; // Modifier le type en fonction du type réel de votre ComboBox

    // Setter pour type_log
    public void setTypeLogComboBox(ComboBox<String> type_log) {
        this.type_log = type_log;
    }

    @Override
    public boolean ajouter(Logement logement) {
        String req1 = "INSERT INTO logement(nom,prix,localisation,num,image,note_moyenne,etat,type_log,equipement_id) VALUES (?,?,?,?,?,?,?,?,?)";
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
            pst.setInt(9, logement.getEquipement()); // Utiliser l'ID de l'équipement associé au logement

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
        final String query="UPDATE logement SET nom = ?, prix = ?, localisation = ?, note_moyenne = ?, etat = ?, image = ?, num = ?, type_log = ? WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setString(1, logement.getNom());
            pst.setInt(2, logement.getPrix());
            pst.setString(3, logement.getLocalisation());
            pst.setInt(4, logement.getNote_moyenne());
            pst.setString(5, logement.getEtat());
            pst.setString(6, logement.getImage());
            pst.setInt(7, logement.getNum());
            pst.setString(8, logement.getType_log());
            pst.setInt(9, logement.getId());

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

    public ObservableList<Logement>  afficher() {
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
                logement.setEquipement(rs.getInt("equipement_id"));
                logements.add(logement);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des données : " + e.getMessage());
        }
        return logements;
    }

}

