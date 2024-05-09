package edu.esprit.servies;

import Utils.Datasource;
import edu.esprit.entites.Equipement;
import edu.esprit.entites.Logement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LogementCrud implements IcrudE<Logement>{
    public static ArrayList<Object> getAll;
    private ComboBox<String> type_log; // Modifier le type en fonction du type réel de votre ComboBox

    public static ObservableList<Logement> getAllByType(String type) {
        ObservableList<Logement> logements = FXCollections.observableArrayList();
        String query="SELECT * FROM logement WHERE type_log = ?";
        try {
            PreparedStatement pst = Datasource.getConn().prepareStatement(query);
            pst.setString(1,type);
            ResultSet rs = pst.executeQuery();
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

                int equi_id=rs.getInt("equipement_id");

                EquipementCrud equipementCrud = new EquipementCrud();
                Equipement equipement=equipementCrud.getById(equi_id);
                System.out.println("hedhhhaaah"+equipement);

                EquipementCrud equipementservice=new EquipementCrud();
                logement.setEquipement_id(equipement);
                logements.add(logement);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des données : " + e.getMessage());
        }
        return logements;


    }

    // Setter pour type_log
    public void setTypeLogComboBox(ComboBox<String> type_log) {
        this.type_log = type_log;
    }

    @Override
    public boolean ajouter(Logement logement) {
        String req1 = "INSERT INTO logement(nom, prix, localisation, num, image, note_moyenne, etat, type_log) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pst = Datasource.getConn().prepareStatement(req1, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, logement.getNom());
            pst.setInt(2, logement.getPrix());
            pst.setString(3, logement.getLocalisation());
            pst.setInt(4, logement.getNum());
            pst.setString(5, logement.getImage());
            pst.setInt(6, logement.getNote_moyenne());
            pst.setString(7, logement.getEtat());
            pst.setString(8, logement.getType_log());

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected == 1) {
                // Retrieve the generated id
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    logement.setId(generatedId);
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public void modifier(Logement logement) {
        final String query="UPDATE logement SET nom = ?, prix = ?, localisation = ?, note_moyenne = ?, image = ?, num = ?, type_log = ? WHERE id = ?";
        try (PreparedStatement pst = Datasource.getConn().prepareStatement(query)) {
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
        try(PreparedStatement pst = Datasource.getConn().prepareStatement(query);){
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
            Statement st = Datasource.getConn().createStatement();
            ResultSet rs = st.executeQuery(req3);
            while (rs.next()) {
                Logement logement = new Logement(4);
                logement.setId(rs.getInt("id"));
                logement.setNom(rs.getString("nom"));
                logement.setPrix(rs.getInt("prix"));
                logement.setLocalisation(rs.getString("localisation"));
                logement.setNum(rs.getInt("num"));
                logement.setImage(rs.getString("image"));
                logement.setEtat(rs.getString("etat"));
                logement.setType_log(rs.getString("type_log"));
                logement.setNote_moyenne(rs.getInt("note_moyenne"));

                int equi_id=rs.getInt("equipement_id");

                EquipementCrud equipementCrud = new EquipementCrud();
                Equipement equipement=equipementCrud.getById(equi_id);
                System.out.println("hedhhhaaah"+equipement);

                EquipementCrud equipementservice=new EquipementCrud();
                logement.setEquipement_id(equipement);
                logements.add(logement);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des données : " + e.getMessage());
        }
        return logements;
    }
    public void associerEquipementALogement(Logement logement, Equipement equipement) throws SQLException {
        String sql = "UPDATE logement SET equipement_id = ? WHERE id = ?";
        PreparedStatement pst = Datasource.getConn().prepareStatement(sql);

        System.out.println("eq"+equipement.getId()+"log"+logement.getId());
        pst.setInt(1, equipement.getId());
        pst.setInt(2, logement.getId());

        pst.executeUpdate();

    }

    public static Logement getLogementParId(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Logement logement = null;

        try {
            conn = Datasource.getConn();
            if (conn == null) {
                throw new SQLException("La connexion à la base de données est nulle.");
            }

            String query = "SELECT * FROM logement WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int equi_id=rs.getInt("equipement_id");
                EquipementCrud equipementCrud = new EquipementCrud();
                Equipement equipement=equipementCrud.getById(equi_id);

                String nom = rs.getString("nom");
                int prix = rs.getInt("prix");
                String localisation = rs.getString("localisation");
                int num = rs.getInt("num");
                int note_moyenne = rs.getInt("note_moyenne");
                String etat = rs.getString("etat");
                String image = rs.getString("image");
                String type_log = rs.getString("type_log");

                logement = new Logement(id, equipement, nom, prix, localisation, num, etat, note_moyenne,type_log,image);
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


    public ObservableList<Logement> trierParPrix(ObservableList<Logement> logementsList) throws SQLException {
        ObservableList<Logement> logements = FXCollections.observableArrayList();

        try (Statement st = Datasource.getConn().createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM logement ORDER BY prix")) {

            while (rs.next()) {
                Logement p = new Logement(4);
                p.setId(rs.getInt("id"));
                p.setNom(rs.getString("nom"));
                p.setPrix(rs.getInt("prix"));
                p.setLocalisation(rs.getString("localisation"));
                p.setNum(rs.getInt("num"));
                p.setNote_moyenne(rs.getInt("note_moyenne"));
                p.setEtat(rs.getString("etat"));
                p.setImage(rs.getString("image"));
                p.setType_log(rs.getString("type_log"));
                logements.add(p);
            }
        } catch (SQLException e) {
            logError(e.getMessage());
        }

        return logements;
    }

    private void logError(String message) {
        System.err.println("Erreur : " + message);
    }

    public Map<String, Integer> getLogementByType() {
        Map<String, Integer> activiteByetat = new HashMap<>();

        try  (Connection connection = Datasource.getConn()) {
            String query = "SELECT type_log, COUNT(*) AS count FROM logement GROUP BY type_log";
            try(PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    String type = resultSet.getString("type_log");
                    int count = resultSet.getInt("count");
                    activiteByetat.put(type, count);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activiteByetat;
    }
    public void modifierEtat(int logementId, String nouvelEtat) {
        String query = "UPDATE logement SET etat = ? WHERE id = ?";
        try (PreparedStatement pst = Datasource.getConn().prepareStatement(query)) {
            pst.setString(1, nouvelEtat);
            pst.setInt(2, logementId);
            pst.executeUpdate();
            System.out.println("État du logement modifié avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de l'état du logement : " + e.getMessage());
            e.printStackTrace();
        }

    }
}

