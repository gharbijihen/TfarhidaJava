package edu.esprit.servies;

import edu.esprit.entites.Equipement;
import edu.esprit.tools.MyConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class EquipementCrud implements IcrudL<Equipement> {
    @Override
    public boolean ajouter(Equipement equipement) {
        String req1 = "INSERT INTO equipement (parking, internet, climatisation, nbr_chambre, description, types_de_chambre) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req1);
            pst.setBoolean(1, equipement.isParking());
            pst.setBoolean(2, equipement.isInetrnet());
            pst.setBoolean(3, equipement.isClimatisation());
            pst.setInt(4, equipement.getNbr_chambre());
            pst.setString(5, equipement.getDescription());
            pst.setString(6, equipement.getType_chambre());
            pst.executeUpdate();
            System.out.println("Equipement ajouté!");
            return true; // Retourne true si l'ajout est réussi
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false; // Retourne false si une exception est levée
        }
    }

    // Autres méthodes de la classe



    @Override
    public void modifier(Equipement T) throws SQLException {

    }

    @Override
    public void supprimer(Equipement T) throws SQLException {

    }

    @Override
    public List<Equipement> afficher() {
        return null;
    }
}
